/*
Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
 */
package xyz.joestr.tachyon.bungeecord_plugin.configuration;

import com.sun.istack.internal.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Joel
 */
public abstract class YamlConfiguration {
  
  Map<Object, Object> config;
  
  public YamlConfiguration() {
  }
  
  private Map<Object, Object> lookupMap(String path, Map<Object, Object> configMap) {
    Map<Object, Object> subMap = new HashMap<>(configMap);
    
    String[] splitted = path.split(".");
    
    for (int i = 0; i < splitted.length - 1; i++) {
      if(!subMap.containsKey(splitted[i])) {
        return null;
      } else {
        subMap =
          (Map<Object, Object>) subMap.get(splitted[i]);
      }
    }
    
    return subMap;
  }
  
  private Object lookupObject(String path, Map<Object, Object> configMap) {
    Map<Object, Object> subMap = new HashMap<>(configMap);
    
    String[] splitted = path.split(".");
    
    for (int i = 0; i < splitted.length - 1; i++) {
      if(!subMap.containsKey(splitted[i])) {
        return null;
      } else {
        subMap =
          (Map<Object, Object>) subMap.get(splitted[i]);
      }
    }
    
    return subMap.get(splitted[splitted.length]);
  }
  
  private <T> T getObject(String path, Class<? extends T> clazz) {
    Object result = this.lookupObject(path, config);
    
    if (result != null) {
      return clazz.cast(result);
    }
    
    return null;
  }
  
  private <T> void setObject(String path, Class<? extends T> clazz, T value) {
    String last = path.split(".")[path.split(".").length - 1];
    Map<Object, Object> result = lookupMap(path, this.config);
    result.put(last, clazz.cast(value));
  }
  
  public boolean contains(String path) {
    return this.lookupObject(path, config) != null;
  }
  
  @Nullable
  public Integer getInteger(String path) {
    return getObject(path, Integer.class);
  }
  
  public void setInteger(String path, Integer value) {
    setObject(path, Integer.class, value);
  }
  
  @Nullable
  public Double getDouble(String path) {
    return getObject(path, Double.class);
  }
  
  public void setDouble(String path, Double value) {
    setObject(path, Double.class, value);
  }
  
  @Nullable
  public Float getFloat(String path) {
    return getObject(path, Float.class);
  }
  
  public void setFloat(String path, Float value) {
    setObject(path, Float.class, value);
  }
  
  @Nullable
  public String getString(String path) {
    return getObject(path, String.class);
  }
  
  public void setString(String path, String value) {
    setObject(path, String.class, value);
  }
  
  @Nullable
  public List<Integer> getIntegerList(String path) {
    return getObject(path, new ArrayList<Integer>().getClass());
  }
  
  public void setIntegerList(String path, List<Integer> value) {
    setObject(path, value.getClass(), value);
  }
  
  @Nullable
  public List<Double> getDoubleList(String path) {
    return getObject(path, new ArrayList<Double>().getClass());
  }
  
  public void setDoubleList(String path, List<Double> value) {
    setObject(path, value.getClass(), value);
  }
  
  @Nullable
  public List<Float> getFloatList(String path) {
    return getObject(path, new ArrayList<Float>().getClass());
  }
  
  public void setFloatList(String path, List<Float> value) {
    setObject(path, value.getClass(), value);
  }
  
  @Nullable
  public List<String> getStringList(String path) {
    return getObject(path, new ArrayList<String>().getClass());
  }
  
  public void setStringList(String path, List<String> value) {
    setObject(path, value.getClass(), value);
  }
  
  @Nullable
  public Map<Object, Integer> getIntegerMap(String path) {
    return getObject(path, new HashMap<Object, Integer>().getClass());
  }
  
  public void setIntegerMap(String path, Map<Object, Integer> value) {
    setObject(path, value.getClass(), value);
  }
  
  @Nullable
  public Map<Object, Double> getDoubleMap(String path) {
    return getObject(path, new HashMap<Object, Double>().getClass());
  }
  
  public void setDoubleMap(String path, Map<Object, Double> value) {
    setObject(path, value.getClass(), value);
  }
  
  @Nullable
  public Map<Object, Float> getFloatMap(String path) {
    return getObject(path, new HashMap<Object, Float>().getClass());
  }
  
  public void setFloatMap(String path, Map<Object, Float> value) {
    setObject(path, value.getClass(), value);
  }
  
  @Nullable
  public Map<Object, String> getStringMap(String path) {
    return getObject(path, new HashMap<Object, String>().getClass());
  }
  
  public void setStringMap(String path, Map<Object, String> value) {
    setObject(path, value.getClass(), value);
  }
  
  @Nullable
  public <T> T getCustomObject(String path, Class<T> claszz) {
    return getObject(path, claszz);
  }
  
  public <T> void setCustomObject(String path, Class<T> value) {
    setObject(path, value.getClass(), value);
  }
}
