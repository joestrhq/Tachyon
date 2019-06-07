/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.tachyon_bungeecord.utils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Is a teleport request for players. Used in the {@link xyz.joestr.tachyon.tachyon_bungeecord.managers.TPAManager} for example.
 * 
 * @author Joel
 */
public class TPARequest {

    private UUID source;
    private UUID target;
    private TPAType tpaType;
    private LocalDateTime expiry;

    public TPARequest(UUID source, UUID target, TPAType tpaType, LocalDateTime expiry) {
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

    public TPAType getTpaType() {
        return tpaType;
    }

    public void setTpaType(TPAType tpaType) {
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
        final TPARequest other = (TPARequest) obj;
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
