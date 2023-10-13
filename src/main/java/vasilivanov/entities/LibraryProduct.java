package vasilivanov.entities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public abstract class LibraryProduct {
  protected String isbnCode;
  protected String title;
  protected LocalDate pubblicationYear;
  protected long pagesNumber;

  public LibraryProduct(String isbnCode, String title, Date pubblicationYear, long pagesNumber) {
    this.isbnCode = isbnCode;
    this.title = title;
    this.pubblicationYear = convertToLocalDate(pubblicationYear);
    this.pagesNumber = pagesNumber;
  }

  public static long getRndm() {
    return Math.round(Math.random() * (400 - 100 + 1) + 100);
  }

  public static void addElement(List<LibraryProduct> catalogue, List<LibraryProduct> archive, String isbn) {
    archive.addAll(catalogue.stream().
            filter(product -> product.isbnCode.equals(isbn))
            .toList());
  }

  public static void removeElement(List<LibraryProduct> archive, String isbn) {

    Iterator<LibraryProduct> i = archive.iterator();

  }

  public String getIsbnCode() {
    return isbnCode;
  }

  public void setIsbnCode(String isbnCode) {
    this.isbnCode = isbnCode;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public LocalDate getPubblicationYear() {
    return pubblicationYear;
  }

  public void setPubblicationYear(LocalDate pubblicationYear) {
    this.pubblicationYear = pubblicationYear;
  }

  public long getPagesNumber() {
    return pagesNumber;
  }

  public void setPagesNumber(long pagesNumber) {
    this.pagesNumber = pagesNumber;
  }

  public LocalDate convertToLocalDate(Date dateToConvert) {
    return Instant.ofEpochMilli(dateToConvert.getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
  }

  @Override
  public String toString() {
    return "Product{" +
            "isbnCode='" + isbnCode + '\'' +
            ", title='" + title + '\'' +
            ", pubblicationYear='" + pubblicationYear + '\'' +
            ", pagesNumber='" + pagesNumber + '\'' +
            '}';
  }
}
