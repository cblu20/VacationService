package Models;
import Database.DataStore;

import java.util.Random;


public class Mitarbeiter {

    public String name;
    public String abteilung;
    public int id ;

    public Mitarbeiter(String name, String abteilung) {
        this.name = name;
        this.abteilung = abteilung;
        Random rand = new Random();
        id = rand.nextInt(200);
        DataStore.addMitarbeiter(this);
    }
}
