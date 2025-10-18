package org.example.medico_norelacional.DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.medico_norelacional.ConnectionDB;
import org.example.medico_norelacional.Model.Cita;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {
    private final MongoCollection<Document> citasCollection;

    public CitaDAO() {
        MongoDatabase db = ConnectionDB.getDatabase();
        citasCollection = db.getCollection("citas");
    }


    //Busca todas las citas para un paciente específico.
    public List<Cita> findByPacienteId(ObjectId pacienteId) {
        List<Cita> listaCitas = new ArrayList<>();

        // El campo en la colección 'citas' es 'paciente_id'
        for (Document doc : citasCollection.find(Filters.eq("paciente_id", pacienteId))) {
            Cita cita = new Cita();
            cita.setId(doc.getObjectId("_id"));
            cita.setNumeroCita(doc.getInteger("numeroCita"));
            cita.setPacienteId(doc.getObjectId("paciente_id"));

            // Convertir java.util.Date de MongoDB a java.time.LocalDateTime para JavaFX
            cita.setFechaCita(doc.getDate("fechaCita").toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());

            cita.setEspecialidad(doc.getString("especialidad"));
            listaCitas.add(cita);
        }
        return listaCitas;
    }
}