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
public class PacketGetPlayerSettings extends Packet {
	
  private UUID player;
	private Map<String, String> settings = null;
	
	public PacketGetPlayerSettings(UUID player) {
		super(Packets.Provided.GET_ALL_PLAYERS.ordinal(), false);
		
    this.player = player;
	}

  public UUID getPlayer() {
    return player;
  }

  public void setPlayer(UUID player) {
    this.player = player;
  }

  public Map<String, String> getSettings() {
    return settings;
  }

  public void setSettings(Map<String, String> settings) {
    this.settings = settings;
  }
}
