package model;
import java.io.Serializable;
import java.util.Objects;

public class Odontologo extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    private String matricula;

    public Odontologo() { super(); }

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
    public String toString() { return "Dr/Dra. " + getNombreCompleto() + " (Mat: " + matricula + ")"; }
}