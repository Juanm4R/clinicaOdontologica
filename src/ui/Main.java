package ui;

import java.time.LocalDate;
import model.*;
import repository.*;
import service.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        IRepositorio<Paciente> repoPaciente = new RepositorioPaciente();
        ServicioPaciente servicioPaciente = new ServicioPaciente(repoPaciente);

        // (Acá deberías inicializar también los de Odontologo y Turno)

        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- CLÍNICA ODONTOLÓGICA ---");
            System.out.println("1. Agregar un Paciente (Create)");
            System.out.println("2. Ver todos los Pacientes (Read)");
            System.out.println("3. Eliminar un Paciente (Delete)");
            System.out.println("4. Probar Polimorfismo de Turnos (Demo)");
            System.out.println("0. Salir");
            System.out.print("Elija una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese ID: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("Ingrese Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese DNI: ");
                    String dni = scanner.nextLine();

                    Domicilio dom = new Domicilio("Falsa", 123, "CABA", "Buenos Aires");
                    Paciente nuevoPaciente = new Paciente(id, nombre, "Perez", dni, LocalDate.now(), dom);

                    try {
                        servicioPaciente.registrarPaciente(nuevoPaciente);
                        System.out.println("¡Paciente registrado con éxito!");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("\n--- LISTA DE PACIENTES ---");
                    for (Paciente p : servicioPaciente.listarTodos()) {
                        // Requiere que hayas generado el toString() en Paciente
                        System.out.println("ID: " + p.getId() + " | DNI: " + p.getDni());
                    }
                    break;

                case 3:
                    System.out.print("Ingrese ID del paciente a eliminar: ");
                    Long idEliminar = scanner.nextLong();
                    servicioPaciente.eliminarPaciente(idEliminar);
                    System.out.println("Paciente eliminado.");
                    break;

                case 4:
                    System.out.println("\n--- DEMO POLIMORFISMO ---");
                    // Hardcodeamos objetos para la demo rápida
                    Odontologo o1 = new Odontologo(1L, "Dr. Muelita", "Perez", "MAT123");
                    Paciente p1 = new Paciente(99L, "Juan", "Gomez", "123", LocalDate.now(), null);

                    Turno tNormal = new Turno(1L, p1, o1, LocalDateTime.now());
                    Turno tUrgente = new TurnoUrgente(2L, p1, o1, LocalDateTime.now(), "Dolor extremo de muela");

                    System.out.println(tNormal.toString());
                    System.out.println(tUrgente.toString());
                    break;

                case 0:
                    salir = true;
                    System.out.println("Cerrando sistema...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
        scanner.close();
    }
}
