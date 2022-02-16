package com.albo.biblioteca.comics.api.vo;

import java.util.Date;

/**
 * @author Hildamar Parra
 */
public class CreatorVO {

  private Long id;
  private String nombre;
  private Date last_sync;
  private String role;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Date getLast_sync() {
    return last_sync;
  }

  public void setLast_sync(Date last_sync) {
    this.last_sync = last_sync;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
