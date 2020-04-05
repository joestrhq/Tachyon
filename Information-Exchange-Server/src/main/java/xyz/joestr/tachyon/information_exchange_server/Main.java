package xyz.joestr.tachyon.information_exchange_server;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.util.standalone.VoteReceiver;
import com.vexsoftware.votifier.util.standalone.VotifierServerBuilder;
import xyz.joestr.tachyon.information_exchange_server.configurations.YamlConfiguration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.accesslog.AccessLogBuilder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.yaml.snakeyaml.Yaml;
import xyz.joestr.tachyon.information_exchange_server.managers.PlayerSettingsManager;

public class Main {
    
    public static final Main INSTANCE = new Main();
    
    // This is the logger for this whole programm
    public static final Logger LOGGER =
        Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler");
    
    public static YamlConfiguration CONFIGURTION;
    
    // pooled database connection
    private PooledDatabaseConnection pooledDatabaseConnection;
    
    private HttpServer httpServer;
    private AccessLogBuilder accessLogBuilder;
    
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
        File hikariConfigFile = new File(selfJar.getParent(), "config-hikari.properties");
        
        File privateKeyFile = new File(selfJar.getParent(), "votifier-privatekey.pem");
        File publicKeyFile = new File(selfJar.getParent(), "votifier-publickey.pem");
        
        if(!configFile.exists()) {
            if(!configFile.getParentFile().canWrite()) {
                return;
            }
            
            OutputStream oS = new FileOutputStream(configFile);
            InputStream iS = INSTANCE.getClass().getResourceAsStream("config.yml");

            byte[] buffer = new byte[1024];
            int bytesRead;

            while((bytesRead = iS.read(buffer)) != -1)
                oS.write(buffer, 0, bytesRead);

            iS.close();
            oS.flush();
            oS.close();
        }
        
        if(!configFile.canRead()) {
            return;
        }
        
        CONFIGURTION = new Yaml().loadAs(
            new FileReader(configFile),
            YamlConfiguration.class
        );
        
        if(!hikariConfigFile.exists()) {
            if(!hikariConfigFile.getParentFile().canWrite()) {
                return;
            }
            
            OutputStream oS = new FileOutputStream(hikariConfigFile);
            InputStream iS = INSTANCE.getClass().getResourceAsStream("config-hikari.properties");

            byte[] buffer = new byte[1024];
            int bytesRead;

            while((bytesRead = iS.read(buffer)) != -1)
                oS.write(buffer, 0, bytesRead);

            iS.close();
            oS.flush();
            oS.close();
        }
        
        if(!hikariConfigFile.canRead()) {
            return;
        }
        
        PooledDatabaseConnection.getInstance(
            new HikariConfig(hikariConfigFile.getAbsolutePath())
        );
        
        PrivateKey loadedPrivateKey;
        PublicKey loadedPublicKey;
        
        KeyPairGenerator kPG = KeyPairGenerator.getInstance("RSA");
        kPG.initialize(2048);
        KeyPair generatedKeyPair = kPG.generateKeyPair();
        
        if(!privateKeyFile.exists()) {
            if(!privateKeyFile.getParentFile().canWrite()) {
                return;
            }
            
            INSTANCE.savePrivateKey(
                privateKeyFile,
                generatedKeyPair.getPrivate()
            );
        }
        
        if(!privateKeyFile.canRead()) {
            return;
        }
        
        loadedPrivateKey = INSTANCE.loadPrivateKey(privateKeyFile, "RSA");
        
        if(!publicKeyFile.exists()) {
            if(!publicKeyFile.getParentFile().canWrite()) {
                return;
            }
            
            INSTANCE.savePublicKey(
                publicKeyFile,
                generatedKeyPair.getPublic()
            );
        }
        
        if(!publicKeyFile.canRead()) {
            return;
        }
        
        loadedPublicKey = INSTANCE.loadPublicKey(publicKeyFile, "RSA");
        
        PlayerSettingsManager.getInstance(
            CONFIGURTION,
            INSTANCE.pooledDatabaseConnection
        );
        
        new VotifierServerBuilder()
            .bind(new InetSocketAddress("0.0.0.0", 8019))
            .v1Key(new KeyPair(loadedPublicKey, loadedPrivateKey))
            .receiver(
                new VoteReceiver() {
                    @Override
                    public void onVote(Vote vote) throws Exception {
                        
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        
                    }
                    
                })
            .create()
            .start();

        INSTANCE.httpServer = GrizzlyHttpServerFactory.createHttpServer(
            URI.create(CONFIGURTION.getListenuri()),
            new ResourceConfig()
                .packages("xyz.joestr.tachyon.information_exchange_server.web.commonv1"),
            false
        );
        
        INSTANCE.accessLogBuilder = new AccessLogBuilder("access.log");
        INSTANCE.accessLogBuilder.rotatedHourly();
        INSTANCE.accessLogBuilder.instrument(INSTANCE.httpServer.getServerConfiguration());
        
        try {
            INSTANCE.httpServer.start();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Writes a private key with encoding
     * @param file The file where the key will be saved
     * @param privateKey The private key
     * @throws FileNotFoundException If the file was not found
     * @throws IOException If an I/O error occours
     */
    private void savePrivateKey(File file, PrivateKey privateKey) throws FileNotFoundException, IOException {
        PKCS8EncodedKeySpec privateKeySpec =
            new PKCS8EncodedKeySpec(
				privateKey.getEncoded()
            );
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(privateKeySpec.getEncoded());
		fos.close();
    }
    
    /**
     * Writes a public key with encoding
     * @param file The file where the key will be saved
     * @param publicKey The public key
     * @throws FileNotFoundException If the file was not found
     * @throws IOException If an I/O error occours
     */
    private void savePublicKey(File file, PublicKey publicKey) throws FileNotFoundException, IOException {
        X509EncodedKeySpec publicKeySpec =
            new X509EncodedKeySpec(
				publicKey.getEncoded()
            );
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(publicKeySpec.getEncoded());
		fos.close();
    }
    
    /**
     * Reads a private key with encoding
     * @param file The file where the key will be read from
     * @param algorithm The algorithm of the key
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     */
    private PrivateKey loadPrivateKey(File file, String algorithm) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File filePublicKey = file;
		FileInputStream fis = new FileInputStream(file);
		byte[] encodedPrivateKey = new byte[(int) filePublicKey.length()];
		fis.read(encodedPrivateKey);
		fis.close();
        
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PKCS8EncodedKeySpec privateKeySpec =
            new PKCS8EncodedKeySpec(
                encodedPrivateKey
            );
        
		return keyFactory.generatePrivate(privateKeySpec);
    }
    
    /**
     * Reads a private key with encoding
     * @param file The file where the key will be read from
     * @param algorithm The algorithm of the key
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     */
    private PublicKey loadPublicKey(File file, String algorithm) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File filePublicKey = file;
		FileInputStream fis = new FileInputStream(file);
		byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
		fis.read(encodedPublicKey);
		fis.close();
        
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        X509EncodedKeySpec publicKeySpec =
            new X509EncodedKeySpec(
                encodedPublicKey
            );
        
		return keyFactory.generatePublic(publicKeySpec);
    }
}
