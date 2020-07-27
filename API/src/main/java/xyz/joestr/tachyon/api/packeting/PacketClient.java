/*
Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
 */
package xyz.joestr.tachyon.api.packeting;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import xyz.joestr.tachyon.api.packeting.packets.Packet;
import xyz.joestr.tachyon.api.utils.ByteSerializer;

/**
 *
 * @author Joel
 */
public class PacketClient {
	private AsynchronousSocketChannel client;
  private Future<Void> future;

	public PacketClient(String hostname, int port) throws IOException, InterruptedException, ExecutionException {
		client = AsynchronousSocketChannel.open();
		InetSocketAddress hostAddress = new InetSocketAddress(hostname, port);
		future = client.connect(hostAddress);
		future.get();
	}
	
	public Packet sendRequest(Packet packet) throws IOException, InterruptedException, ClassNotFoundException, ExecutionException {
		ByteBuffer buffer = ByteBuffer.wrap(ByteSerializer.serialize(packet));

		Future<Integer> writeResult = client.write(buffer);
		writeResult.get();

		buffer.clear();
		
		Future<Integer> readResult = client.read(buffer);
		readResult.get();
		
		Packet result = (Packet) ByteSerializer.deserialize(buffer.array());
		
		buffer.clear();
		
		return result;
    }
}
