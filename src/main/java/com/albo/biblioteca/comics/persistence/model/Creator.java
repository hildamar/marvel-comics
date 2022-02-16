package com.albo.biblioteca.comics.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="creator")
public class Creator {

  @Id
  @Column(name="\"id\"",nullable = false)
  private Long id;

  @Column(name="\"nombre\"")
  private String nombre;

  @Column(name="\"last_sync\"")
  private Date last_sync;

  @Column(name="\"role\"")
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


  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Date getLast_sync() {
    return last_sync;
  }

  public void setLast_sync(Date last_sync) {
    this.last_sync = last_sync;
  }
}

