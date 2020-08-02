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
public class PacketGenAllPlayers extends Packet {
	
	public PacketGenAllPlayers() {
		super(Packets.Provided.GET_ALL_PLAYERS);
	}
	
}
