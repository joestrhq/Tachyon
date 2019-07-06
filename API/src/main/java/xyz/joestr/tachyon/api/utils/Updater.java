/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.api.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Consumer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Updater {
    
    /**
     * The Path to the maven repo folder containing the different version folders
     * and the<br />
     * maven-metadata.xml file with the last slash
     */
    private String mavenUri; 
    private File updateFolder;
    private Integer autoCheckSeconds;
    private File donwloadedFile;

    private boolean updateAvailable = false;
    private URL downloadUri;

    private String latestString;
    private long latestTimestamp;
    private String artifactId;

    private volatile boolean isFetching = false;
    private volatile boolean hasDownloaded = false;

    public Thread delayCheckThread;
    
    private boolean downloadUpdate;
    
    public Updater(String mavenUri, File updateFolder, Integer autoCheckSeconds, File donwloadedFile) {
        this.mavenUri = mavenUri;
        this.updateFolder = updateFolder;
        this.autoCheckSeconds = autoCheckSeconds;
        this.donwloadedFile = donwloadedFile;

        this.delayCheckThread = new Thread(() -> {
            while (Thread.currentThread() == delayCheckThread) {
                
                check((updater_) -> {});
                
                try {
                    Thread.sleep(autoCheckSeconds);
                } catch (InterruptedException exc) {
                }
            }
        });

        if (this.autoCheckSeconds > 0)
            this.delayCheckThread.start();
    }

    /**
     * This method checks for updates
     * @param consumer
     */
    public void check(Consumer<Updater> consumer) {
        if (this.isFetching) { consumer.accept(this); return; }

        if (!this.updateAvailable) { consumer.accept(this); return; }
        
        if (this.downloadUpdate) { consumer.accept(this); return; }
        
        if (this.hasDownloaded) { consumer.accept(this); return; }

        this.isFetching = true;

        new Thread(() -> {
            fetchData();
        }).start();
        
        consumer.accept(this);
    }

    public boolean isFetching() {
        return this.isFetching;
    }
    
    public boolean updateAvailable() {
        return this.updateAvailable;
    }

    public boolean downloadUpdate() {
        return this.downloadUpdate;
    }

    public boolean downloadedUpdate() {
        return this.hasDownloaded;
    }

    public String latestVersion() {
        return this.latestString;
    }

    public String downloadUri() {
        return this.downloadUri.toString();
    }

    private void fetchData() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            String mainlineVersion = getMainlineVersion(documentBuilder.parse(this.mavenUri + "maven-metadata.xml"));
            
            readUpdateInfo(documentBuilder.parse(this.mavenUri + mainlineVersion + "/maven-metadata.xml"));

            // Now check the versions (add +0100 to latest)
           
            if (this.latestTimestamp + 3600000 > this.donwloadedFile.lastModified()) {
                this.updateAvailable = true;
                this.downloadUri = new URL(this.mavenUri + mainlineVersion + "/" + this.artifactId + "-"
                        + this.extractMainlineVersion(mainlineVersion) + "-" + latestString + ".jar");

                this.isFetching = false;
                this.check((updater_) -> {}); // Force re-call of method to output informations
                if (this.downloadUpdate()) {
                    this.downloadUpdateFile();
                }
            } else {
                this.updateAvailable = false;
                this.downloadUri = null;
            }
        } catch (IOException | ParseException | ParserConfigurationException | DOMException | SAXException exc) {
        }

        this.isFetching = false;
    }

    private void downloadUpdateFile() {
        if (!this.updateAvailable)
            return;

        try {
            URL url = this.downloadUri;
            URLConnection urlConnection = url.openConnection();

            this.updateFolder.mkdirs();

            InputStream inputStream = urlConnection.getInputStream();
            OutputStream outputStream = new FileOutputStream(new File(this.updateFolder, this.donwloadedFile.getName()));

            byte[] buffer = new byte[512];
            int read;

            while ((read = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, read);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

            this.hasDownloaded = true;
            this.check((updater_) -> {}); // Force re-call of method to output informations
        } catch (IOException exc) {
        }
    }

    private String getMainlineVersion(Document document) {
        Element metadata = (Element) document.getElementsByTagName("metadata").item(0);
        Element versioning = (Element) metadata.getElementsByTagName("versioning").item(0);
        Element versions = (Element) versioning.getElementsByTagName("versions").item(0);
        NodeList version = versions.getElementsByTagName("version");

        return version.item(version.getLength() - 1).getTextContent();
    }

    private String extractMainlineVersion(String mainlineVersion) {
        return mainlineVersion.replace("-RELEASE", "").replace("-SNAPSHOT", "");
    }

    private void readUpdateInfo(Document document) throws DOMException, ParseException {
        Element metadata = (Element) document.getElementsByTagName("metadata").item(0);
        Element versioning = (Element) metadata.getElementsByTagName("versioning").item(0);

        Element snapshot = (Element) versioning.getElementsByTagName("snapshot").item(0);
        Element lastUpdated = (Element) versioning.getElementsByTagName("lastUpdated").item(0);

        this.latestString = snapshot.getElementsByTagName("timestamp").item(0).getTextContent() + "-"
                + snapshot.getElementsByTagName("buildNumber").item(0).getTextContent();
        this.latestTimestamp = new SimpleDateFormat("yyyyMMddHHmmss").parse(lastUpdated.getTextContent()).getTime();
        this.artifactId = metadata.getElementsByTagName("artifactId").item(0).getTextContent();
    }
    
    private void downloadFile(URL url, File target) throws IOException {
        if (target.exists())
            target.delete();

        try (InputStream inputStream = url.openConnection().getInputStream(); OutputStream outputStream = new FileOutputStream(target)) {
            
            byte[] buffer = new byte[512];
            int read = 0;
            
            while ((read = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, read);
            }
            
            outputStream.flush();
        }
    }
}
