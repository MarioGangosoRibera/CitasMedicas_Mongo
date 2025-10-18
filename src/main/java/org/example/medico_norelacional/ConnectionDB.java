package org.example.medico_norelacional;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConnectionDB {

    private static MongoClient mongoClient;
    private static final String URI = "mongodb://admin:1234@localhost:27017/?authSource=admin";
    private static final String DB_NAME = "centro_medico";

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(URI);
            System.out.println("Conectado a la BD correctamente");
        }
        return mongoClient.getDatabase(DB_NAME);
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexión cerrada");
        }
    }
}

