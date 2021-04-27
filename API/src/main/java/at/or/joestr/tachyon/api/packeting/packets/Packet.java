/*
Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
 */
package at.or.joestr.tachyon.api.packeting.packets;

import at.or.joestr.tachyon.api.packeting.Packets;

/**
 *
 * @author Joel
 */
public abstract class Packet {
  
	private int id;
  private boolean response;

	public Packet(int id, boolean response) {
		this.id = id;
    this.response = response;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

  public boolean isResponse() {
    return response;
  }

  public void setResponse(boolean response) {
    this.response = response;
  }
}
