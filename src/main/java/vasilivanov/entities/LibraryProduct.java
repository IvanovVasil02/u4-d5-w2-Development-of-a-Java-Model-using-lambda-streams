package vasilivanov.entities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

  public static void addElement(List<LibraryProduct> catalogue, Map<String, LibraryProduct> archive, String isbn) {
    Object[] o = catalogue.stream().filter(product -> product.isbnCode.equals(isbn)).toArray();
    archive.put(isbn, (LibraryProduct) o[0]);
  }

  public static void removeElement(Map<String, LibraryProduct> archive, String isbn) {
    archive.remove(isbn);
  }

  public static void getSearchedElementByIsbn(List<LibraryProduct> catalogue, String isbn) {
    Iterator<LibraryProduct> i = catalogue.iterator();

    while (i.hasNext()) {
      LibraryProduct current = i.next();
      if (current.isbnCode.equals(isbn)) {
        System.out.println(current);
      }
    }
  }

  public static void getSearchedElementByDate(List<LibraryProduct> catalogue, String userDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate date = LocalDate.parse(userDate, formatter);

    Iterator<LibraryProduct> i = catalogue.iterator();

    while (i.hasNext()) {
      LibraryProduct current = i.next();
      if (current.pubblicationYear.equals(date)) {
        System.out.println(current);
      }
    }
  }

  public static void getSearchedElementByAuthor(List<LibraryProduct> catalogue, String author) {
    Iterator<LibraryProduct> i = catalogue.iterator();

    while (i.hasNext()) {
      LibraryProduct current = i.next();
      if (current instanceof Book) {
        if (((Book) current).getAuthor().contains(author)) {
          System.out.println(current);
        }
      }
    }
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
