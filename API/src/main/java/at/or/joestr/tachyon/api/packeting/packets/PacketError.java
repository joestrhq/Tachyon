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
public class PacketError extends Packet {
	
  private String message;
  
	public PacketError(String message) {
		super(Packets.Provided.ERROR_RECEIVE.ordinal(), false);
    
    this.message = message;
	}

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
	
  
}
