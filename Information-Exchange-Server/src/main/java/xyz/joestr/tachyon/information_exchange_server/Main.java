package xyz.joestr.tachyon.information_exchange_server;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.util.standalone.VoteReceiver;
import com.vexsoftware.votifier.util.standalone.VotifierServerBuilder;
import com.zaxxer.hikari.HikariConfig;
import xyz.joestr.tachyon.information_exchange_server.configurations.YamlConfiguration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.server.accesslog.AccessLogBuilder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.yaml.snakeyaml.Yaml;

public class Main {
    
    public static final Main INSTANCE = new Main();
    
    // This is the logger for this whole programm
    public static final Logger LOGGER = Logger.getLogger("Tachyon - IES");
    
    // Variables for the configuration
    private InputStream localConfigurationFileInputStream;
    private File externalConfigurationFile;
    private Yaml yaml;
    private YamlConfiguration yamlConfiguration;
    
    // The variables for votifier
    private File externalPrivateKeyFile;
    private File externalPublicKeyFile;
    private KeyPair votifierKeyPair;
    
    // The server for the REST endpoints
    private HttpServer httpServer;
    private AccessLogBuilder accessLogBuilder;
    
    // The votifier server
    private VotifierServerBuilder votifierServerBuilder;
    
    public static void main(String[] args) throws URISyntaxException, NoSuchAlgorithmException, NoSuchProviderException, IOException, FileNotFoundException, InvalidKeySpecException, InvalidKeySpecException, InvalidKeySpecException, Throwable {
        
        //<editor-fold defaultstate="collapsed" desc="Fill variables">
        
        // Get the config.yml from the jar file
        INSTANCE.localConfigurationFileInputStream =
            INSTANCE.getClass().getResourceAsStream("config.yml");
        
        // The jar-file
        File selfJar = new File(
            INSTANCE.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
        );
        
        // Get the config.yml from the directory where the jar itself is
        INSTANCE.externalConfigurationFile = new File(
            selfJar.getParent(), "config.yml"
        );
        
        INSTANCE.externalPrivateKeyFile = new File(
            selfJar.getParent(), "votifier.key"
        );
        
        INSTANCE.externalPublicKeyFile = new File(
            selfJar.getParent(), "votifier.key.pub"
        );
        
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Configuration file logic">
        
        // Check if the config from the outside world exists
        if(INSTANCE.externalConfigurationFile.exists()) {
            // Check if we can read this file
            if(INSTANCE.externalConfigurationFile.canRead()) {
                // Let's try to load
                try {
                    INSTANCE.loadConfiguration();
                } catch (FileNotFoundException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            } else {
                // If we are unable to read this file
                LOGGER.log(Level.WARNING,
                    "File at {0} is not readable!",
                    INSTANCE.externalConfigurationFile.getAbsolutePath()
                );
                LOGGER.log(Level.SEVERE, "Configuration error!");
                return;
            }
        } else {
            // Let's check for rights to write
            if(INSTANCE.externalConfigurationFile.getParentFile().canWrite()) {
                // Trying to reading from the local and writing to the external file
                try {
                    InputStream initialStream =
                        INSTANCE.localConfigurationFileInputStream;
                    
                    byte[] buffer = new byte[initialStream.available()];
                    initialStream.read(buffer);
                    
                    OutputStream outStream = new FileOutputStream(
                        INSTANCE.externalConfigurationFile
                    );
                    outStream.write(buffer);
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                    LOGGER.log(Level.SEVERE, "Configuration error!");
                    return;
                }
                
                // Check if we able to read the freshly written file again
                if(INSTANCE.externalConfigurationFile.canRead()) {
                    try {
                        INSTANCE.loadConfiguration();
                    } catch (FileNotFoundException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                } else {
                    LOGGER.log(Level.WARNING,
                        "File at {0} is not readable!",
                        INSTANCE.externalConfigurationFile.getAbsolutePath()
                    );
                    LOGGER.log(Level.SEVERE, "Configuration error!");
                    return;
                }
            } else {
                // We are here beacuse we cannot read and write to the file
                LOGGER.log(Level.WARNING,
                    "File at {0} is not writeable!",
                    INSTANCE.externalConfigurationFile.getAbsolutePath()
                );
                LOGGER.log(Level.SEVERE, "Configuration error!");
                return;
            }
        }
        
        //</editor-fold>
        
        PrivateKey loadedPrivateKey = null;
        PublicKey loadedPublicKey = null;
        KeyPairGenerator kPG = KeyPairGenerator.getInstance("RSA");
        kPG.initialize(2048);
        KeyPair generatedKeyPair = kPG.generateKeyPair();
        
        
        if(INSTANCE.externalPrivateKeyFile.exists()) {
            if(INSTANCE.externalPrivateKeyFile.canRead()) {
                loadedPrivateKey = INSTANCE.loadPrivateKey(INSTANCE.externalPrivateKeyFile,
                    "RSA"
                );
            } else {
                
            }
        } else {
            if(INSTANCE.externalPrivateKeyFile.getParentFile().canWrite()) {
                
                INSTANCE.savePrivateKey(INSTANCE.externalPrivateKeyFile,
                    generatedKeyPair.getPrivate()
                );
                
                if(INSTANCE.externalPrivateKeyFile.canRead()) {
                
                    loadedPrivateKey = INSTANCE.loadPrivateKey(INSTANCE.externalPrivateKeyFile,
                        "RSA"
                    );
                    
                } else {

                }
            } else {
                
            }
        }
        
        if(INSTANCE.externalPublicKeyFile.exists()) {
            if(INSTANCE.externalPublicKeyFile.canRead()) {
                loadedPublicKey = INSTANCE.loadPublicKey(INSTANCE.externalPublicKeyFile,
                    "RSA"
                );
            } else {
                
            }
        } else {
            if(INSTANCE.externalPublicKeyFile.getParentFile().canWrite()) {
                
                INSTANCE.savePublicKey(INSTANCE.externalPublicKeyFile,
                    generatedKeyPair.getPublic()
                );
                
                if(INSTANCE.externalPublicKeyFile.canRead()) {
                    loadedPublicKey = INSTANCE.loadPublicKey(INSTANCE.externalPublicKeyFile,
                        "RSA"
                    );
                } else {

                }
            } else {
                
            }
        }
        
        INSTANCE.votifierKeyPair = new KeyPair(loadedPublicKey, loadedPrivateKey);
        
        INSTANCE.votifierServerBuilder = new VotifierServerBuilder();
        INSTANCE.votifierServerBuilder
            .bind(new InetSocketAddress("0.0.0.0", 8019))
            .v1Key(INSTANCE.votifierKeyPair)
            .receiver(
                new VoteReceiver() {
                    @Override
                    public void onVote(Vote vote) throws Exception {
                        
                    }

                    @Override
                    public void onException(Throwable thrwbl) {
                        
                    }

            })
            .create()
            .start();

        final ResourceConfig rc = new ResourceConfig().packages("xyz.joestr.tachyon.information_exchange_server.rest");
        INSTANCE.httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:59000/"), rc, false);
        
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
     * Reads the external yaml file.
     * @throws FileNotFoundException If the file was not found.
     */
    private void loadConfiguration() throws FileNotFoundException {
        InputStream input = new FileInputStream(
            this.externalConfigurationFile
        );
        
        this.yaml = new Yaml();
        
        this.yamlConfiguration = this.yaml.loadAs(
            input,
            YamlConfiguration.class
        );
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
