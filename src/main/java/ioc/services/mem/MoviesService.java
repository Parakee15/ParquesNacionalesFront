package ioc.services.mem;

import ioc.model.Movie;
import ioc.providers.MovieProvider;
import ioc.services.MoviesMutations;

public class MoviesService extends ReadOnlyMoviesService implements MoviesMutations {

  public MoviesService(MovieProvider movieProvider) {
    super(movieProvider);
  }

  @Override
  public Movie add(Movie movie) {
    return movieProvider.put(movie.getId(), movie);
  }

  @Override
  public Movie update(Movie movie) {
    return movieProvider.put(movie.getId(), movie);
  }

  @Override
  public Movie remove(String id) {
    return movieProvider.remove(id);
  }
}
