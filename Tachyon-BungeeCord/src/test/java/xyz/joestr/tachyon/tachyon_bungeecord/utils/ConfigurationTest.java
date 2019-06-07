/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.tachyon_bungeecord.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import junit.framework.Assert;
import net.md_5.bungee.api.chat.TextComponent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Joel
 */
public class ConfigurationTest {
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    
    @Test
    public void testTextComponentFromLegacyText() {
        
        /*
        // we have to use PowerMockito.mock because .getPlayer() is a final method
        PlayerJoinEvent mockEvent = PowerMockito.mock(PlayerJoinEvent.class);
        
        // we are not calling any final methods on this class so can just use Mockitos normal mock
        Player mockPlayerPandarr = mock(Player.class);

        // effectively set the player name to "Pandarr"
        when(mockPlayerPandarr.getName()).thenReturn("Pandarr");

        // when our onPlayerJoin calls .getPlayer(), we want to return this mockPlayer
        when(mockEvent.getPlayer()).thenReturn(mockPlayerPandarr);

        // create our listener
        PTPlayerListener myPlayerListener = new PTPlayerListener();
        // send in our mock event
        myPlayerListener.onPlayerJoin(mockEvent);

        // verify that our mockPlayer had sendMessage called
        //   we don't care what the string was
        verify(mockPlayerPandarr).sendMessage(anyString());
        
        // we are not calling any final methods on this class so can just use Mockitos normal mock
        Player mockPlayerCodenameB = mock(Player.class);

        // effectively set the player name to "CodenameB"
        when(mockPlayerCodenameB.getName()).thenReturn("CodenameB");

        // when our onPlayerJoin calls .getPlayer(), we want to return this mockPlayer
        when(mockEvent.getPlayer()).thenReturn(mockPlayerCodenameB);

        // send in our mock event
        myPlayerListener.onPlayerJoin(mockEvent);
        
        // verify we did not send this player a message
        verify(mockPlayerCodenameB, never()).sendMessage(anyString());
        */
        
        Assert.assertEquals("", DumpToString.dump(TextComponent.fromLegacyText("&7&6Testing")));
    }
}
