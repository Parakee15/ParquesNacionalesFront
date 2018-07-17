package ioc.providers;

import com.google.common.collect.ForwardingMap;
import ioc.model.Movie;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CsvMemoryMovieProvider extends ForwardingMap<String, Movie> implements MovieProvider {

  private Map<String, Movie> movies = new LinkedHashMap<>();

  public CsvMemoryMovieProvider(final String csvFilePath) {
    loadDataFromCsv(csvFilePath);
  }

  private void loadDataFromCsv(final String csvFilePath) {
    System.out.println(String.format("Initializing movies data from csv file: %s", csvFilePath));
    try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(csvFilePath)) {
      Reader reader = new InputStreamReader(inputStream);
      Iterable<CSVRecord> records =
          CSVFormat.EXCEL.withDelimiter(';').withFirstRecordAsHeader().parse(reader);
      records.forEach(record -> {
        String id = UUID.randomUUID().toString();
        String title = record.get("Film");
        String director = record.get("Director");
        String genre = record.get("Genre");
        movies.put(id, new Movie(id, title, director, genre));
      });
      System.out.println(String.format("Loaded %s movies", movies.size()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected Map<String, Movie> delegate() {
    return movies;
  }
}
