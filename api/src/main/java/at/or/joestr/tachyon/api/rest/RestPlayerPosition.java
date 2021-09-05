/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.joestr.tachyon.api.rest;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Joel
 */
public class RestPlayerPosition {
    
    private String server;
    private UUID world;
    private double x, y, z;

    public RestPlayerPosition(String server, UUID world, double x, double y, double z) {
        this.server = server;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public RestPlayerPosition() {
    }
    
    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public UUID getWorld() {
        return world;
    }

    public void setWorld(UUID world) {
        this.world = world;
    }
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "RestPlayerPosition{" + "server=" + server + ", world=" + world + ", x=" + x + ", y=" + y + ", z=" + z + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.server);
        hash = 47 * hash + Objects.hashCode(this.world);
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
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
        final RestPlayerPosition other = (RestPlayerPosition) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
            return false;
        }
        if (!Objects.equals(this.server, other.server)) {
            return false;
        }
        if (!Objects.equals(this.world, other.world)) {
            return false;
        }
        return true;
    }
}
