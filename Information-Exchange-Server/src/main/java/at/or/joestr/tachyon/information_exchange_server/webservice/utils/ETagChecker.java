/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.joestr.tachyon.information_exchange_server.webservice.utils;

/**
 *
 * @author Joel
 */
public class ETagChecker {
    
    public static boolean isCurrent(String receivedEtag, String currentEtag) {
        
        if(receivedEtag == null) {
            return false;
        }
        
        if(currentEtag == null) {
            return false;
        }
        
        return receivedEtag.equalsIgnoreCase(currentEtag);
    }
}
