/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.information_exchange_server.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Joel
 */
@Controller
public class NotificationController {
    
    @MessageMapping("/notification")
    @SendTo("/subscribe/notification")
    public String greeting(String message) throws Exception {
        return message;
    }

}
