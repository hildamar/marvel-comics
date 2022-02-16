package com.albo.biblioteca.comics.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="\"character\"")
public class Character {

  @Id
  @Column(name="\"id\"",nullable = false)
  private Long id;

  @Column(name="\"name\"")
  private String name;

  @Column(name="\"character_related\"")
  private String characterRelated;

  @Column(name="\"last_sync\"")
  private Date last_sync;

  public Character() {

  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getLast_sync() {
    return last_sync;
  }

  public void setLast_sync(Date last_sync) {
    this.last_sync = last_sync;
  }

  public String getCharacterRelated() {
    return characterRelated;
  }

  public void setCharacterRelated(String characterRelated) {
    this.characterRelated = characterRelated;
  }
}
