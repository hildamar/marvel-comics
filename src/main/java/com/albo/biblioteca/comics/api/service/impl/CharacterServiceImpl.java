package com.albo.biblioteca.comics.api.service.impl;

import com.albo.biblioteca.comics.api.configuration.MarvelConfiguration;
import com.albo.biblioteca.comics.api.service.CharacterService;
import com.albo.biblioteca.comics.api.vo.CharacterResponse;
import com.albo.biblioteca.comics.persistence.model.Character;
import com.albo.biblioteca.comics.persistence.model.Comic;
import com.albo.biblioteca.comics.persistence.model.ComicCharacter;
import com.albo.biblioteca.comics.persistence.repository.CharacterRepository;
import com.albo.biblioteca.comics.persistence.repository.ComicCharacterRepository;
import com.albo.biblioteca.comics.persistence.repository.ComicRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

@Service
public class CharacterServiceImpl implements CharacterService {

  private MarvelConfiguration marvenConfiguration;
  private String apiPublicKey;
  private String apiPrivateKey;
  private RestTemplate restTemplate;
  HttpHeaders headers= new HttpHeaders();
  private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Autowired
  ComicRepository comicRepository;

  @Autowired
  CharacterRepository characterRepository;

  @Autowired
  ComicCharacterRepository comicCharacterRepository;

  @Autowired
  public void setMarvelConfigurations(MarvelConfiguration marvelConfigurations){
    this.marvenConfiguration =  marvelConfigurations;
    this.apiPublicKey =  marvelConfigurations.getApiPublicKey();
    this.apiPrivateKey =  marvelConfigurations.getApiPrivateKey();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

  }

  private RestTemplate getRestTemplate(){
    if(this.restTemplate == null){
      this.restTemplate = new RestTemplateBuilder().build();
    }
    this.restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    for (HttpMessageConverter converter : this.restTemplate.getMessageConverters()){
      if (converter instanceof StringHttpMessageConverter){
        ((StringHttpMessageConverter) converter ).setWriteAcceptCharset(false);
      }
    }
    return this.restTemplate;
  }



  public ResponseEntity<String> getCharacters(String idComic)  {

    String urlCharacter = marvenConfiguration.getEndPointCharacter();
    Timestamp tsCh = new Timestamp(new Date().getTime());
    String hash = DigestUtils.md5Hex( tsCh.toString() + apiPrivateKey + apiPublicKey);
    Map<String, String> params = new HashMap<>();
    params.put("ts", tsCh.toString());
    params.put("apikey", apiPublicKey);
    params.put("hash", hash);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlCharacter.replace("{id}",idComic));
    for (Map.Entry<String, String> entry : params.entrySet()) {
      builder.queryParam(entry.getKey(), entry.getValue());
    }
    URI uri = null;
    try {
      uri = new URI(builder.toUriString());


    HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
    this.getRestTemplate();
    ResponseEntity<String> response = null;
      response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
      return response;
    }
    catch (ResourceAccessException e){
      logger.error(e.getMessage());
      logger.error(e.getClass().toString());
      return ResponseEntity.status(500).body(e.getCause().getMessage());
    }
    catch (HttpClientErrorException e){
      logger.error(e.getMessage());
      logger.error(e.getClass().toString());
      return ResponseEntity.status(e.getStatusCode().value()).body(e.getResponseBodyAsString());
    } catch (URISyntaxException e) {
      logger.error(e.getMessage());
      logger.error(e.getClass().toString());
      return ResponseEntity.status(500).body(e.getCause().getMessage());
    }

  }

  public void getMarvelCharacters(String hero) {
    List<Comic> comics = comicRepository.findByHero(hero);
    List<Character> characters = new ArrayList<>();
    List<ComicCharacter> comicCharacters = new ArrayList<>();

    Gson gson = new Gson();
    for (Comic comic : comics) {

      JsonObject ch= gson.fromJson(getCharacters(comic.getId().toString()).getBody(), JsonObject.class);
      JsonObject data = gson.fromJson(ch.get("data"), JsonObject.class);
      JsonArray result = gson.fromJson(data.get("results"), JsonArray.class);

      for (JsonElement item : result) {
        ComicCharacter comicCharacter = new ComicCharacter();
        Character character = new Character();
        character.setId(new Long(item.getAsJsonObject().get("id").getAsLong()));
        character.setName(item.getAsJsonObject().get("name").getAsString());
        character.setLast_sync(new Date());
        character.setCharacterRelated(hero);
        comicCharacter.setId(new Long(comic.getId()+character.getId()));
        comicCharacter.setComic(comic);
        characterRepository.save(character);
        comicCharacter.setCharacter(character);
        comicCharacters.add(comicCharacter);

      }

    }
    comicCharacterRepository.saveAll(comicCharacters);

  }

  @Override
  public List<CharacterResponse> getCharacterRelated(String hero) {

    List<CharacterResponse> characterResponses = new ArrayList<>();
    for (Character character : characterRepository.findByCharacterRelated(hero)) {
      CharacterResponse characterResponse = new CharacterResponse();
      characterResponse.setLast_sync(character.getLast_sync());
      characterResponse.setName(character.getName());
      List<String> comics = new ArrayList<>();
      for(ComicCharacter comicCharacter : comicCharacterRepository.findByCharacter(character)){

          comics.add(comicRepository.findById(comicCharacter.getComic().getId()).get().getTitulo());
        }
      characterResponse.setComics(comics);
      characterResponses.add(characterResponse);
      }


    return characterResponses;
  }
}
