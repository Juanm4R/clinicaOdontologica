package model;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Paciente extends Persona implements Comparable<Paciente>, Serializable {
    private static final long serialVersionUID = 1L;
    private String dni;
    private LocalDate fechaIngreso;
    private Domicilio domicilio;

    public Paciente() { super(); }

    public Paciente(Long id, String nombre, String apellido, String dni, LocalDate fechaIngreso, Domicilio domicilio) {
        super(id, nombre, apellido);
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    public Domicilio getDomicilio() { return domicilio; }
    public void setDomicilio(Domicilio domicilio) { this.domicilio = domicilio; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(dni, paciente.dni);
    }

    @Override
    public String toString() { return getNombreCompleto() + " (DNI: " + dni + ")"; }

    @Override
    public int compareTo(Paciente otro) {
        int comparacionApellido = this.apellido.compareToIgnoreCase(otro.getApellido());
        if (comparacionApellido == 0) return this.nombre.compareToIgnoreCase(otro.getNombre());
        return comparacionApellido;
    }
}