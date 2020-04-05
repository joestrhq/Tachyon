/*
Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
 */
package xyz.joestr.tachyon.bungeecord_plugin.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joel
 */
public class LanguageConfiguration {
  
  private static LanguageConfiguration INSTANCE;
  private static final Logger LOG = Logger.getLogger(LanguageConfiguration.class.getName());
  
  public Map<Locale, YamlFileConfiguration> externalLanguageConfigurations;
  public Map<Locale, YamlFileConfiguration> bundledLanguageConfigurations;
  public Locale fallback;
  
  private LanguageConfiguration(File externalLanguages, File bundledLanguages, Locale fallback) throws FileNotFoundException {
    if (!externalLanguages.isDirectory()) {
      throw new RuntimeException("The external language folder is not a directory!");
    }
    if (!externalLanguages.canRead()) {
      throw new RuntimeException("The external language folder is not readable!");
    }
    
    for (File languageFile : externalLanguages.listFiles()) {
      String fileName = languageFile.getName();
      externalLanguageConfigurations.put(
        Locale.forLanguageTag(
          fileName.contains(".") ? fileName.split(".")[0] : fileName
        ),
        new YamlFileConfiguration(languageFile)
      );
    }
    
    for (File languageFile : bundledLanguages.listFiles()) {
      String fileName = languageFile.getName();
      externalLanguageConfigurations.put(
        Locale.forLanguageTag(
          fileName.contains(".") ? fileName.split(".")[0] : fileName
        ),
        new YamlFileConfiguration(languageFile)
      );
    }
    
    this.fallback = fallback;
  }
  
  public static LanguageConfiguration getInstance(File externalLanguages, File bundledLanguages, Locale fallback) throws FileNotFoundException {
    if (INSTANCE != null) {
      throw new RuntimeException("This class has already been instantiated!");
    }
    
    INSTANCE = new LanguageConfiguration(externalLanguages, bundledLanguages, fallback);
    
    return INSTANCE;
  }
  
  public static LanguageConfiguration getInstance() {
    if (INSTANCE == null) {
      throw new RuntimeException("This class has not been instantiated yet!");
    }
    
    return INSTANCE;
  }
  
  private void externalLangaugeNotFound(Locale locale) {
    LOG.log(
      Level.WARNING,
      "The external language {0} was not found!",
      new Object[] { locale.toLanguageTag() }
    );
  }
  
  private void pathNotInExternalLanguage(String path, Locale locale) {
    LOG.log(
      Level.WARNING,
      "The path {0} was not found in {1}!",
      new Object[] { path, this.externalLanguageConfigurations.get(locale) }
    );
  }
  
  public String getString(String path, Locale locale) {
    YamlFileConfiguration configFile =
      this.externalLanguageConfigurations.get(locale);
    if (configFile != null) {
      String result =
        configFile.getString(path);
      if (result != null) {
        return result;
      } else {
        this.pathNotInExternalLanguage(path, locale);
      }
    } else {
      this.externalLangaugeNotFound(locale);
    }
    configFile =
      this.bundledLanguageConfigurations.get(locale);
    if (configFile != null) {
      String result =
        configFile.getString(path);
      if (result != null) {
        return result;
      }
    }
    configFile =
      this.bundledLanguageConfigurations.get(fallback);
    if (configFile != null) {
      String result =
        configFile.getString(path);
      if (result != null) {
        return result;
      }
    }
    return null;
  }
}
