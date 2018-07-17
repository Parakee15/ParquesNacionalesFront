package ioc.services.mem;

import com.google.common.collect.ImmutableList;
import ioc.model.Movie;
import ioc.providers.MovieProvider;
import ioc.services.MoviesQueries;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReadOnlyMoviesService implements MoviesQueries {

  protected final MovieProvider movieProvider;

  public ReadOnlyMoviesService(MovieProvider movieProvider) {
    this.movieProvider = movieProvider;
  }


  @Override
  public List<Movie> getMovies() {
    return ImmutableList.copyOf(movieProvider.values());
  }

  @Override
  public List<Movie> filter(Predicate<Movie> predicate) {
    return Collections
        .unmodifiableList(
            movieProvider.values()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList())
        );
  }

  @Override
  public Movie getMovie(String id) {
    return movieProvider.get(id);
  }
}
