package Clinica;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Turno {
    private Integer id;
    private Paciente paciente;
    private Odontologo odontologo;
    private LocalDateTime fechaHora;

    public Turno(){}

    public Turno(Integer id, Paciente paciente, Odontologo odontologo, LocalDateTime fechaHora) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaHora = fechaHora;
    }

    public Integer getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Odontologo getOdontologo() {
        return odontologo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String fechaFormateada = fechaHora.format(formato);

        return "=== DETALLE DEL TURNO ===\n" +
                "Fecha y Hora: " + fechaFormateada + " hs\n" +
                "Atiende: " + odontologo + "\n" +
                "Paciente: " + paciente + "\n" +
                "=========================";
    }
}
