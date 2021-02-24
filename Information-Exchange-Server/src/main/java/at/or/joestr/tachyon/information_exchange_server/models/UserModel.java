/*
Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
 */
package at.or.joestr.tachyon.information_exchange_server.models;

import io.ebean.Model;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Joel
 */
@Entity
public class UserModel extends Model implements Serializable {
  
  @Id
  UUID uuid;
  
  String name;

  public UserModel() {
  }

  public UserModel(String name) {
    super(name);
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
