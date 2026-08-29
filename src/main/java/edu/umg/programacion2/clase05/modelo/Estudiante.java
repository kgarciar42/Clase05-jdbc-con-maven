package edu.umg.programacion2.clase05.modelo;

/**
 * Representa un estudiante tal como se guarda en la tabla `estudiantes`.
 *
 * IMPORTANTE: esta es una clase de dominio simple: solo datos + encapsulamiento
 * (atributos privados + getters/setters). No sabe nada de SQL ni de conexiones a
 * base de datos - esa responsabilidad es de EstudianteDAO. Separar "que es un
 * estudiante" de "como se guarda un estudiante" es una idea que van a ver una y
 * otra vez en el curso.
 */
public class Estudiante {

	 // Constantes para no repetir estos strings en Main/EstudianteDAO
    public static final String TIPO_PREGRADO = "Pregrado";
    public static final String TIPO_POSTGRADO = "Postgrado";
    private int id;
    private String nombre;
    private String carnet;
    
    // Se Agrego 3 atributos nuevos
    private boolean activo;
    private String tipo;
    private String email;

    // se agregaron activo, tipo, email aqui
    public Estudiante(int id, String nombre, String carnet, boolean activo, String tipo, String email ) {
        this.id = id;
        this.nombre = nombre;
        this.carnet = carnet;
        
        // se agrego estos 3
        this.activo = activo;
        this.tipo = tipo;
        this.email = email;
    }

    // Constructor de conveniencia para cuando todavia no existe en la base de
    // datos (por eso id = 0: MySQL le va a asignar el id real al insertarlo).
    // se agrego activo, tipo, email
    public Estudiante(String nombre, String carnet , boolean activo, String tipo, String email) {
        this(0, nombre, carnet, activo, tipo, email);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarnet() {
        return carnet;
    }

    // se agrego activo
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    // se agrego tipo
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    // se agrego emial
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    // ahora incluye activo/tipo/email
    @Override
    public String toString () {
    return String.format("[%d] %s - carnet %s - %s - %s - %s",
            id, nombre, carnet, (activo ? "activo" : "inactivo"), tipo, email);
	}
}
