package functions;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe que conté funcions reutilitzables per a la gestió de configuracions i connexions.
 */
public class Functions {

    private static Properties properties;

    public static final String NEGRETA = "\\u0011";
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m"; // Blanc estàndard
    public static final String BRIGHT_WHITE = "\u001B[97m"; // Blanc brillant
    public static final String WHITE_BOLD = "\u001B[97m\u001B[1m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";

    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/.env"));
        } catch (IOException e) {
            System.err.println("Error carregant el fitxer .env: " + e.getMessage());
        }
    }

    /**
     * Retorna la URL de la base de dades segons el projecte especificat.
     * @param project Nom del projecte ("productes" o "biblioteca")
     * @return URL de la base de dades
     */
    public static String getDbUrl(String project) {
        return properties.getProperty("DB_" + project.toUpperCase() + "_URL");
    }

    /**
     * Retorna l'usuari de la base de dades segons el projecte especificat.
     * @param project Nom del projecte ("productes" o "biblioteca")
     * @return Usuari de la base de dades
     */
    public static String getDbUser(String project) {
        return properties.getProperty("DB_" + project.toUpperCase() + "_USER");
    }

    /**
     * Retorna la contrasenya de la base de dades segons el projecte especificat.
     * @param project Nom del projecte ("productes" o "biblioteca")
     * @return Contrasenya de la base de dades
     */
    public static String getDbPassword(String project) {
        return properties.getProperty("DB_" + project.toUpperCase() + "_PASSWORD");
    }

    /**
     * Estableix una connexió amb la base de dades utilitzant les dades del projecte especificat.
     * @param project Nom del projecte ("productes" o "biblioteca")
     * @return Un objecte Connection amb la connexió establerta
     * @throws SQLException Si hi ha algun error durant la connexió
     */
    public static Connection establirConnexio(String project) throws SQLException {
        return DriverManager.getConnection(
                getDbUrl(project),
                getDbUser(project),
                getDbPassword(project)
        );
    }

    /**
     * Tanca una connexió a la base de dades de manera segura.
     * @param conn L'objecte Connection a tancar
     */
    public static void tancarConnexio(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al tancar la connexió: " + e.getMessage());
            }
        }
    }
}
