package ioc.services;

import ioc.model.Movie;
import java.util.List;
import java.util.function.Predicate;

public interface MoviesQueries {

  /**
   * All available movies.
   */
  List<Movie> getMovies();

  /**
   * List of filtered movies.
   */
  List<Movie> filter(Predicate<Movie> predicate);

  /**
   * Get a movie by id.
   */
  Movie getMovie(String id);
}
