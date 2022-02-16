package com.albo.biblioteca.comics.api.vo;

import java.util.Date;
import java.util.List;

/**
 * @author Hildamar Parra
 */
public class ComicResponse {

  private Date last_sync;
  private List<String> editors;
  private List<String> writers;
  private List<String> colorist;




  public Date getLast_sync() {
    return last_sync;
  }

  public void setLast_sync(Date last_sync) {
    this.last_sync = last_sync;
  }

  public List<String> getEditors() {
    return editors;
  }

  public void setEditors(List<String> editors) {
    this.editors = editors;
  }

  public List<String> getWriters() {
    return writers;
  }

  public void setWriters(List<String> writers) {
    this.writers = writers;
  }

  public List<String> getColorist() {
    return colorist;
  }

  public void setColorist(List<String> colorist) {
    this.colorist = colorist;
  }
}
