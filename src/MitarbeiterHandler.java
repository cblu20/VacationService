import Database.DataStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class MitarbeiterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String response = "";
        int statusCode = 200;

        // Überprüfen des HTTP-Methodentyps
        String method = exchange.getRequestMethod();
        if ("GET".equals(method)) {
            response = ExportiereMitarbeiterAlsJson();
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

    private String ExportiereMitarbeiterAlsJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(DataStore.getMitarbeiterList());
        } catch (Exception e) {
            System.err.println("Fehler beim Konvertieren der Mitarbeiter-Liste: " + e.getMessage());
            return "{FEHLER}";
        }
    }
}
