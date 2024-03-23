package vasilivanov.entities;

import org.apache.commons.io.FileUtils;
import vasilivanov.enums.Periodicity;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.lang.Long.parseLong;

public class ArchiveFunctions {
  public static long getRndm() {
    return Math.round(Math.random() * (400 - 100 + 1) + 100);
  }

  public static void addElement(List<LibraryProduct> catalogue, Map<String, LibraryProduct> archive, String isbn) {
    Object[] o = catalogue.stream().filter(element -> element.isbnCode.equals(isbn)).toArray();
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
    Optional<LibraryProduct> searchResult = catalogue.stream().filter(element -> Objects.equals(element.isbnCode, isbn)).findFirst();
    if (searchResult.isEmpty()) {
      System.out.println("The are not aitems whit this ISBN code...");
    } else {
      System.out.println(searchResult);
    }
  }

  public static void getSearchedElementByDate(List<LibraryProduct> catalogue, String userDate) {
    List<LibraryProduct> searchResult = catalogue.stream().filter(element -> element.publicationYear.equals(LibraryProduct.getStrLocaleDate(userDate))).toList();
    if (searchResult.isEmpty()) {
      System.out.println("The are not aitems this day...");
    } else {
      searchResult.forEach(System.out::println);
    }
  }

  public static void getSearchedElementByAuthor(List<LibraryProduct> catalogue, String author) {
    List<LibraryProduct> searchResult = catalogue.stream()
            .filter(element -> element instanceof Book && ((Book) element).getAuthor().toLowerCase().contains(author.toLowerCase()))
            .toList();
    if (searchResult.isEmpty()) {
      System.out.println("The are not items whit this Author's name...");
    } else {
      searchResult.forEach(System.out::println);
    }
  }

  public static void writeArchive(Map<String, LibraryProduct> archive, File filePage) throws IOException {

    for (LibraryProduct current : archive.values()) {
      String stringElement;
      if (current instanceof Book) {
        stringElement = "Book#" +
                current.isbnCode + "#" +
                current.title + "#" +
                current.publicationYear + "#" +
                current.pagesNumber + "#" +
                ((Book) current).getAuthor() + "#" +
                ((Book) current).getGenre() + "#";
      } else {
        stringElement = "Magazine#" +
                current.isbnCode + "#" +
                current.title + "#" +
                current.publicationYear + "#" +
                current.pagesNumber + "#" +
                ((Magazine) current).getPeriodicity() + "#";
      }
      FileUtils.writeStringToFile(filePage, stringElement + "-!-", StandardCharsets.UTF_8, true);
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
                  splitElement[2], LibraryProduct.getStrLocaleDate(splitElement[3]),
                  parseLong(splitElement[4]), splitElement[5], splitElement[6]));
        }
      } else if (splitElement[0].equals("Magazine")) {
        for (int j = 0; j < splitElement.length; j++) {
          archive.put(splitElement[1], new Magazine(splitElement[1],
                  splitElement[2], LibraryProduct.getStrLocaleDate(splitElement[3]),
                  parseLong(splitElement[4]), Periodicity.valueOf(splitElement[5])));
        }
      }
    }
  }
}
