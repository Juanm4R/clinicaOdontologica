package Clinica;

import java.time.LocalDate;

public class Paciente {
    private Integer id;
    private String nombre;
    private String apellido;
    private Integer dni;
    private LocalDate fechaIngreso;
    private Domicilio domicilio;

    public Paciente(Integer id, String nombre, String apellido, Integer dni, LocalDate fechaIngreso, Domicilio domicilio) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "Paciente [ID: " + id + "] " + nombre + " " + apellido +
                " | DNI: " + dni + " | Alta: " + fechaIngreso +
                "\nResidencia: " + domicilio; // Acá ocurre la magia, llama a domicilio.toString()
    }
}
