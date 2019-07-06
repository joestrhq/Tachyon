/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.api.request;

import com.google.gson.Gson;

/**
 * This class represents a request which can be sent and received by and from
 * the {@link xyz.joestr.tachyon.api.request.RequestManager}.<br>
 * A class which extends this class can be parameterized to the
 * {@link xyz.joestr.tachyon.api.request.RequestEvent}.
 * 
 * @author Joel
 */
public abstract class Request {
    
    /**
     * Transforms a this request into JSON string for, obvious, better and
     * easier transport.
     * 
     * @return A JSON string representation of this request.
     */
    public String convertRequestToString() {
        return new Gson().toJson(this);
    }
    
    /**
     * Transforms a JSON string representation of a request into a request
     * again.
     * 
     * @param string The JSON representation of a request.
     * @return 
     */
    public Request convertStringToRequest(String string) {
        return new Gson().fromJson(string, this.getClass());
    }
}
