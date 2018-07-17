package ioc.providers;

import ioc.model.Movie;
import java.util.Collection;
import java.util.Set;

public interface MovieProvider {

  Movie get(Object id);

  Movie remove(Object id);

  Movie put(String id, Movie movie);

  Collection<Movie> values();

  int size();

  Set<String> keySet();

  boolean isEmpty();

  boolean containsKey(Object id);

  boolean containsValue(Object movie);
}
