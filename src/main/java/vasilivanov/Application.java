package vasilivanov;

import com.github.javafaker.Faker;
import vasilivanov.entities.Book;
import vasilivanov.entities.LibraryProduct;
import vasilivanov.entities.Magazine;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;

import static vasilivanov.entities.LibraryProduct.*;
import static vasilivanov.entities.Magazine.randomPeriodicity;

public class Application {

  public static void main(String[] args) throws ParseException {

    List<LibraryProduct> libraryCatalogue = new ArrayList<>();
    Faker f = new Faker(new Locale("ITALY"));

    Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2020");
    Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2023");

    Supplier<Book> bookSupplier = () -> new Book(f.code().isbn10(),
            f.book().title(), f.date().between(date1, date2),
            getRndm(), f.book().author(), f.book().genre());

    Supplier<Magazine> magazineSupplier = () -> new Magazine(f.code().isbn10(),
            f.book().title(), f.date().between(date1, date2),
            getRndm(), randomPeriodicity());

    for (int i = 0; i < 2; i++) {
      libraryCatalogue.add(bookSupplier.get());
      libraryCatalogue.add(magazineSupplier.get());
    }
    List<LibraryProduct> userArchive = new ArrayList<>();
    while (true) {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter 0 to view the library catalog");
      System.out.println("Enter 1 to add an element to your archive.");
      System.out.println("Enter 2 to remove an element from your archive.");
      System.out.println("Enter 3 to search an element of the catalog by ISBN.");
      System.out.println("Enter 4 to search elements of the catalog by pubblication date.");
      System.out.println("Enter 5 to search an element of the catalog by author.");
      System.out.println("Enter 6 to save your archive.");
      System.out.println("Enter 7 to view your archive");

      try {
        int choice = Integer.parseInt(scanner.nextLine());
        String isbn;

        switch (choice) {

          case 0:
            libraryCatalogue.forEach(System.out::println);
            System.out.println("What do you want to do now");
            choice = Integer.parseInt(scanner.nextLine());

          case 1:

            while (choice == 1) {
              System.out.println("enter the ISBN code of the book you want to add to your archive");
              isbn = scanner.nextLine();
              addElement(libraryCatalogue, userArchive, isbn);
              userArchive.forEach(System.out::println);
              System.out.println("What do you want to do now");
              choice = Integer.parseInt(scanner.nextLine());
            }

          case 2:
            System.out.println("enter the ISBN code of the book you want to remove from your archive");
            isbn = scanner.nextLine();
            removeElement(userArchive, isbn);
            userArchive.forEach(System.out::println);
            System.out.println("What do you want to do now");
            choice = Integer.parseInt(scanner.nextLine());
        }
      } catch (NumberFormatException ex) {
        System.out.println("You have to enter a number");
      }

      break;
    }
  }
}
