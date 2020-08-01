// 
// Private License
// 
// Copyright (c) 2019-2020 Joel Strasser
// 
// Only the owner is allowed to use this software.
// 
package xyz.joestr.tachyon.api.packeting.packets;

import xyz.joestr.tachyon.api.packeting.StandardPackets;

/**
 *
 * @author Joel
 */
public class PacketInit extends Packet {
	
	short nextPacketByteSize;
	
	public PacketInit(short nextPacketByteSize) {
		super(StandardPackets.INIT);
		this.nextPacketByteSize = nextPacketByteSize;
	}

	public short getNextPacketByteSize() {
		return nextPacketByteSize;
	}
}
