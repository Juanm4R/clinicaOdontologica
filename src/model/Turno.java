package model;

import java.time.LocalDateTime;

public abstract class Turno {
    protected Long id;
    protected Paciente paciente;
    protected Odontologo odontologo;
    protected LocalDateTime fechaHora;
    protected EstadoTurno estado;

    public Turno(Long id, Paciente paciente, Odontologo odontologo, LocalDateTime fechaHora) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaHora = fechaHora;
        this.estado = EstadoTurno.PENDIENTE;
    }

    public abstract double calcularDuracionMinutos();

    public Long getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public Odontologo getOdontologo() { return odontologo; }
    public LocalDateTime getFechaHora() { return fechaHora; }

    public void setId(Long id) { this.id = id; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setOdontologo(Odontologo odontologo) { this.odontologo = odontologo; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Paciente: " + paciente.getNombre() + " " + paciente.getApellido() +
                " | Odontólogo: " + odontologo.getApellido() +
                " | Fecha y Hora: " + fechaHora +
                " | Estado: " + estado;
    }
}