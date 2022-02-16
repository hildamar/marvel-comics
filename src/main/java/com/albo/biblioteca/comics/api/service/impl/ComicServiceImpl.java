package com.albo.biblioteca.comics.api.service.impl;

import com.albo.biblioteca.comics.api.configuration.MarvelConfiguration;
import com.albo.biblioteca.comics.api.service.ComicService;
import com.albo.biblioteca.comics.api.vo.ComicResponse;
import com.albo.biblioteca.comics.persistence.model.Comic;
import com.albo.biblioteca.comics.persistence.model.ComicCreator;
import com.albo.biblioteca.comics.persistence.model.Creator;
import com.albo.biblioteca.comics.persistence.repository.ComicCreatorRepository;
import com.albo.biblioteca.comics.persistence.repository.ComicRepository;
import com.albo.biblioteca.comics.persistence.repository.CreatorRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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

/**
 * @author Hildamar Parra
 */
@Service
public class ComicServiceImpl implements ComicService {

  private MarvelConfiguration marvenConfiguration;
  private String apiPublicKey;
  private String apiPrivateKey;
  private RestTemplate restTemplate;
  private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Autowired
  private ComicRepository comicRepository;
  @Autowired
  private CreatorRepository creatorRepository;
  @Autowired
  private ComicCreatorRepository comicCreatorRepository;

  HttpHeaders headers= new HttpHeaders();


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


  @Autowired
  public void setMarvelConfigurations(MarvelConfiguration marvelConfigurations){
    this.marvenConfiguration =  marvelConfigurations;
    this.apiPublicKey =  marvelConfigurations.getApiPublicKey();
    this.apiPrivateKey =  marvelConfigurations.getApiPrivateKey();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

  }

    private ResponseEntity<String> getMarvelComics(String hero) {
      Gson gson = new Gson();
      JsonObject character= gson.fromJson(getMarvelCharacterName(hero).getBody(), JsonObject.class);
      JsonObject data =  gson.fromJson(character.get("data"), JsonObject.class);
     com.google.gson.JsonArray result =  gson.fromJson(data.get("results"), JsonArray.class);
      String characterId  = result.get(0).getAsJsonObject().get("id").getAsString();
    String urlComic = marvenConfiguration.getEndPointComic();

      Timestamp ts = new Timestamp(new Date().getTime());
      String hash = DigestUtils.md5Hex( ts.toString() + apiPrivateKey + apiPublicKey);
      Map<String, String> params = new HashMap<>();
      params.put("characters",characterId);
      params.put("ts", ts.toString());
      params.put("apikey", apiPublicKey);
      params.put("hash", hash);

      UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlComic);
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






  @Override
  public ResponseEntity<String> getCollaborators(String hero) {
    ResponseEntity<String> marvelComics = null;
    marvelComics = this.getMarvelComics(hero);

    Gson gson = new Gson();
    JsonObject json = gson.fromJson(marvelComics.getBody(), JsonObject.class);
    JsonObject jsonData = gson.fromJson(json.get("data"), JsonObject.class);
    JsonArray jsonResults = gson.fromJson(jsonData.get("results"), JsonArray.class);
    if(!jsonResults.isEmpty()) {
      jsonResults.forEach(jsonResult -> {
        ComicCreator comicCreator = new ComicCreator();
        JsonObject obj = (JsonObject) jsonResult;
        Comic comic = new Comic();
        comic.setId(obj.get("id").getAsLong());
        comic.setTitulo(obj.get("title").getAsString());
        comic.setLast_sync(new Date());
        comic.setHero(hero);
        comicCreator.setComic(comic);
        JsonObject objCreator = gson.fromJson(obj.get("creators"), JsonObject.class);

        JsonArray items = gson.fromJson(objCreator.get("items"), JsonArray.class);
        comicRepository.save(comic);
        for (int i = 0; i < items.size(); i++) {
          JsonObject objItem = (JsonObject) items.get(i);
          Creator creator = new Creator();
          if (!objItem.get("role").isJsonNull() && (objItem.get("role").getAsString().equals("editor") ||
            objItem.get("role").getAsString().equals("writer") ||
            objItem.get("role").getAsString().equals("colorist"))) {
            creator.setId(new Long(objItem.get("resourceURI").getAsString().split("creators/")[1]));
            creator.setNombre(objItem.get("name").getAsString());
            creator.setRole(objItem.get("role").getAsString());
            creator.setLast_sync(new Date());
            creatorRepository.save(creator);
            comicCreator.setCreator(creator);
            comicCreatorRepository.save(comicCreator);
          }
        }


      });
    }
    else{
      return ResponseEntity.status(500).body("An error has occurred ==> The request don't got results");
    }
    return marvelComics;

  }



  private ResponseEntity<String> getMarvelCharacterName(String hero){


    String urlCharacterName = marvenConfiguration.getEndPointCharacterName();
    Timestamp tsCh = new Timestamp(new Date().getTime());
    String hash = DigestUtils.md5Hex( tsCh.toString() + apiPrivateKey + apiPublicKey);
    Map<String, String> params = new HashMap<>();
    params.put("name",hero);
    params.put("ts", tsCh.toString());
    params.put("apikey", apiPublicKey);
    params.put("hash", hash);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlCharacterName);
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

  @Override
  public ComicResponse getColaborators() {
    ComicResponse comicResponse = new ComicResponse();
    List<String> editors = new ArrayList<>();
    List<String> writers = new ArrayList<>();
    List<String> colorists = new ArrayList<>();
    for (Creator creator : creatorRepository.findByRole("editor")) {
      editors.add(creator.getNombre());
      comicResponse.setLast_sync(creator.getLast_sync());
    }
    for (Creator creator : creatorRepository.findByRole("writer")) {
      writers.add(creator.getNombre());
    }
    for (Creator creator : creatorRepository.findByRole("colorist")) {
      colorists.add(creator.getNombre());
    }
    comicResponse.setColorist(colorists);
    comicResponse.setEditors(editors);
    comicResponse.setWriters(writers);

    return comicResponse;
  }
}
