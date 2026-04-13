package Clinica;

import java.time.LocalDate;
import java.util.Objects;

public class Paciente {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private LocalDate fechaIngreso;
    private Domicilio domicilio;

    public Paciente() {}

    public Paciente(Long id, String nombre, String apellido, String dni, String email, LocalDate fechaIngreso, Domicilio domicilio) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    public Domicilio getDomicilio() { return domicilio; }
    public void setDomicilio(Domicilio domicilio) { this.domicilio = domicilio; }

    // Métodos de Negocio sugeridos
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    // equals() basado en el DNI, ya que no puede haber dos pacientes con el mismo DNI
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(dni, paciente.dni);
    }

    @Override
    public String toString() {
        return "Paciente [ID: " + id + "] " + getNombreCompleto() +
                " | DNI: " + dni + " | Email: " + email + " | Alta: " + fechaIngreso +
                "\nResidencia: " + domicilio;
    }
}