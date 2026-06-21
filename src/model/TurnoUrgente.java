package model;
import java.time.LocalDateTime;

public class TurnoUrgente extends Turno {
    private static final long serialVersionUID = 1L;
    private String motivoUrgencia;

    public TurnoUrgente(Long id, Paciente paciente, Odontologo odontologo, LocalDateTime fechaHora, String motivoUrgencia) {
        super(id, paciente, odontologo, fechaHora);
        this.motivoUrgencia = motivoUrgencia;
    }
    @Override
    public double calcularDuracionMinutos() { return 60.0; }
    public String getMotivoUrgencia() { return motivoUrgencia; }
    public void setMotivoUrgencia(String motivoUrgencia) { this.motivoUrgencia = motivoUrgencia; }
    @Override
    public String toString() { return "[URGENTE] " + super.toString() + " | Motivo: " + motivoUrgencia + " (60 min)"; }
}