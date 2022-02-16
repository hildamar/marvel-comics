package com.albo.biblioteca.comics.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="comic")
public class Comic {

  @Id
  @Column(name="\"id\"",nullable = false)
  private Long id;

  @Column(name="\"titulo\"")
  private String titulo;

  @Column(name="\"last_sync\"")
  private Date last_sync;

  @Column(name="\"hero\"")
  private String hero;

  public String getHero() {
    return hero;
  }

  public void setHero(String hero) {
    this.hero = hero;
  }




  public Comic() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Date getLast_sync() {
    return last_sync;
  }

  public void setLast_sync(Date last_sync) {
    this.last_sync = last_sync;
  }


}
