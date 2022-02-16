package com.albo.biblioteca.comics.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="comic_creator")
public class ComicCreator {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="\"id\"",nullable = false)
  private Long id;

  @JoinColumn(name ="\"idcomic\"", referencedColumnName = "\"id\"")
  @ManyToOne
  @JsonIgnoreProperties(value = "creators")
  private Comic comic;

  @ManyToOne
  @JoinColumn(name ="\"idcreator\"")
  private Creator creator;

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

  public Creator getCreator() {
    return creator;
  }

  public void setCreator(Creator creator) {
    this.creator = creator;
  }
}
