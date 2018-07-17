package ioc.services;

import ioc.model.Movie;

public interface MoviesMutations {

  /**
   * Add a movie.
   */
  Movie add(Movie movie);

  /**
   * Update a movie.
   *
   * @param movie new movie
   * @return the old movie
   */
  Movie update(Movie movie);

  /**
   * Remove the movie by id.
   */
  Movie remove(String id);
}
