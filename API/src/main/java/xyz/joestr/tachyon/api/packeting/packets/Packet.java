/*
Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
 */
package xyz.joestr.tachyon.api.packeting.packets;

import xyz.joestr.tachyon.api.packeting.StandardPackets;

/**
 *
 * @author Joel
 */
public abstract class Packet {
	StandardPackets Id;

	public Packet(StandardPackets Id) {
		this.Id = Id;
	}

	public StandardPackets getId() {
		return Id;
	}

	public void setId(StandardPackets Id) {
		this.Id = Id;
	}
}
