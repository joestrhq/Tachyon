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
	Packets.Provided Id;

	public Packet(Packets.Provided Id) {
		this.Id = Id;
	}

	public Packets.Provided getId() {
		return Id;
	}

	public void setId(Packets.Provided Id) {
		this.Id = Id;
	}
}
