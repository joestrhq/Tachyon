/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.information_exchange_server.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Joel
 */
@RestController
public class ServerTPSController {
    @RequestMapping(
        value = "/server/{servername}/settings",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @ResponseBody
    public String getTPS(/*@RequestHeader("authorization") String bearerToken,*/ @PathVariable String servername) {
        return "20";
    }
}
