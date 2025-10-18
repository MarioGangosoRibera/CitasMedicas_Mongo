package org.example.medico_norelacional.Model;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class Cita {
    private ObjectId id;
    private int numeroCita;
    private ObjectId pacienteId;
    private LocalDateTime fechaCita;
    private String especialidad;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getNumeroCita() {
        return numeroCita;
    }

    public void setNumeroCita(int numeroCita) {
        this.numeroCita = numeroCita;
    }

    public ObjectId getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(ObjectId pacienteId) {
        this.pacienteId = pacienteId;
    }

    public LocalDateTime getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDateTime fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
