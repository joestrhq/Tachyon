/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.information_exchange_server.utils;

import java.sql.SQLException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import xyz.joestr.tachyon.information_exchange_server.managers.APIKeyManager;

/**
 *
 * @author Joel
 */
public class APIKeyChecker {
    
    public static void isPermitted(String bearerToken, String category) {
        try {
            if(!APIKeyManager
                .getInstance()
                .isPermitted(
                    bearerToken.split(" ")[1],
                    "players.coordinates.get"
                )
            ) {
                throw new WebApplicationException(
                    "Unauthrozied",
                    Response.Status.UNAUTHORIZED
                );
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(
                ex,
                Response.Status.INTERNAL_SERVER_ERROR
            );
        }
    }
}
