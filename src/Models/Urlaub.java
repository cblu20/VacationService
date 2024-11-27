package Models;

import jdk.jfr.Timespan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Urlaub {
    public LocalDate startDate;
    public LocalDate endDate;
    public long days;

    public int mitarbeiterID;

    public Urlaub(String startDate, String endDate, int mitarbeiterID) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.startDate = LocalDate.parse(startDate,formatter);
        this.endDate = LocalDate.parse(endDate,formatter);
        this.mitarbeiterID = mitarbeiterID;
        days = ChronoUnit.DAYS.between(this.startDate, this.endDate);
    }
}
