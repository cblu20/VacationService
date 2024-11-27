import Database.DataStore;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
public class VacationService
{
    public static void main(String[] args) throws Exception {
        // HTTP-Server erstellen und auf Port 8080 hören
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // REST-Routen konfigurieren
        server.createContext("/api/urlaub", new UrlaubHandler());  // Hier wird die Route '/api/vacations' definiert
        server.createContext("/api/mitarbeiter", new MitarbeiterHandler());
        // Server starten
        server.setExecutor(null); // Verwende den Standard-Executor
        server.start();
        DataStore.SetTestData();

        System.out.println("Server läuft auf http://localhost:8080");
    }



}
