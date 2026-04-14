package main;

import model.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- INICIANDO SISTEMA CLÍNICA SONRISA FELIZ ---\n");

        //Fecha de alta en la clinica
        LocalDate fecha = LocalDate.now();

        // Agendamos el turno para mañana a las 10:30 am
        LocalDate fechaTurno = LocalDate.now().plusDays(1);
        LocalTime horaTurno = LocalTime.of(10, 30);

        Domicilio domicilioJuan = new Domicilio(1L, "Calle", "123", "Localidad", "Buenos Aires");
        Paciente pacienteJuan = new Paciente(14563L, "Juan", "Doe", "12345678", fecha, domicilioJuan);
        Odontologo docPedro = new Odontologo(1L, "Pedro", "Fernandez", "MP-12345");
        Turno turnoManana = new Turno(1L, pacienteJuan, docPedro, fechaTurno, horaTurno, EstadoTurno.PENDIENTE);

        // Imprimimos por consola para validar que todo se enlazó correctamente
        System.out.println("Datos del Paciente:");
        System.out.println(pacienteJuan);

        System.out.println("\nDatos del Odontólogo:");
        System.out.println(docPedro);

        System.out.println("\nDatos del Turno:");
        System.out.println(turnoManana);
    }
}
