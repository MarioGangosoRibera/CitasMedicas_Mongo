package org.example.medico_norelacional.DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.medico_norelacional.ConnectionDB;
import org.example.medico_norelacional.Model.Cita;

import java.time.LocalDate;
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

    public void insertNuevaCita(ObjectId pacienteId, String especialidad, LocalDate fecha) {
        Document doc = new Document("numeroCita", generarNumeroCita(pacienteId))
                .append("paciente_id", pacienteId)
                .append("fechaCita", fecha.atStartOfDay())
                .append("especialidad", especialidad);
        citasCollection.insertOne(doc);
    }

    private int generarNumeroCita(ObjectId pacienteId) {
        // cuenta las citas que tiene el paciente y suma 1
        return (int) citasCollection.countDocuments(Filters.eq("paciente_id", pacienteId)) + 1;
    }

    public void deleteCita(ObjectId citaId) {
        citasCollection.deleteOne(Filters.eq("_id", citaId));
    }

    public void updateCita(ObjectId citaId, String especialidad, LocalDate fecha) {
        citasCollection.updateOne(
                Filters.eq("_id", citaId),
                Updates.combine(
                        Updates.set("especialidad", especialidad),
                        Updates.set("fechaCita", fecha.atStartOfDay())
                )
        );
    }

}