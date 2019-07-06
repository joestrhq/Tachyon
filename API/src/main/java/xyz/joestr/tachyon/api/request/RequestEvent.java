/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.api.request;

/**
 * Represents a event which can get called, registered and unregisters by or
 * from the {@link xyz.joestr.tachyon.api.request.RequestManager}.
 * 
 * @author Joel
 * @param <T> The extended {@link xyz.joestr.tachyon.api.request.Request} to
 * listen at.
 */
public interface RequestEvent<T extends Request> {

    public void onRequestReceived(Request request);
}
