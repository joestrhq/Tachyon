// 
// Private License
// 
// Copyright (c) 2019-2020 Joel Strasser
// 
// Only the owner is allowed to use this software.
// 
package xyz.joestr.tachyon.api.packeting.packets;

import java.util.Map;
import java.util.UUID;
import xyz.joestr.tachyon.api.packeting.Packets;


/**
 *
 * @author Joel
 */
public class PacketGenAllPlayers extends Packet {
	
	public Map<UUID, String> players;
	
	public PacketGenAllPlayers(Map<UUID, String> players) {
		super(Packets.Provided.GET_ALL_PLAYERS);
		
		this.players = players;
	}
	
	public Map<UUID, String> getPlayers() {
		return this.players;
	}
}
