package Clinica;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        LocalDate fecha = LocalDate.now();
        LocalDateTime fechaHora = LocalDateTime.now();

        Domicilio domicilioJuan = new Domicilio("Calle", 123, "Localidad", "Buenos Aires");
        Paciente pacienteJuan = new Paciente(1, "Juan", "Doe", 12345678, fecha, domicilioJuan);
        Odontologo docPedro = new Odontologo(1, "12345678", "Pedro", "Fernandez");
        Turno turnoManana = new Turno(1, pacienteJuan, docPedro, fechaHora);

        System.out.println(turnoManana);
    }
}
