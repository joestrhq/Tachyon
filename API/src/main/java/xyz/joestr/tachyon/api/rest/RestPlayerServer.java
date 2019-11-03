/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.api.rest;

import java.util.Objects;

/**
 *
 * @author Joel
 */
public class RestPlayerServer {
    private String server;

    public RestPlayerServer(String server) {
        this.server = server;
    }

    public RestPlayerServer() {
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    @Override
    public String toString() {
        return "RestPlayerServer{" + "server=" + server + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.server);
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
        final RestPlayerServer other = (RestPlayerServer) obj;
        if (!Objects.equals(this.server, other.server)) {
            return false;
        }
        return true;
    }
}
