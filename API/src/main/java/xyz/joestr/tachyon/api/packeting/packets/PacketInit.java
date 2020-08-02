// 
// Private License
// 
// Copyright (c) 2019-2020 Joel Strasser
// 
// Only the owner is allowed to use this software.
// 
package xyz.joestr.tachyon.api.packeting.packets;

import xyz.joestr.tachyon.api.packeting.Packets;

/**
 *
 * @author Joel
 */
public class PacketInit extends Packet {
	
	int nextPacketByteSize;
	
	public PacketInit(int nextPacketByteSize) {
		super(Packets.Provided.INIT);
		
		if (nextPacketByteSize > Packets.MAX_BYTES) {
			throw new IllegalArgumentException(
				"The maximum number of bytes per packet is " + Packets.MAX_BYTES
			);
		}
		
		this.nextPacketByteSize = nextPacketByteSize;
	}

	public int getNextPacketByteSize() {
		return nextPacketByteSize;
	}
}
