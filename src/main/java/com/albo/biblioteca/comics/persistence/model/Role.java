package com.albo.biblioteca.comics.persistence.model;

import javax.persistence.*;

@Entity
@Table(name="role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="\"id \"",nullable = false)
  private Long id;

  @Column(name="\"nombre\"")
  private String nombre;

  public Role(Long id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  public Role() {

  }

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
}
