package model;

import java.time.LocalDateTime;

public class TurnoRegular extends Turno {
    public TurnoRegular(Long id, Paciente paciente, Odontologo odontologo, LocalDateTime fechaHora) {
        super(id, paciente, odontologo, fechaHora);
    }

    @Override
    public double calcularDuracionMinutos() {
        return 30.0;
    }

    @Override
    public String toString() {
        return "[Turno REGULAR] " + super.toString() + " | Duración: " + calcularDuracionMinutos() + " min";
    }
}
