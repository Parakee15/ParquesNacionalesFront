package ioc.providers;

import com.google.common.collect.ForwardingMap;
import ioc.model.Movie;
import java.util.LinkedHashMap;
import java.util.Map;

public class MemoryMovieProvider extends ForwardingMap<String, Movie> implements MovieProvider {

  private Map<String, Movie> movies = new LinkedHashMap<>();

  public MemoryMovieProvider() {
    System.out.println("Using MemoryMovieProvider");
  }

  @Override
  protected Map<String, Movie> delegate() {
    return movies;
  }
}
