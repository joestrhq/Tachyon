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
public abstract class CustomPacket extends Packet {
	
	Integer customId;
	
	public CustomPacket(Boolean response, Integer customId) {
		super(Packets.OTHER, response);
		this.customId = customId;
	}

	public Integer getCustomId() {
		return customId;
	}

	public void setCustomId(Integer customId) {
		this.customId = customId;
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
