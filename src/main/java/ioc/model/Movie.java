package ioc.model;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;

/**
 * Immutable movie
 */
@Value
@Builder(toBuilder = true)
public class Movie {

  private String id;
  private String title;
  private String director;
  private String genre;

  public Movie(String id, String title, String director, String genre) {
    this.id = id;
    this.title = title;
    this.director = director;
    this.genre = genre;
  }

  public Movie(String title, String director, String genre) {
    this.id = UUID.randomUUID().toString();
    this.title = title;
    this.director = director;
    this.genre = genre;
  }
}
