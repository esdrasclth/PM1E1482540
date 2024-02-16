package Configuracion;

public class Transacciones {

    // Nombre de la base de datos
    public static final String DBName = "DBContactPerson";

    // Creacion de las tablas de las bases de datos
    public static final String TableContactos = "contactos";

    // Creacion de los campos de la base de datos
    public static final String id = "id";
    public static final String pais = "pais";
    public static final String nombres = "nombres";
    public static final String telefonos = "telefonos";
    public static final String notas = "notas";
    public static final String imagen = "imagen";

    // Creación de la sentencia SQL para crear la tabla contactos
    public static final String CreateTableContactos = "CREATE TABLE " + TableContactos + " ("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + pais + " TEXT, "
            + nombres + " TEXT, "
            + telefonos + " TEXT, "
            + imagen + " BLOB, "
            + notas + " TEXT); ";

    // Sentencia SQL para eliminar la tabla contactos
    public static final String DropTableContactos = "DROP TABLE IF EXISTS " + TableContactos + ";";

    // Sentencia SQL para seleccionar todos los registros de la tabla contactos
    public static final String SelectAllsContactos = "SELECT * FROM " + TableContactos + ";";
}
