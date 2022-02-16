package com.albo.biblioteca.comics.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Embeddable
@Table(name="comic_character")
public class ComicCharacter {


  @Id
  @Column(name="\"id\"",nullable = false)
  private Long id;

  @JoinColumn(name ="\"idcomic\"", referencedColumnName = "\"id\"")
  @ManyToOne
  @JsonIgnoreProperties(value = "characters")
  private Comic comic;

  @ManyToOne
  @JoinColumn(name ="\"idcharacter\"")
  private Character character;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Comic getComic() {
    return comic;
  }

  public void setComic(Comic comic) {
    this.comic = comic;
  }

  public Character getCharacter() {
    return character;
  }

  public void setCharacter(Character character) {
    this.character = character;
  }
}
