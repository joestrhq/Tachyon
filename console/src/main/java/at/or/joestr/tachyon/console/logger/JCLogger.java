/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.joestr.tachyon.console.logger;

import java.util.logging.Level;
import at.or.joestr.tachyon.console.console.Console;

/**
 *
 * @author Joel
 */
public class JCLogger {

    public static void info(String message) {

        Console.getInstance().sendMessage("[" + Level.INFO.getLocalizedName() + "] " + message);
    }

    public static void warning(String message) {

        Console.getInstance().sendMessage("[" + Level.WARNING.getLocalizedName() + "] " + message);
    }

    public static void error(String message) {

        Console.getInstance().sendMessage("[" + Level.SEVERE.getLocalizedName() + "] " + message);
    }
}
