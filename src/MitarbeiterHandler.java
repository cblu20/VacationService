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
            response = "GET: Urlaubsanträge abrufen!";
        } else if ("POST".equals(method)) {
            response = "POST: Neuen Urlaubsantrag erstellen!";
        } else if ("PUT".equals(method)) {
            response = "PUT: Urlaubsantrag aktualisieren!";
        } else if ("DELETE".equals(method)) {
            response = "DELETE: Urlaubsantrag löschen!";
        } else {
            response = "Methode nicht unterstützt!";
            statusCode = 405;  // Method Not Allowed
        }

        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
