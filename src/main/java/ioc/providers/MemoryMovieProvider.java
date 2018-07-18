package ioc.providers;

import com.google.common.collect.ForwardingMap;
import ioc.model.Movie;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class MemoryMovieProvider extends ForwardingMap<String, Movie> implements MovieProvider {

  private Map<String, Movie> movies = new LinkedHashMap<>();

  public MemoryMovieProvider() {
    System.out.println("Using MemoryMovieProvider");
  }

  @PostConstruct
  public void init() {
    System.out.println("Initializing movies data in a MemoryMovieProvider:@PostConstruct method");
    List<Movie> moviesToLoad = new ArrayList<>();
    moviesToLoad.add(new Movie("Vertigo", "Alfred Hitchcock", "CRIME"));
    moviesToLoad.add(new Movie("North by Northwest", "Alfred Hitchcock", "ACTION"));
    moviesToLoad.add(new Movie("Psycho", "Alfred Hitchcock", "HORROR"));
    moviesToLoad.add(new Movie("The Conversation", "Francis Ford Coppola", "CRIME"));
    moviesToLoad.add(new Movie("Apocalypse Now", "Francis Ford Coppola", "ACTION"));
    moviesToLoad.add(new Movie("The Godfather", "Francis Ford Coppola", "DRAMA"));
    moviesToLoad.add(new Movie("Scarface", "Brian De Palma", "CRIME"));
    moviesToLoad.add(new Movie("Carrie", "Brian De Palma", "HORROR"));
    moviesToLoad.add(new Movie("Pulp Fiction", "Quentin Tarantino", "CRIME"));
    moviesToLoad.add(new Movie("2001", "Stanley Kubrick", "SCI-FI"));
    moviesToLoad.add(new Movie("The Shining", "Stanley Kubrick", "HORROR"));
    moviesToLoad.add(new Movie("Full Metal Jacket", "Stanley Kubrick", "ACTION"));
    moviesToLoad.add(new Movie("A Clockwork Orange", "Stanley Kubrick", "DRAMA"));
    moviesToLoad.forEach(m -> movies.put(m.getId(), m));
    System.out.println(String.format("Loaded %s movies", moviesToLoad.size()));
  }

  @PreDestroy
  public void destroy() {
    System.out.println("Executing a MemoryMovieProvider:@PreDestroy method");
  }

  @Override
  protected Map<String, Movie> delegate() {
    return movies;
  }
}
