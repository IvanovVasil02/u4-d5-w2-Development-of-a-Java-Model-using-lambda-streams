package vasilivanov.entities;

import java.time.LocalDate;

public class Book extends LibraryProduct {

  private String author;
  private String genre;


  public Book(String isbnCode, String title, LocalDate pubblicationYear, long pagesNumber, String author, String genre) {
    super(isbnCode, title, pubblicationYear, pagesNumber);
    this.author = author;
    this.genre = genre;
  }

  public String getAuthor() {
    return author;
  }

  public String getGenre() {
    return genre;
  }

  @Override
  public String toString() {
    return "Book{" +
            "isbnCode='" + isbnCode + '\'' +
            ", title='" + title + '\'' +
            ", pubblicationYear='" + pubblicationYear + '\'' +
            ", pagesNumber='" + pagesNumber + '\'' +
            "author='" + author + '\'' +
            ", genre='" + genre + '\'' +
            '}';
  }

}
