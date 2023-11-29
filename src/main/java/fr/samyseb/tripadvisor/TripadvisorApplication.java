package fr.samyseb.tripadvisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TripadvisorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripadvisorApplication.class, args);
        String serverAddress = System.getProperty("server.address", "localhost");
        String serverPort = System.getProperty("server.port", "8080");
        System.out.println("L'application est démarrée. Accédez à l'index à : http://" + serverAddress + ":" + serverPort);
    }

}
