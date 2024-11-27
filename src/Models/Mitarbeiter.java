package Models;
import Database.DataStore;

import java.util.Objects;
import java.util.Random;


public class Mitarbeiter {

    public String name;
    public String abteilung;
    public int id ;
    private static int idCounter = 1;

    public Mitarbeiter(String name, String abteilung) {
        this.name = name;
        this.abteilung = abteilung;
        id = idCounter++;
        DataStore.addMitarbeiter(this);
    }

    @Override
    public boolean equals(Object o){
        return id == ((Mitarbeiter) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Hash basiert auf der ID
    }
}
