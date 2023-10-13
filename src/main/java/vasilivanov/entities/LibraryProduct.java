package vasilivanov.entities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public abstract class LibraryProduct {
  protected String isbnCode;
  protected String title;
  protected LocalDate publicationYear;
  protected long pagesNumber;

  public LibraryProduct(String isbnCode, String title, LocalDate publicationYear, long pagesNumber) {
    this.isbnCode = isbnCode;
    this.title = title;
    this.publicationYear = publicationYear;
    this.pagesNumber = pagesNumber;
  }

  //conver string to localdate
  public static LocalDate getStrLocaleDate(String dateString) {
    return LocalDate.parse(dateString);
  }

  //conver Date to localdate
  public static LocalDate convertToLocalDate(Date dateToConvert) {
    return Instant.ofEpochMilli(dateToConvert.getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
  }

  @Override
  public String toString() {
    return "Product{" +
            "isbnCode='" + isbnCode + '\'' +
            ", title='" + title + '\'' +
            ", publicationYear='" + publicationYear + '\'' +
            ", pagesNumber='" + pagesNumber + '\'' +
            '}';
  }
}
