package xyz.joestr.tachyon.information_exchange_server;

import java.io.File;
import java.net.URISyntaxException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.Yaml;

@SpringBootApplication
public class Application {

    private File localCOnfigurationFile;
    private File externalConfigurationFile;
    private Yaml configuration;
    
    public void main(String[] args) throws URISyntaxException {
        
        this.localCOnfigurationFile  = new File(this.getClass().getResource("config.yml").toURI());
        this.externalConfigurationFile = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "config.yml");
        
        
        
        SpringApplication.run(Application.class, args);
    }
}
