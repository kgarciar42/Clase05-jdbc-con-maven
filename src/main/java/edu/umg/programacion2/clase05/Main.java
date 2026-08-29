package edu.umg.programacion2.clase05;

import edu.umg.programacion2.clase05.dao.EstudianteDAO;
import edu.umg.programacion2.clase05.modelo.Estudiante;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Clase 5 - CRUD completo de estudiantes contra MySQL, CON Maven.
 *
 * IMPORTANTE: el driver de MySQL llega al proyecto como dependencia declarada
 * en pom.xml; Maven lo descarga solo. La logica de negocio (Estudiante,
 * EstudianteDAO, este menu) es identica a la del proyecto
 * clase05-jdbc-sin-maven: lo unico que cambia entre ambos proyectos es como
 * llega el driver al classpath.
 *
 * Esta clase Main SOLO se encarga de mostrar el menu y leer lo que escribe el
 * usuario. Toda la logica de base de datos vive en EstudianteDAO. Esta
 * separacion (interfaz de consola vs. acceso a datos) es la misma idea que
 * usaran despues con interfaces y con la app de Android.
 */
public class Main {

    private static final Scanner teclado = new Scanner(System.in);
    private static final EstudianteDAO estudianteDAO = new EstudianteDAO();

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    agregarEstudiante();
                    break;
                case 2:
                    listarEstudiantes();
                    break;
                case 3:
                    buscarEstudiante();
                    break;
                case 4:
                    buscarEstudiantePorEmail();
                    break;
                case 5:
                    actualizarEstudiante();
                    break;
                case 6:
                    eliminarEstudiante();
                    break;
                case 7:
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opcion invalida. Intenta de nuevo.");
            }
            System.out.println();
        } while (opcion != 7);

        teclado.close();
    }

    private static void mostrarMenu() {
        System.out.println("=== CRUD de Estudiantes (MySQL) ===");
        System.out.println("1. Agregar estudiante");
        System.out.println("2. Listar todos los estudiantes");
        System.out.println("3. Buscar estudiante por carnet");
        System.out.println("4. Buscar estudiante por email");
        System.out.println("5. Actualizar nombre de un estudiante");
        System.out.println("6. Eliminar estudiante");
        System.out.println("7. Salir");
        System.out.print("Elige una opcion: ");
    }

 // Se utiliza nextLine() para leer directamente cada dato ingresado
 // por el usuario y evitar problemas con el salto de linea pendiente.
    private static void agregarEstudiante() {
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();
        System.out.print("Carnet: ");
        String carnet = teclado.nextLine();
        boolean activo = leerActivo();
        String tipo = leerTipo();
        System.out.print("Email: ");
        String email = teclado.nextLine();

        try {
            int id = estudianteDAO.crear(new Estudiante(nombre, carnet, activo, tipo, email));
            System.out.println("Estudiante creado con id " + id);
        } catch (IllegalArgumentException e) {
            System.err.println("Error al crear el estudiante: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al crear el estudiante: " + e.getMessage());
        }
    }

    private static boolean leerActivo() {
        while (true) {
            System.out.print("Activo (Si/No): ");
            String respuesta = teclado.nextLine().trim();
            if (respuesta.equalsIgnoreCase("Si")) return true;
            if (respuesta.equalsIgnoreCase("No")) return false;
            System.out.println("Respuesta invalida. Escribe Si (activo) o No (inactivo).");
        }
    }

    private static String leerTipo() {
        while (true) {
            System.out.print("Tipo (1 = Pregrado, 2 = Postgrado): ");
            String respuesta = teclado.nextLine().trim();
            if (respuesta.equals("1")) return Estudiante.TIPO_PREGRADO;
            if (respuesta.equals("2")) return Estudiante.TIPO_POSTGRADO;
            System.out.println("Opcion invalida. Escribe 1 o 2.");
        }
    }

    private static void listarEstudiantes() {
        try {
            List<Estudiante> estudiantes = estudianteDAO.listarTodos();
            if (estudiantes.isEmpty()) {
                System.out.println("No hay estudiantes registrados todavia.");
                return;
            }
            for (Estudiante estudiante : estudiantes) {
                System.out.println(estudiante);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los estudiantes: " + e.getMessage());
        }
    }

    private static void buscarEstudiante() {
        System.out.print("Carnet a buscar: ");
        String carnet = teclado.nextLine();

        try {
            Optional<Estudiante> estudiante = estudianteDAO.buscarPorCarnet(carnet);
            if (estudiante.isPresent()) {
                System.out.println("Encontrado: " + estudiante.get());
            } else {
                System.out.println("No existe ningun estudiante con ese carnet.");
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el estudiante: " + e.getMessage());
        }
    }


    private static void buscarEstudiantePorEmail() {
        System.out.print("Email a buscar: ");
        String email = teclado.nextLine();

        try {
            Optional<Estudiante> estudiante = estudianteDAO.buscarPorEmail(email);
            if (estudiante.isPresent()) {
                System.out.println("Encontrado: " + estudiante.get());
            } else {
                System.out.println("No existe ningun estudiante con ese email.");
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el estudiante: " + e.getMessage());
        }
    }

    private static void actualizarEstudiante() {
        System.out.print("Carnet del estudiante a actualizar: ");
        String carnet = teclado.nextLine();
        System.out.print("Nuevo nombre: ");
        String nuevoNombre = teclado.nextLine();

        try {
            boolean actualizado = estudianteDAO.actualizarNombre(carnet, nuevoNombre);
            if (actualizado) {
                System.out.println("Nombre actualizado.");
            } else {
                System.out.println("No existe ningun estudiante con ese carnet.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el estudiante: " + e.getMessage());
        }
    }

    private static void eliminarEstudiante() {
        System.out.print("Carnet del estudiante a eliminar: ");
        String carnet = teclado.nextLine();

        try {
            boolean eliminado = estudianteDAO.eliminar(carnet);
            if (eliminado) {
                System.out.println("Estudiante eliminado.");
            } else {
                System.out.println("No existe ningun estudiante con ese carnet.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el estudiante: " + e.getMessage());
        }
    }
    private static int leerOpcion() {
        while (true) {
            String entrada = teclado.nextLine().trim();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.print("Opcion invalida. Escribe un numero: ");
            }
        }
    }
}
