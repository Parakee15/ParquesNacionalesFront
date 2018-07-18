package ioc;

import ioc.model.Movie;
import ioc.providers.MemoryMovieProvider;
import ioc.services.MoviesMutations;
import ioc.services.MoviesQueries;
import ioc.services.mem.MoviesService;

public class App {

  public static void main(String[] args) {
    IoC ioC = new IoC();
    ioC.registerInstance("csvFilePath", "ALL-MOVIES.csv");
    ioC.registerInstance("other", "Other String");
    ioC.register(MemoryMovieProvider.class);
    ioC.register(MoviesService.class);

    MoviesMutations moviesMutations = ioC.lookup(MoviesMutations.class);
    moviesMutations.add(new Movie("El Paseo 4", "Juan Camilo Pinzon", "COMEDY"));

    MoviesQueries moviesQueries = ioC.lookup(MoviesQueries.class);
    moviesQueries.getMovies().forEach(System.out::println);

    System.out.println("--------------------------");
    System.out.println("IoC instances:");
    ioC.log();
    ioC.stop();
  }
}
