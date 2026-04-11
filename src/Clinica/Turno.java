package Clinica;

import java.time.LocalDateTime;

public class Turno {
    private Integer id;
    private Paciente paciente;
    private Odontologo odontologo;
    private LocalDateTime fechaHora;

    public Turno(Integer id, Paciente paciente, Odontologo odontologo, LocalDateTime fechaHora) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "=== DETALLE DEL TURNO ===\n" +
                "Fecha y Hora: " + fechaHora + " hs\n" +
                "Atiende: " + odontologo + "\n" +
                "Paciente: " + paciente + "\n" +
                "=========================";
    }
}
