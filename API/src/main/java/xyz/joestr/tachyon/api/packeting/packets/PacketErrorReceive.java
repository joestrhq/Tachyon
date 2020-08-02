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
public class PacketErrorReceive extends Packet {
	
	public PacketErrorReceive() {
		super(Packets.Provided.ERROR_RECEIVE);
	}
	
}
