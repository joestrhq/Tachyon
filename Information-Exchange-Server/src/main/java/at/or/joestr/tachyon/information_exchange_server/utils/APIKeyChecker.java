/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.joestr.tachyon.information_exchange_server.utils;

import java.sql.SQLException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import at.or.joestr.tachyon.information_exchange_server.managers.ApiKeyManager;

/**
 *
 * @author Joel
 */
public class APIKeyChecker {
    
    public static void isPermitted(String bearerToken, String category) {
        try {
            if(!ApiKeyManager
                .getInstance()
                .isPermitted(
                    bearerToken.split(" ")[1],
                    category
                )
            ) {
                throw new WebApplicationException(
                    "Unauthrized",
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
