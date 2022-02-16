package com.albo.biblioteca.comics.api.configuration;

import com.albo.biblioteca.comics.api.service.CharacterService;
import com.albo.biblioteca.comics.api.service.ComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MarvelScheduler {
  private MarvelConfiguration marvenConfiguration;
  private String heroes;

  @Autowired
  ComicService comicService;

  @Autowired
  CharacterService characterService;

  @Scheduled(cron = "0 12 * * * ?")
  public void synchronization() throws IOException
  {
    for(String hero : this.heroes.split(",")) {
        comicService.getCollaborators(hero);
        characterService.getMarvelCharacters(hero);

    }
  }

  @Autowired
  public void setMarvelConfigurations(MarvelConfiguration marvelConfigurations){
    this.marvenConfiguration =  marvelConfigurations;
    this.heroes = marvelConfigurations.getHeroes();
  }
}
