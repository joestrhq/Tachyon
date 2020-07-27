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
public class PacketErrorReceive extends Packet {
	
	public PacketErrorReceive() {
		super(StandardPackets.ERROR_RECEIVE);
	}
	
}
