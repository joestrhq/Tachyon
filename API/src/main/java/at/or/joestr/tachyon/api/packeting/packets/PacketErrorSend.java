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
public class PacketErrorSend extends Packet {
	
	public PacketErrorSend() {
		super(Packets.Provided.ERROR_SEND);
	}
	
}
