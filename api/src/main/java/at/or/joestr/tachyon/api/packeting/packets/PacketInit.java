// 
// Private License
// 
// Copyright (c) 2019-2020 Joel Strasser
// 
// Only the owner is allowed to use this software.
// 
package at.or.joestr.tachyon.api.packeting.packets;

import at.or.joestr.tachyon.api.packeting.Packets;

/**
 *
 * @author Joel
 */
public class PacketInit extends Packet {
	
	private int nextPacketByteSize;
	
	public PacketInit(int nextPacketByteSize) {
		super(Packets.Provided.INIT.ordinal(), false);
		
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

  public void setNextPacketByteSize(int nextPacketByteSize) {
    this.nextPacketByteSize = nextPacketByteSize;
  }
}
