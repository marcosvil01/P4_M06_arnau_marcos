// Classe GestorLlibres per gestionar operacions CRUD (Create, Read, Update, Delete)
// relacionades amb la taula "llibres" de la base de dades.
package exercici3.biblioteca;

import functions.Functions;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe GestorLlibres per gestionar operacions CRUD (Create, Read, Update, Delete)
 * relacionades amb la taula "llibres" de la base de dades.
 */
public class GestorLlibres {

    private PropertyChangeSupport pcs; // Suport per a notificacions de canvis

    /**
     * Constructor de GestorLlibres.
     * Inicialitza el suport per a notificacions de canvis.
     */
    public GestorLlibres() {
        this.pcs = new PropertyChangeSupport(this);
    }

    /**
     * Afegeix un nou llibre a la base de dades.
     * @param llibre L'objecte Llibre que es vol inserir
     * @throws SQLException Si hi ha algun error durant la inserció
     */
    public void crearLlibre(Llibre llibre) throws SQLException {
        String sql = "INSERT INTO llibres (titol, any_publicacio) VALUES (?, ?)";

        try (Connection conn = Functions.establirConnexio("biblioteca");
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Validació de les dades del llibre
            if (llibre.getTitol() == null || llibre.getTitol().trim().isEmpty()) {
                throw new IllegalArgumentException("El títol del llibre no pot estar buit.");
            }
            if (llibre.getAnyPublicacio() <= 0) {
                throw new IllegalArgumentException("L'any de publicació ha de ser un número positiu.");
            }

            stmt.setString(1, llibre.getTitol());
            stmt.setInt(2, llibre.getAnyPublicacio());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        llibre.setId(generatedKeys.getInt(1));
                        pcs.firePropertyChange("crearLlibre", null, llibre.getId());
                    }
                }
            }
        }
    }

    /**
     * Llegeix tots els llibres de la base de dades.
     * @return Llista d'objectes Llibre
     * @throws SQLException Si hi ha algun error durant la consulta
     */
    public List<Llibre> llegirLlibres() throws SQLException {
        List<Llibre> llibres = new ArrayList<>();
        String sql = "SELECT * FROM llibres";

        try (Connection conn = Functions.establirConnexio("biblioteca");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Llibre llibre = new Llibre();
                llibre.setId(rs.getInt("id"));
                llibre.setTitol(rs.getString("titol"));
                llibre.setAnyPublicacio(rs.getInt("any_publicacio"));
                llibres.add(llibre);
            }
        }
        return llibres;
    }

    /**
     * Actualitza la informació d'un llibre existent a la base de dades.
     * @param llibre L'objecte Llibre amb les dades actualitzades
     * @throws SQLException Si hi ha algun error durant l'actualització
     */
    public void actualitzarLlibre(Llibre llibre) throws SQLException {
        String sql = "UPDATE llibres SET titol = ?, any_publicacio = ? WHERE id = ?";

        try (Connection conn = Functions.establirConnexio("biblioteca");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Validació de les dades del llibre
            if (llibre.getTitol() == null || llibre.getTitol().trim().isEmpty()) {
                throw new IllegalArgumentException("El títol del llibre no pot estar buit.");
            }
            if (llibre.getAnyPublicacio() <= 0) {
                throw new IllegalArgumentException("L'any de publicació ha de ser un número positiu.");
            }

            stmt.setString(1, llibre.getTitol());
            stmt.setInt(2, llibre.getAnyPublicacio());
            stmt.setInt(3, llibre.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                pcs.firePropertyChange("actualitzarLlibre", null, llibre);
            }
        }
    }

    /**
     * Elimina un llibre de la base de dades.
     * @param id L'ID del llibre a eliminar
     * @throws SQLException Si hi ha algun error durant l'eliminació
     */
    public void eliminarLlibre(int id) throws SQLException {
        String sql = "DELETE FROM llibres WHERE id = ?";

        try (Connection conn = Functions.establirConnexio("biblioteca");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                pcs.firePropertyChange("eliminarLlibre", id, null);
            }
        }
    }
}
