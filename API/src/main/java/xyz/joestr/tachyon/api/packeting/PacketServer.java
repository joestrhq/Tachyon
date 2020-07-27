// 
// Private License
// 
// Copyright (c) 2019-2020 Joel Strasser
// 
// Only the owner is allowed to use this software.
// 
package xyz.joestr.tachyon.api.packeting;

import xyz.joestr.tachyon.api.packeting.packets.Packet;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.joestr.tachyon.api.packeting.packets.PacketErrorReceive;
import xyz.joestr.tachyon.api.packeting.packets.PacketErrorSend;
import xyz.joestr.tachyon.api.utils.ByteSerializer;

/**
 *
 * @author Joel
 */
public class PacketServer {

	private AsynchronousServerSocketChannel serverChannel;
	private Future<AsynchronousSocketChannel> acceptResult;
	private AsynchronousSocketChannel clientChannel;
	
	private HashMap<Packet, Callable<Packet>> packetHandlers;

	public PacketServer(String ipAddress, int port) throws IOException {
		this.serverChannel = AsynchronousServerSocketChannel.open();
		this.serverChannel.bind(new InetSocketAddress(ipAddress, port));
		acceptResult = serverChannel.accept();
	}
	
	public void addPacketHandler(Packet packet, Callable<Packet> handler) {
		this.packetHandlers.put(packet, handler);
	}
	
	public void removePacketHanlder(Packet packet) {
		this.packetHandlers.remove(packet);
	}

	public void runServer() {
		serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

			@Override
			public void completed(AsynchronousSocketChannel result, Object attachment) {
				if (serverChannel.isOpen()) {
					serverChannel.accept(null, this);
				}
				clientChannel = result;
				if ((clientChannel != null) && (clientChannel.isOpen())) {
					
					ByteBuffer readBuffer = ByteBuffer.allocate(512);
					clientChannel.read(readBuffer);
					
					Packet received = null;
					
					try {
						received = (Packet) ByteSerializer.deserialize(readBuffer.array());
					} catch (IOException ex) {
						Logger.getLogger(PacketServer.class.getName()).log(Level.SEVERE, null, ex);
					} catch (ClassNotFoundException ex) {
						Logger.getLogger(PacketServer.class.getName()).log(Level.SEVERE, null, ex);
					}
					
					ByteBuffer writeBuffer = null;
					try {
						writeBuffer =  ByteBuffer.allocate(512);
						writeBuffer.put(ByteSerializer.serialize(new PacketErrorReceive()));
					} catch (IOException ex) {
						Logger.getLogger(PacketServer.class.getName()).log(Level.SEVERE, null, ex);
					}
					
					if (received == null) {
						clientChannel.write(writeBuffer);
						return;
					}
					
					try {
						writeBuffer =  ByteBuffer.allocate(512);
						writeBuffer.put(ByteSerializer.serialize(new PacketErrorSend()));
					} catch (IOException ex) {
						Logger.getLogger(PacketServer.class.getName()).log(Level.SEVERE, null, ex);
					}
					
					Packet sending = null;
					
					for (Packet p : packetHandlers.keySet()) {
						if (!p.getId().equals(received.getId())) continue;
						
						try {
							sending = packetHandlers.get(p).call();
						} catch (Exception ex) {
							Logger.getLogger(PacketServer.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
					
					if (sending == null ) {
						clientChannel.write(writeBuffer);
					}
				}
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				// process error
			}
		});
	}
}
