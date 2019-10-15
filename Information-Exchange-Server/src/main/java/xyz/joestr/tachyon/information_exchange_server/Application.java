package xyz.joestr.tachyon.information_exchange_server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.Yaml;

@SpringBootApplication
public class Application {
    
    public static final Logger LOGGER = Logger.getLogger("Tachyon - IES");
    
    private File localConfigurationFile;
    private File externalConfigurationFile;
    private Yaml configuration;
    
    public void main(String[] args) throws URISyntaxException {
        
        // Get the config.yml from the jar file
        this.localConfigurationFile  = new File(
            this.getClass().getResource("config.yml").toURI()
        );
        // Get the config.yml from the directory where the jar itself is
        this.externalConfigurationFile = new File(
            this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "config.yml"
        );
        
        // Check if the config from the outside world exists
        if(this.externalConfigurationFile.exists()) {
            // Check if we can read this file
            if(this.externalConfigurationFile.canRead()) {
                // Let's try to load 
                try {
                    this.loadConfiguration();
                } catch (FileNotFoundException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            } else {
                // If we are unable to read this file
                LOGGER.log(Level.WARNING, "File at '{0}' is not readable!", this.externalConfigurationFile.getAbsolutePath());
                LOGGER.log(Level.SEVERE, "Configuration error!");
                return;
            }
        } else {
            // Let's check for rights to write
            if(this.externalConfigurationFile.canWrite()) {
                // Trying to reading from the local and writing to the external file
                try {
                    InputStream initialStream = new FileInputStream(this.localConfigurationFile);
                    byte[] buffer = new byte[initialStream.available()];
                    initialStream.read(buffer);
                    
                    OutputStream outStream = new FileOutputStream(this.externalConfigurationFile);
                    outStream.write(buffer);
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                    LOGGER.log(Level.SEVERE, "Configuration error!");
                    return;
                }
                
                // Check if we able to read the freshly written file again 
                if(this.externalConfigurationFile.canRead()) {
                    try {
                        this.loadConfiguration();
                    } catch (FileNotFoundException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                } else {
                    LOGGER.log(Level.WARNING, "File at '{0}' is not readable!", this.externalConfigurationFile.getAbsolutePath());
                    LOGGER.log(Level.SEVERE, "Configuration error!");
                    return;
                }
            } else {
                // We are here beacuse we cannot read and write to the file
                LOGGER.log(Level.WARNING, "File at '{0}' is not writeable!", this.externalConfigurationFile.getAbsolutePath());
                LOGGER.log(Level.SEVERE, "Configuration error!");
                return;
            }
        }
        
        SpringApplication.run(Application.class, args);
    }
    /**
     * Reads the external configuration file.
     * @throws FileNotFoundException If the file was not found.
     */
    private void loadConfiguration() throws FileNotFoundException {
        InputStream input = new FileInputStream(this.externalConfigurationFile);
        this.configuration.load(input);
    }
}
