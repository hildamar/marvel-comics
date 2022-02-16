package com.albo.biblioteca.comics.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author Hildamar Parra
 */
@Component
public class MarvelConfiguration implements Serializable {

  @Value("${marvel.api.publicKey:#{null}}")
  private String apiPublicKey;

  @Value("${marvel.api.privateKey:#{null}}")
  private String apiPrivateKey;

  @Value("${marvel.api.endpoint.comics:#{null}}")
  private String endPointComic;

  @Value("${marvel.api.endpoint.characters:#{null}}")
  private String endPointCharacter;

  @Value("${marvel.api.endpoint.character.name:#{null}}")
  private String endPointCharacterName;

  @Value("${marvel.api.endpoint.comics.creators:#{null}}")
  private String endPointComicCreators;

  @Value("${marvel.api.endpints.heroes:#{null}}")
  private String heroes;

  public String getEndPointComicCreators() {
    return endPointComicCreators;
  }

  public void setEndPointComicCreators(String endPointComicCreators) {
    this.endPointComicCreators = endPointComicCreators;
  }

  public String getHeroes() {
    return heroes;
  }

  public void setHeroes(String heroes) {
    this.heroes = heroes;
  }

  public String getEndPointCharacterName() {
    return endPointCharacterName;
  }

  public void setEndPointCharacterName(String endPointCharacterName) {
    this.endPointCharacterName = endPointCharacterName;
  }




  public String getApiPublicKey() {
    return apiPublicKey;
  }

  public void setApiPublicKey(String apiPublicKey) {
    this.apiPublicKey = apiPublicKey;
  }

  public String getApiPrivateKey() {
    return apiPrivateKey;
  }

  public void setApiPrivateKey(String apiPrivateKey) {
    this.apiPrivateKey = apiPrivateKey;
  }

  public String getEndPointComic() {
    return endPointComic;
  }

  public void setEndPointComic(String endPointComic) {
    this.endPointComic = endPointComic;
  }

  public String getEndPointCharacter() {
    return endPointCharacter;
  }

  public void setEndPointCharacter(String endPointCharacter) {
    this.endPointCharacter = endPointCharacter;
  }
}
