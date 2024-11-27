package Models;

import java.util.ArrayList;
import java.util.List;

public class UrlaubList {

    public List<Urlaub> list;
    private int idMitarbeiter;


    public UrlaubList(int idMitarbeiter){
        if(list == null) {
            this.idMitarbeiter = idMitarbeiter;
            list = new ArrayList<>();
        }
    }

    public void addUrlaubToList( Urlaub urlaub) throws Exception {
        if(urlaub.mitarbeiterID != idMitarbeiter) {
            throw new Exception("Urlaub konnte nicht zum Mitarbeiter hinzugefügt werden.");
        }else {
            list.add(urlaub);
        }
    }

    public void removeUrlaubFromList(Urlaub urlaub ) throws Exception {
        try {
            list.remove(urlaub);
        }catch(Exception ex) {
            throw new Exception("Urlaub konnte nicht entfernt werden: " + ex.getMessage());
        }
    }

}
