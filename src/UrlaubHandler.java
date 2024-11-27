import Database.DataStore;
import Models.Mitarbeiter;
import Models.UrlaubList;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Map<String, Object> exportData = new java.util.HashMap<>();

        // Gehe durch alle Mitarbeiter in der Map
        for (Map.Entry<Mitarbeiter, UrlaubList> entry : mitarbeiterUrlaubMap.entrySet()) {
            Mitarbeiter mitarbeiter = entry.getKey();
            UrlaubList urlaubsListe = entry.getValue();

            // Erstelle eine Map für den Mitarbeiter, die seine Urlaubsinfos enthält
            Map<String, Object> mitarbeiterInfo = new java.util.LinkedHashMap<>();
            mitarbeiterInfo.put("Id", mitarbeiter.id);
            mitarbeiterInfo.put("Name", mitarbeiter.name);
            mitarbeiterInfo.put("Abteilung", mitarbeiter.abteilung);
            mitarbeiterInfo.put("Urlaube", urlaubsListe);


            exportData.put("mitarbeiter_" + mitarbeiter.id, mitarbeiterInfo);
        }

        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exportData);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
