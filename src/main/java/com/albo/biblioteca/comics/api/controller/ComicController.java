package com.albo.biblioteca.comics.api.controller;

import com.albo.biblioteca.comics.api.service.CharacterService;
import com.albo.biblioteca.comics.api.service.ComicService;
import com.albo.biblioteca.comics.api.vo.CharacterResponse;
import com.albo.biblioteca.comics.api.vo.ComicResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Hildamar Parra
 */

@RequestMapping("/marvel")
@RestController
@Api(value="Comic's services")
public class ComicController  {

  @Autowired
  ComicService comicService;

  @Autowired
  CharacterService characterService;

  @ApiOperation(value = "Allow get comic's colaborator related with the hero")
  @ApiResponses(value={ @ApiResponse (code=200,message = "Request Successfully"),
                        @ApiResponse (code=400, message = "Generic Error with the sent information"),
                        @ApiResponse (code=404, message = "The resource that you were trying to reach was not found"),
                        @ApiResponse (code=500, message = "General Error")})
  @GetMapping("/collaborators")
  public ComicResponse getComic(String hero) {
    ComicResponse comicResponse = comicService.getColaborators();
    return comicResponse;
  }


  @ApiOperation(value = "Allow get comic's characters related with the hero")
  @ApiResponses(value={ @ApiResponse (code=200,message = "Request Successfully"),
    @ApiResponse (code=400, message = "Generic Error with the sent information"),
    @ApiResponse (code=404, message = "The resource that you were trying to reach was not found"),
    @ApiResponse (code=500, message = "General Error")})
  @GetMapping("/characters")
  public List<CharacterResponse> getCharacter(String hero){

    return characterService.getCharacterRelated(hero);

  }




  
  
}
