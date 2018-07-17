package ioc;

import ioc.model.Movie;
import ioc.providers.CsvMemoryMovieProvider;
import ioc.providers.MemoryMovieProvider;
import ioc.services.mem.MoviesService;

public class App {

  public static void main(String[] args) {
    // CDI: Constructor Dependency Injection
    MoviesService moviesService = new MoviesService(new MemoryMovieProvider());
    moviesService.add(new Movie("Vertigo", "Alfred Hitchcock", "CRIME"));
    moviesService.getMovies().forEach(System.out::println);

    System.out.println("---------------------------------------");
    // CDI: Constructor Dependency Injection
    MoviesService csvMoviesService = new MoviesService(
        new CsvMemoryMovieProvider("ALL-MOVIES.csv")
    );
    csvMoviesService.getMovies().forEach(System.out::println);
  }
}
