package Clinica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Turno {
    private Long id;
    private Paciente paciente;
    private Odontologo odontologo;
    private LocalDate fecha;
    private LocalTime hora;
    private EstadoTurno estado;

    public Turno() {}

    public Turno(Long id, Paciente paciente, Odontologo odontologo, LocalDate fecha, LocalTime hora, EstadoTurno estado) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public Odontologo getOdontologo() { return odontologo; }
    public void setOdontologo(Odontologo odontologo) { this.odontologo = odontologo; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public EstadoTurno getEstado() { return estado; }
    public void setEstado(EstadoTurno estado) { this.estado = estado; }

    // Métodos de negocio útiles (sugeridos por la cátedra)
    public boolean esFuturo() {
        return fecha.isAfter(LocalDate.now()) ||
                (fecha.isEqual(LocalDate.now()) && hora.isAfter(LocalTime.now()));
    }

    @Override
    public String toString() {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

        String fechaFormateada = fecha.format(formatoFecha);
        String horaFormateada = hora.format(formatoHora);
        return "=== DETALLE DEL TURNO ===\n" +
                "ID Turno: " + id + "\n" +
                "Estado: " + estado + "\n" +
                "Fecha y Hora: " + fechaFormateada + " a las " + horaFormateada + " hs\n" +
                "Atiende: " + odontologo.getNombreCompleto() + "\n" +
                "Paciente: " + paciente.getNombreCompleto() + "\n" +
                "=========================";
    }
}