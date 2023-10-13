package vasilivanov.entities;

import enums.Periodicity;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static java.lang.Long.parseLong;

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

  //return random number for the number of the pages
  public static long getRndm() {
    return Math.round(Math.random() * (400 - 100 + 1) + 100);
  }

  public static void addElement(List<LibraryProduct> catalogue, Map<String, LibraryProduct> archive, String isbn) {
    Object[] o = catalogue.stream().filter(product -> product.isbnCode.equals(isbn)).toArray();
    if (o.length == 0) {
      System.out.println("ISBN code wrong!!");
    } else {
      archive.put(isbn, (LibraryProduct) o[0]);
      System.out.println("The item was added successfully");
    }
  }

  public static void removeElement(Map<String, LibraryProduct> archive, String isbn) {
    archive.remove(isbn);
    System.out.println("The item was successfully removed");
  }

  public static void getSearchedElementByIsbn(List<LibraryProduct> catalogue, String isbn) {
    List<LibraryProduct> searchResult = new ArrayList<>();
    Iterator<LibraryProduct> i = catalogue.iterator();

    while (i.hasNext()) {
      LibraryProduct current = i.next();
      if (current.isbnCode.equals(isbn)) {
        searchResult.add(current);
      }
    }
    if (searchResult.isEmpty()) {
      System.out.println("The are not aitems whit this ISBN code...");
    } else {
      searchResult.forEach(System.out::println);
    }
  }

  public static void getSearchedElementByDate(List<LibraryProduct> catalogue, String userDate) {
    List<LibraryProduct> searchResult = new ArrayList<>();
    Iterator<LibraryProduct> i = catalogue.iterator();

    while (i.hasNext()) {
      LibraryProduct current = i.next();
      if (current.publicationYear.equals(getStrLocaleDate(userDate))) {
        searchResult.add(current);
      }
    }
    if (searchResult.isEmpty()) {
      System.out.println("The are not aitems whit this ISBN code...");
    } else {
      searchResult.forEach(System.out::println);
    }
  }

  public static void getSearchedElementByAuthor(List<LibraryProduct> catalogue, String author) {
    List<LibraryProduct> searchResult = new ArrayList<>();
    Iterator<LibraryProduct> i = catalogue.iterator();

    while (i.hasNext()) {
      LibraryProduct current = i.next();
      if (current instanceof Book) {
        if (((Book) current).getAuthor().contains(author)) {
          searchResult.add(current);
        }
      }
    }
    if (searchResult.isEmpty()) {
      System.out.println("The are not aitems whit this ISBN code...");
    } else {
      searchResult.forEach(System.out::println);
    }
  }

  public static void writeArchive(Map<String, LibraryProduct> archive, File filePage) throws IOException {
    Iterator<LibraryProduct> i = archive.values().iterator();

    while (i.hasNext()) {
      LibraryProduct current = i.next();
      if (current instanceof Book) {
        String stringElement = "Book#" +
                current.isbnCode + "#" +
                current.title + "#" +
                current.publicationYear + "#" +
                current.pagesNumber + "#" +
                ((Book) current).getAuthor() + "#" +
                ((Book) current).getGenre() + "#";
        FileUtils.writeStringToFile(filePage, stringElement + "-!-", StandardCharsets.UTF_8, true);
      } else {
        String stringElement = "Magazine#" +
                current.isbnCode + "#" +
                current.title + "#" +
                current.publicationYear + "#" +
                current.pagesNumber + "#" +
                ((Magazine) current).getPeriodicity() + "#";
        FileUtils.writeStringToFile(filePage, stringElement + "-!-", StandardCharsets.UTF_8, true);
      }
    }
  }

  public static void readArchive(File filePage, Map<String, LibraryProduct> archive) throws IOException, ParseException {
    String readFileTostring = FileUtils.readFileToString(filePage, "UTF-8");
    String[] splitFile = readFileTostring.split("-!-");
    for (int i = 0; i < splitFile.length - 1; i++) {
      String[] splitElement = splitFile[i].split("#");

      if (splitElement[0].equals("Book")) {
        for (int j = 0; j < splitElement.length; j++) {
          archive.put(splitElement[1], new Book(splitElement[1],
                  splitElement[2], getStrLocaleDate(splitElement[3]),
                  parseLong(splitElement[4]), splitElement[5], splitElement[6]));
        }
      } else if (splitElement[0].equals("Magazine")) {
        for (int j = 0; j < splitElement.length; j++) {
          archive.put(splitElement[1], new Magazine(splitElement[1],
                  splitElement[2], getStrLocaleDate(splitElement[3]),
                  parseLong(splitElement[4]), Periodicity.valueOf(splitElement[5])));
        }
      }
    }
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
