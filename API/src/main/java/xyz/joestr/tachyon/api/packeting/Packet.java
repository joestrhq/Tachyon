/*
Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
 */
package xyz.joestr.tachyon.api.packeting;

/**
 *
 * @author Joel
 */
public abstract class Packet {
	Packets Id;
	Boolean response;

	public Packet(Packets Id, Boolean response) {
		this.Id = Id;
		this.response = response;
	}

	public Packets getId() {
		return Id;
	}

	public void setId(Packets Id) {
		this.Id = Id;
	}

	public Boolean getResponse() {
		return response;
	}

	public void setResponse(Boolean response) {
		this.response = response;
	}
}
