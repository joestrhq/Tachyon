/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.information_exchange_server.classes;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

/**
 *
 * @author Joel
 */
public class User {
    
    UUID uniqueID;
    String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
        this.uniqueID = UUID.randomUUID();
    }

    public User(UUID uniqueID, String name) {
        this.uniqueID = uniqueID;
        this.name = name;
    }
    
    public UUID getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(UUID uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.uniqueID);
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
        final User other = (User) obj;
        if (!Objects.equals(this.uniqueID, other.uniqueID)) {
            return false;
        }
        return true;
    }
}
