package model;
import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Turno implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Paciente paciente;
    private Odontologo odontologo;
    private LocalDateTime fechaHora;
    private EstadoTurno estado;

    public Turno(Long id, Paciente paciente, Odontologo odontologo, LocalDateTime fechaHora) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaHora = fechaHora;
        this.estado = EstadoTurno.PENDIENTE;
    }

    public abstract double calcularDuracionMinutos();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public Odontologo getOdontologo() { return odontologo; }
    public void setOdontologo(Odontologo odontologo) { this.odontologo = odontologo; }
    public LocalDateTime getFechaHora() { return fechaHora; }
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