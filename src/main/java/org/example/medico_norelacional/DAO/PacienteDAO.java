package org.example.medico_norelacional.DAO;

import org.bson.Document;
import org.example.medico_norelacional.Model.Paciente;
import org.example.medico_norelacional.ConnectionDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class PacienteDAO {
    private final MongoCollection<Document> pacientes;

    public PacienteDAO() {
        MongoDatabase db = ConnectionDB.getDatabase();
        pacientes = db.getCollection("pacientes");
    }

    public Paciente findByEmail(String email) {
        Document doc = pacientes.find(Filters.eq("email", email)).first();
        if (doc == null) return null;

        Paciente p = new Paciente();
        p.setId(doc.getObjectId("_id"));
        p.setEmail(doc.getString("email"));
        p.setPassword(doc.getString("password"));
        p.setNombre(doc.getString("nombre"));
        p.setDni(doc.getString("dni"));
        p.setDireccion(doc.getString("direccion"));
        p.setTelefono(doc.getString("telefono"));
        return p;
    }
}
