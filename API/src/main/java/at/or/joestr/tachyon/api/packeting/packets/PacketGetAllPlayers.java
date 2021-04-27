// 
// Private License
// 
// Copyright (c) 2019-2020 Joel Strasser
// 
// Only the owner is allowed to use this software.
// 
package at.or.joestr.tachyon.api.packeting.packets;

import java.util.Map;
import java.util.UUID;
import at.or.joestr.tachyon.api.packeting.Packets;


/**
 *
 * @author Joel
 */
public class PacketGetAllPlayers extends Packet {
	
	private Map<UUID, String> players = null;
	
	public PacketGetAllPlayers() {
		super(Packets.Provided.GET_ALL_PLAYERS.ordinal(), false);
	}

  public Map<UUID, String> getPlayers() {
    return players;
  }

  public void setPlayers(Map<UUID, String> players) {
    this.players = players;
  }
}
