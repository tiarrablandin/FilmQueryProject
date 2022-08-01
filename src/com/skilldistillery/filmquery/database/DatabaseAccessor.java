package com.skilldistillery.filmquery.database;

import java.util.*;
import com.skilldistillery.filmquery.entities.*;

public interface DatabaseAccessor {
  public Film findFilmById(int filmId);
  public Actors findActorById(int actorId);
  public List<Actors> findActorsByFilmId(int filmId);
  public List<Film> findFilmByKeyword(String keyword);
}
