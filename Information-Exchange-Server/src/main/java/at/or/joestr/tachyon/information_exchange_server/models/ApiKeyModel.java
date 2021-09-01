// 
// Private License
// 
// Copyright (c) 2019-2020 Joel Strasser
// 
// Only the owner is allowed to use this software.
// 
package at.or.joestr.tachyon.information_exchange_server.models;

import io.ebean.Model;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author joestr
 */
@Entity
public class ApiKeyModel extends Model implements Serializable {
  
  @Id
  UUID id;
  
  @NotNull
  String category;

  public ApiKeyModel() {
  }

  public ApiKeyModel(String category) {
    this.category = category;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }
}
