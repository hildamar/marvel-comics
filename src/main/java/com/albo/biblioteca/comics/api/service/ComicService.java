package com.albo.biblioteca.comics.api.service;

import com.albo.biblioteca.comics.api.vo.ComicResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author Hildamar Parra
 */

public interface ComicService {
  ResponseEntity<String> getCollaborators(String hero);
  ComicResponse getColaborators();
}
