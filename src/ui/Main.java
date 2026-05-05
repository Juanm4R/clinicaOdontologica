package ui;

import model.*;
import repository.*;
import service.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static ServicioPaciente servicioPaciente;
    private static ServicioOdontologo servicioOdontologo;
    private static ServicioTurno servicioTurno;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. INICIALIZACIÓN (Patrón Creator)
        IRepositorio<Paciente> repoPaciente = new RepositorioPaciente();
        servicioPaciente = new ServicioPaciente(repoPaciente);

        IRepositorio<Odontologo> repoOdontologo = new RepositorioOdontologo();
        servicioOdontologo = new ServicioOdontologo(repoOdontologo);

        IRepositorio<Turno> repoTurno = new RepositorioTurno();
        servicioTurno = new ServicioTurno(repoTurno);

        boolean salir = false;

        // 2. BUCLE PRINCIPAL
        while (!salir) {
            System.out.println("\n==================================");
            System.out.println("   SISTEMA CLÍNICA ODONTOLÓGICA   ");
            System.out.println("==================================");
            System.out.println("1. Gestión de Pacientes");
            System.out.println("2. Gestión de Odontólogos");
            System.out.println("3. Gestión de Turnos");
            System.out.println("0. Salir del Sistema");
            System.out.print("➤ Elija una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer (CLAVE para que no se saltee el próximo nextLine)

            switch (opcion) {
                case 1:
                    menuPacientes(scanner);
                    break;
                case 2:
                    menuOdontologos(scanner);
                    break;
                case 3:
                    menuTurnos(scanner);
                    break;
                case 0:
                    salir = true;
                    System.out.println("Cerrando el sistema... ¡Éxitos en la entrega!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
        scanner.close();
    }

    // =========================================================
    // SUBMENÚ: PACIENTES
    // =========================================================
    private static void menuPacientes(Scanner scanner) {
        System.out.println("\n--- MENÚ PACIENTES ---");
        System.out.println("1. Registrar nuevo paciente");
        System.out.println("2. Listar todos los pacientes");
        System.out.print("➤ Opción: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 1) {
            System.out.println("\n-- Datos del Paciente --");
            System.out.print("ID (Numérico): ");
            Long id = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();
            System.out.print("DNI: ");
            String dni = scanner.nextLine();

            System.out.println("-- Datos del Domicilio --");
            System.out.print("Calle: ");
            String calle = scanner.nextLine();
            System.out.print("Número: ");
            int numero = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Localidad: ");
            String localidad = scanner.nextLine();
            System.out.print("Provincia: ");
            String provincia = scanner.nextLine();

            // Armamos los objetos con la data del usuario
            Domicilio dom = new Domicilio(calle, numero, localidad, provincia);
            Paciente paciente = new Paciente(id, nombre, apellido, dni, LocalDate.now(), dom);

            try {
                servicioPaciente.registrarPaciente(paciente);
                System.out.println("✅ Paciente registrado con éxito.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else if (op == 2) {
            System.out.println("\n-- Lista de Pacientes --");
            for (Paciente p : servicioPaciente.listarTodos()) {
                System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + " " + p.getApellido() + " | DNI: " + p.getDni());
            }
        }
    }

    // =========================================================
    // SUBMENÚ: ODONTÓLOGOS
    // =========================================================
    private static void menuOdontologos(Scanner scanner) {
        System.out.println("\n--- MENÚ ODONTÓLOGOS ---");
        System.out.println("1. Registrar nuevo odontólogo");
        System.out.println("2. Listar todos los odontólogos");
        System.out.print("➤ Opción: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 1) {
            System.out.println("\n-- Datos del Odontólogo --");
            System.out.print("ID (Numérico): ");
            Long id = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();
            System.out.print("Matrícula: ");
            String matricula = scanner.nextLine();

            Odontologo odontologo = new Odontologo(id, nombre, apellido, matricula);
            servicioOdontologo.registrarOdontologo(odontologo);
            System.out.println("Odontólogo registrado con éxito.");

        } else if (op == 2) {
            System.out.println("\n-- Lista de Odontólogos --");
            for (Odontologo o : servicioOdontologo.listarTodos()) {
                System.out.println("ID: " + o.getId() + " | Dr/Dra: " + o.getApellido() + " | Matrícula: " + o.getMatricula());
            }
        }
    }

    // =========================================================
    // SUBMENÚ: TURNOS (Donde ocurre la magia de las asociaciones)
    // =========================================================
    private static void menuTurnos(Scanner scanner) {
        System.out.println("\n--- MENÚ TURNOS ---");
        System.out.println("1. Asignar nuevo turno");
        System.out.println("2. Listar todos los turnos");
        System.out.print("➤ Opción: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 1) {
            System.out.println("\n-- Asignación de Turno --");

            // 1. Validar Paciente
            System.out.print("Ingrese el ID del Paciente: ");
            Long idPaciente = scanner.nextLong();
            Paciente pacienteAsignado = servicioPaciente.buscarPaciente(idPaciente);
            if (pacienteAsignado == null) {
                System.out.println("Paciente no encontrado. Debe registrarlo primero.");
                return; // Corta la ejecución de este método y vuelve al menú principal
            }

            // 2. Validar Odontólogo
            System.out.print("Ingrese el ID del Odontólogo: ");
            Long idOdontologo = scanner.nextLong();
            Odontologo odontologoAsignado = servicioOdontologo.buscarOdontologo(idOdontologo);
            if (odontologoAsignado == null) {
                System.out.println("Odontólogo no encontrado. Debe registrarlo primero.");
                return;
            }

            // 3. Datos del Turno
            System.out.print("ID para el nuevo turno: ");
            Long idTurno = scanner.nextLong();
            scanner.nextLine();

            // Para simplificar la consola, usamos la fecha actual. En un sistema real se pediría ingresar día/hora.
            LocalDateTime fechaTurno = LocalDateTime.now().plusDays(1); // Turno para mañana
            System.out.println("ℹFecha asignada automáticamente para mañana: " + fechaTurno);

            // 4. POLIMORFISMO: Elegir tipo de turno
            System.out.println("\n¿Qué tipo de turno es?");
            System.out.println("1. Turno Regular (Control / Rutina)");
            System.out.println("2. Turno de Urgencia (Dolor / Extracción)");
            System.out.print("➤ Opción: ");
            int tipo = scanner.nextInt();
            scanner.nextLine();

            Turno nuevoTurno = null;

            if (tipo == 1) {
                nuevoTurno = new TurnoRegular(idTurno, pacienteAsignado, odontologoAsignado, fechaTurno);
            } else if (tipo == 2) {
                System.out.print("Especifique el motivo de la urgencia: ");
                String motivo = scanner.nextLine();
                nuevoTurno = new TurnoUrgente(idTurno, pacienteAsignado, odontologoAsignado, fechaTurno, motivo);
            } else {
                System.out.println("Tipo inválido. Turno cancelado.");
                return;
            }

            // Guardamos el turno usando el polimorfismo
            servicioTurno.registrarTurno(nuevoTurno);
            System.out.println("Turno asignado con éxito.");

        } else if (op == 2) {
            System.out.println("\n-- Agenda de Turnos --");
            for (Turno t : servicioTurno.listarTodos()) {
                System.out.println(t.toString());
            }
        }
    }
}