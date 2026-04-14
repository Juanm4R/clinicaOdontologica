package model;

import java.util.Objects;

public class Odontologo {
    private Long id;
    private String nombre;
    private String apellido;
    private String matricula;

    public Odontologo() {}

    public Odontologo(Long id, String nombre, String apellido, String matricula) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    // Métodos de negocio
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    // Dos odontólogos son iguales si tienen la misma matrícula
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Odontologo that = (Odontologo) o;
        return Objects.equals(matricula, that.matricula);
    }

    @Override
    public String toString() {
        return "Odontólogo: " + getNombreCompleto() + " (Matrícula: " + matricula + ")";
    }
}