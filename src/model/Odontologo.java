package model;

import java.util.Objects;

public class Odontologo extends Persona {
    private String matricula;

    public Odontologo() {
        super();
    }

    public Odontologo(Long id, String nombre, String apellido, String matricula) {
        super(id, nombre, apellido);
        this.matricula = matricula;
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

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