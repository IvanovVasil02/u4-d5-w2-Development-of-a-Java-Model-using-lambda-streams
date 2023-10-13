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
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.lang.Long.parseLong;

public abstract class LibraryProduct {
  protected String isbnCode;
  protected String title;
  protected LocalDate pubblicationYear;
  protected long pagesNumber;

  public LibraryProduct(String isbnCode, String title, LocalDate pubblicationYear, long pagesNumber) {
    this.isbnCode = isbnCode;
    this.title = title;
    this.pubblicationYear = pubblicationYear;
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

    Iterator<LibraryProduct> i = catalogue.iterator();

    while (i.hasNext()) {
      LibraryProduct current = i.next();
      if (current.pubblicationYear.equals(getStrLocaleDate(userDate))) {
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

  public static void writeArchive(Map<String, LibraryProduct> archive, File filePage) throws IOException {

    Iterator<LibraryProduct> i = archive.values().iterator();

    while (i.hasNext()) {
      LibraryProduct current = i.next();
      if (current instanceof Book) {

        String stringElement = "Book#" +
                current.isbnCode + "#" +
                current.title + "#" +
                current.pubblicationYear + "#" +
                current.pagesNumber + "#" +
                ((Book) current).getAuthor() + "#" +
                ((Book) current).getGenre() + "#";
        FileUtils.writeStringToFile(filePage, stringElement + "-!-", StandardCharsets.UTF_8, true);
      } else {
        String stringElement = "Magazine#" +
                current.isbnCode + "#" +
                current.title + "#" +
                current.pubblicationYear + "#" +
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

      if (splitElement[1].equals("Book")) {
        for (int j = 0; j < splitElement.length; j++) {
          archive.put(splitElement[1], new Book(splitElement[1],
                  splitElement[2], getStrLocaleDate(splitElement[3]),
                  parseLong(splitElement[4]), splitElement[5], splitElement[6]));
        }
      } else if (splitElement[1].equals("Magazine")) {
        for (int j = 0; j < splitElement.length; j++) {
          archive.put(splitElement[1], new Magazine(splitElement[1],
                  splitElement[2], getStrLocaleDate(splitElement[3]),
                  parseLong(splitElement[4]), Periodicity.valueOf(splitElement[5])));
        }
      }
    }
  }

  public static LocalDate getStrLocaleDate(String dateString) {
    return LocalDate.parse(dateString);
  }

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
            ", pubblicationYear='" + pubblicationYear + '\'' +
            ", pagesNumber='" + pagesNumber + '\'' +
            '}';
  }
}
