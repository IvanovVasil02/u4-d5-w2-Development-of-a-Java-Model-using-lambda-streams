package vasilivanov.entities;

import java.util.Date;

public class Book extends LibraryProduct {

  private String author;
  private String genre;


  public Book(String isbnCode, String title, Date pubblicationYear, long pagesNumber, String author, String genre) {
    super(isbnCode, title, pubblicationYear, pagesNumber);
    this.author = author;
    this.genre = genre;
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
