package at.or.joestr.tachyon.information_exchange_server;

import com.vexsoftware.votifier.util.standalone.VoteReceiver;
import com.vexsoftware.votifier.util.standalone.VotifierServerBuilder;
import at.or.joestr.tachyon.information_exchange_server.configurations.YamlConfiguration;
import at.or.joestr.tachyon.information_exchange_server.utils.RSAKeyUtil;
import com.vexsoftware.votifier.model.Vote;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import java.io.File;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;

public class Main {

  public static final Main INSTANCE = new Main();

  // This is the logger for this whole programm
  public static final Logger LOGGER
    = Logger.getLogger(Main.class.getCanonicalName());

  public static YamlConfiguration tCONFIGURATION;
  
  public static Database tDATABASE;
  
	// For the internal socket channel
	private AsynchronousServerSocketChannel internalServer;

  public static void main(String[] args) throws Throwable {
    
    // The jar file itself
    File selfJar = new File(
      INSTANCE.getClass()
        .getProtectionDomain()
        .getCodeSource()
        .getLocation()
        .getPath()
    );

    // The configuration files
    File configFile = new File(selfJar.getParent(), "config.yml");
    File databaseConfigFile = new File(selfJar.getParent(), "config-database.yml");
    File privateKeyFile = new File(selfJar.getParent(), "votifier-privatekey.pem");
    File publicKeyFile = new File(selfJar.getParent(), "votifier-publickey.pem");

    if (!configFile.exists()) {
			LOGGER.log(Level.WARNING, "{0} does not exists", configFile.getAbsolutePath());
			
      if (!configFile.getParentFile().canWrite()) {
				LOGGER.log(Level.SEVERE, "Cannot write to {0}", configFile.getAbsolutePath());
        return;
      }
			
			Files.copy(
				INSTANCE.getClass().getResourceAsStream("config.yml"),
				configFile.toPath(),
				StandardCopyOption.REPLACE_EXISTING
			);
    }

    if (!configFile.canRead()) {
			LOGGER.log(Level.SEVERE, "Cannot read from {0}", configFile.getAbsolutePath());
      return;
    }

    tCONFIGURATION = new Yaml().loadAs(new FileReader(configFile),YamlConfiguration.class);
    
    tDATABASE = DatabaseFactory.create(databaseConfigFile.getPath());

    PrivateKey loadedPrivateKey;
    PublicKey loadedPublicKey;

    KeyPairGenerator kPG = KeyPairGenerator.getInstance("RSA");
    kPG.initialize(2048);
    KeyPair generatedKeyPair = kPG.generateKeyPair();

    if (!privateKeyFile.exists()) {
      if (!privateKeyFile.getParentFile().canWrite())
        return;

      RSAKeyUtil.savePrivateKey(privateKeyFile, generatedKeyPair.getPrivate());
    }

    if (!privateKeyFile.canRead())
      return;

    loadedPrivateKey = RSAKeyUtil.loadPrivateKey(privateKeyFile, "RSA");

    if (!publicKeyFile.exists()) {
      if (!publicKeyFile.getParentFile().canWrite())
        return;

      RSAKeyUtil.savePublicKey(publicKeyFile, generatedKeyPair.getPublic());
    }

    if (!publicKeyFile.canRead()) {
      return;
    }

    loadedPublicKey = RSAKeyUtil.loadPublicKey(publicKeyFile, "RSA");

    new VotifierServerBuilder()
      .bind(new InetSocketAddress("0.0.0.0", 8019))
      .v1Key(new KeyPair(loadedPublicKey, loadedPrivateKey))
      .receiver(new VoteReceiver(){
        @Override
        public void onVote(Vote vote) throws Exception {
          throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onException(Throwable thrwbl) {
          throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
      }).create().start();
  }
}
