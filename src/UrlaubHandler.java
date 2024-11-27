import Database.DataStore;
import Models.Mitarbeiter;
import Models.UrlaubList;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.OutputStream;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import java.io.IOException;

public class UrlaubHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "";
        int statusCode = 200;

        // Überprüfen des HTTP-Methodentyps
        String method = exchange.getRequestMethod();
        if ("GET".equals(method)) {
            response = exportiereUrlaubsdatenAlsJson();
        } else {
            response = "Methode nicht unterstützt!";
            statusCode = 405;  // Method Not Allowed
        }

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static String exportiereUrlaubsdatenAlsJson() {
        Map<Mitarbeiter, UrlaubList> mitarbeiterUrlaubMap = DataStore.mitarbeiterUrlaub;

        // Objekt zum Erstellen von JSON
        ObjectMapper objectMapper = new ObjectMapper();

        // Eine Map, die die Daten strukturiert, um sie als JSON zu speichern
        Map<String, Object> exportData = new java.util.HashMap<>();

        // Gehe durch alle Mitarbeiter in der Map
        for (Map.Entry<Mitarbeiter, UrlaubList> entry : mitarbeiterUrlaubMap.entrySet()) {
            Mitarbeiter mitarbeiter = entry.getKey();
            UrlaubList urlaubsListe = entry.getValue();

            // Erstelle eine Map für den Mitarbeiter, die seine Urlaubsinfos enthält
            Map<String, Object> mitarbeiterInfo = new java.util.HashMap<>();
            mitarbeiterInfo.put("id", mitarbeiter.id);
            mitarbeiterInfo.put("name", mitarbeiter.name);
            mitarbeiterInfo.put("abteilung", mitarbeiter.abteilung);
            mitarbeiterInfo.put("urlaube", urlaubsListe);


            exportData.put("mitarbeiter_" + mitarbeiter.id, mitarbeiterInfo);
        }

        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exportData);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
