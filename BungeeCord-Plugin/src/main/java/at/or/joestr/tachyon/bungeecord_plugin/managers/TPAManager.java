/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.joestr.tachyon.bungeecord_plugin.managers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import at.or.joestr.tachyon.bungeecord_plugin.utils.TpaRequest;
import at.or.joestr.tachyon.bungeecord_plugin.utils.TpaType;

/**
 * Handles TPA requests of players.
 * 
 * @author Joel
 */
public class TPAManager {

    private static TPAManager instance = null;

    private Collection<TpaRequest> tpaRequests = null;

    /**
     * Private constrcutor for singelton
     */
    private TPAManager() {

        this.tpaRequests = new HashSet<>();
    }

    /**
     * Get an instance of this class.
     *
     * @return The instance of this class
     */
    public static TPAManager getInstance() {

        if (instance == null) {
            instance = new TPAManager();
        }

        return instance;
    }

    /**
     * Creates a new request built up from the given parameters.
     *
     * @param source The UUID of the source
     * @param target The UUID of the target
     * @param tpaType The type of this request
     * @param expiry When the request should expire
     * @return {@code true} if the request could be added, otherwise {@code false}
     */
    public boolean createRequest(UUID source, UUID target, TpaType tpaType, LocalDateTime expiry) {

        this.filterOldRequests();

        TpaRequest tpaRequestToAdd = new TpaRequest(source, target, tpaType, expiry);

        return this.tpaRequests.add(tpaRequestToAdd);
    }

    public boolean addRequest(TpaRequest tpaRequest) {

        this.filterOldRequests();

        return this.tpaRequests.add(tpaRequest);
    }

    /**
     * Check if an UUID has an outstanding request. (This method calls
     * {@link #filterOldRequests()} before filtering againt the UUID of the
     * source.)
     *
     * @param uuid The UUID of the source.
     * @return {@code true} if there is an open request, otherwise {@code false}
     */
    public boolean hasOutstandingRequest(UUID uuid) {

        this.filterOldRequests();

        return this.tpaRequests
            .stream()
            .anyMatch(
                tpaRequest -> tpaRequest.getSource().equals(uuid)
            );
    }

    /**
     * Get the oustanding request of an UUID. (This method calls
     * {@link #filterOldRequests()} before filtering againt the UUID of the
     * source.)
     *
     * @param uuid The UUID of the source
     * @return An optional. You have to check yourself if it is present or not.
     */
    public Optional<TpaRequest> getOutstandingRequest(UUID uuid) {
        return this.tpaRequests
            .stream()
            .filter(
                tpaRequest -> tpaRequest.getSource().equals(uuid)
            )
            .findFirst();
    }

    /**
     * Filter expired requests from the set of requests.
     *
     * This method is <b>synchronized</b> to prevent asynchronous computation
     * errors. (Thats means there is only the possibility of a one by one call
     * of this method.)
     */
    synchronized public void filterOldRequests() {
        this.tpaRequests.removeIf(
            tpaRequest -> tpaRequest.getExpiry().isBefore(LocalDateTime.now())
        );
    }
}
