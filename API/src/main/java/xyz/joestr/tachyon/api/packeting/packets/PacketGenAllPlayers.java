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
public class PacketGenAllPlayers extends Packet {
	
	public PacketGenAllPlayers() {
		super(StandardPackets.GET_ALL_PLAYERS);
	}
	
}
