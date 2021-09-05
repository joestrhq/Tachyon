/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.joestr.tachyon.bungeecord.utils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Is a teleport request for players. Used in the {@link xyz.joestr.tachyon.tachyon_bungeecord.managers.TPAManager} for example.
 * 
 * @author Joel
 */
public class TpaRequest {

    private UUID source;
    private UUID target;
    private TpaType tpaType;
    private LocalDateTime expiry;

    public TpaRequest(UUID source, UUID target, TpaType tpaType, LocalDateTime expiry) {
        this.source = source;
        this.target = target;
        this.tpaType = tpaType;
        this.expiry = expiry;
    }

    public UUID getSource() {
        return source;
    }

    public void setSource(UUID source) {
        this.source = source;
    }

    public UUID getTarget() {
        return target;
    }

    public void setTarget(UUID target) {
        this.target = target;
    }

    public TpaType getTpaType() {
        return tpaType;
    }

    public void setTpaType(TpaType tpaType) {
        this.tpaType = tpaType;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.source);
        hash = 97 * hash + Objects.hashCode(this.target);
        hash = 97 * hash + Objects.hashCode(this.tpaType);
        hash = 97 * hash + Objects.hashCode(this.expiry);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TpaRequest other = (TpaRequest) obj;
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        if (!Objects.equals(this.target, other.target)) {
            return false;
        }
        if (this.tpaType != other.tpaType) {
            return false;
        }
        if (!Objects.equals(this.expiry, other.expiry)) {
            return false;
        }
        return true;
    }

}
