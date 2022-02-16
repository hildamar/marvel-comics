package com.albo.biblioteca.comics.api.service;

import com.albo.biblioteca.comics.api.vo.CharacterResponse;

import java.util.List;

public interface CharacterService {
  void getMarvelCharacters(String hero) ;
  List<CharacterResponse> getCharacterRelated(String hero);
}
