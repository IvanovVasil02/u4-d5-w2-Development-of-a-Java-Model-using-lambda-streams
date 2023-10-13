package vasilivanov;

import com.github.javafaker.Faker;
import vasilivanov.entities.Book;
import vasilivanov.entities.LibraryProduct;
import vasilivanov.entities.Magazine;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

import static vasilivanov.entities.LibraryProduct.getRndm;
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

    for (int i = 0; i < 9; i++) {
      libraryCatalogue.add(bookSupplier.get());
      libraryCatalogue.add(magazineSupplier.get());
    }

    libraryCatalogue.forEach(System.out::println);

  }
}
