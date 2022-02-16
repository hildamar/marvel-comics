package com.albo.biblioteca.comics.api.vo;

import java.util.Date;
import java.util.List;

/**
 * @author Hildamar Parra
 */
public class CharacterResponse {

  private Date last_sync;
  private String name;
  private List<String> comics;

  public Date getLast_sync() {
    return last_sync;
  }

  public void setLast_sync(Date last_sync) {
    this.last_sync = last_sync;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getComics() {
    return comics;
  }

  public void setComics(List<String> comics) {
    this.comics = comics;
  }
}
