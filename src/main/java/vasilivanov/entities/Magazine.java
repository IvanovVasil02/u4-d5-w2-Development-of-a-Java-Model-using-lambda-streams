package vasilivanov.entities;

import enums.Periodicity;

import java.util.Date;
import java.util.Random;

public class Magazine extends LibraryProduct {
  private Periodicity periodicity;

  public Magazine(String isbnCode, String title, Date pubblicationYear, long pagesNumber, Periodicity periodicity) {
    super(isbnCode, title, pubblicationYear, pagesNumber);
    this.periodicity = periodicity;
  }

  public static Periodicity randomPeriodicity() {
    Random PRNG = new Random();
    Periodicity[] directions = Periodicity.values();
    return directions[PRNG.nextInt(directions.length)];
  }

  public Periodicity getPeriodicity() {
    return periodicity;
  }

  public void setPeriodicity(Periodicity periodicity) {
    this.periodicity = periodicity;
  }

  @Override
  public String toString() {
    return "Magazine{" +
            "isbnCode='" + isbnCode + '\'' +
            ", title='" + title + '\'' +
            ", pubblicationYear='" + pubblicationYear + '\'' +
            ", pagesNumber='" + pagesNumber + '\'' +
            "periodicity=" + periodicity +
            '}';
  }

}
