package xyz.joestr.tachyon.information_exchange_server.controllers;

import xyz.joestr.tachyon.information_exchange_server.managers.SettingsManager;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.joestr.tachyon.api.settings.PlayerSettings;
import xyz.joestr.tachyon.information_exchange_server.classes.User;

@RestController
public class PlayersSettingsController {

    @RequestMapping(
        value = "/players/{uuid}/settings",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @ResponseBody
    public PlayerSettings getSettings(@RequestHeader("authorization") String bearerToken, @PathVariable String uuid) {
        System.out.println(bearerToken);
        return SettingsManager.of(new UUID(1, 1));
    }
    
    @RequestMapping(
        value = "/players/{uuid}/settings",
        method = RequestMethod.PUT,
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseBody
    public void post(@RequestHeader("authorization") String bearerToken, @PathVariable String uuid, @RequestBody PlayerSettings settings) {
        SettingsManager.of(UUID.fromString(uuid)).setSettings(settings);
    }
}
