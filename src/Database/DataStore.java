package Database;

import Models.Mitarbeiter;
import Models.Urlaub;
import Models.UrlaubList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore {

    public static Map<Mitarbeiter, UrlaubList> mitarbeiterUrlaub = new HashMap<>();

    private static List<Mitarbeiter> mitarbeiterList = new ArrayList<>();
    private static DataStore instance = new DataStore();


    public static DataStore getInstance(){
        return instance;
    }

    public static void addMitarbeiter(Mitarbeiter mitarbeiter) {
        if(mitarbeiter!=null){
            mitarbeiterList.add(mitarbeiter);
        }
    }

    public static void deleteMitarbeiter(Mitarbeiter mitarbeiter) throws Exception {
        try {
            if (mitarbeiter != null) {
                Mitarbeiter mitTmp = getMitarbeiter(mitarbeiter.id);
                mitarbeiterUrlaub.remove(mitTmp);
                mitarbeiterList.remove(mitarbeiter);
            }
        }catch (Exception ex) {
            throw new Exception("Mitarbeiter konnte nicht gelöscht werden: " + ex.getMessage());
        }
    }

    public static void addUrlaub(int idMitarbeiter, Urlaub urlaub) throws Exception {
        Mitarbeiter mitTmp = getMitarbeiter(idMitarbeiter);
        UrlaubList list = mitarbeiterUrlaub.get(mitTmp);

        if (list != null) {
            list.addUrlaubToList(urlaub);
        } else {
            list = new UrlaubList(mitTmp.id);
            list.addUrlaubToList(urlaub);
            mitarbeiterUrlaub.put(mitTmp, list);
        }
    }

    public static void removeUrlaub(int idMitarbeiter, Urlaub urlaub) throws Exception {
        Mitarbeiter mitTmp = getMitarbeiter(idMitarbeiter);
        UrlaubList list = mitarbeiterUrlaub.get(mitTmp);

        if(list!=null) {
            list.removeUrlaubFromList(urlaub);
        }
    }

    private static Mitarbeiter getMitarbeiter(int idMitarbeiter) throws Exception {
        Mitarbeiter mitTmp = null;
        for (Mitarbeiter mit:mitarbeiterList) {
            if(mit.id == idMitarbeiter) {
                mitTmp = mit;
            }
        }
        if(mitTmp!=null) {
            return mitTmp;
        }else {
            throw new Exception("Mitarbeiter " + idMitarbeiter + " konnte nicht gefunden werden!");
        }
    }

    public static List<Mitarbeiter> getMitarbeiterList() {
        return mitarbeiterList;
    }

    public static void SetTestData() throws Exception {
        Mitarbeiter mitarbeiter1 = new Mitarbeiter("Leon Löwe","Logistik");
        Mitarbeiter mitarbeiter2 = new Mitarbeiter("Anna Apfel","Verkauf");
        Mitarbeiter mitarbeiter3 = new Mitarbeiter("Tim Turm","Einkauf");

        Urlaub urlaub1 = new Urlaub("10/10/2024","20/10/2024",mitarbeiter1.id);
        Urlaub urlaub2 = new Urlaub("05/12/2024","18/12/2024",mitarbeiter1.id);

        DataStore.addUrlaub(mitarbeiter1.id ,urlaub1);
        DataStore.addUrlaub(mitarbeiter1.id, urlaub2);

        Urlaub urlaub3 = new Urlaub("12/05/2024","18/05/2024",mitarbeiter2.id);
        DataStore.addUrlaub(mitarbeiter2.id, urlaub3);
    }
}


