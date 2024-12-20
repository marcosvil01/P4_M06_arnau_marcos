// Classe GestorAutors per gestionar operacions CRUD (Create, Read, Update, Delete)
// relacionades amb la taula "autors" de la base de dades.
package exercici3.biblioteca;

import functions.Functions;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe GestorAutors per gestionar operacions CRUD (Create, Read, Update, Delete)
 * relacionades amb la taula "autors" de la base de dades.
 */
public class GestorAutors {

    private PropertyChangeSupport pcs; // Suport per a notificacions de canvis

    /**
     * Constructor de GestorAutors.
     * Inicialitza el suport per a notificacions de canvis.
     */
    public GestorAutors() {
        this.pcs = new PropertyChangeSupport(this);
    }

    /**
     * Afegeix un nou autor a la base de dades.
     * @param autor L'objecte Autor que es vol inserir
     * @throws SQLException Si hi ha algun error durant la inserció
     */
    public void crearAutor(Autor autor) throws SQLException {
        String sql = "INSERT INTO autors (nom, nacionalitat) VALUES (?, ?)";

        try (Connection conn = Functions.establirConnexio("biblioteca");
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Validació de les dades de l'autor
            if (autor.getNom() == null || autor.getNom().trim().isEmpty()) {
                throw new IllegalArgumentException("El nom de l'autor no pot estar buit.");
            }
            if (autor.getNacionalitat() == null || autor.getNacionalitat().trim().isEmpty()) {
                throw new IllegalArgumentException("La nacionalitat de l'autor no pot estar buida.");
            }

            stmt.setString(1, autor.getNom());
            stmt.setString(2, autor.getNacionalitat());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        autor.setId(generatedKeys.getInt(1));
                        pcs.firePropertyChange("crearAutor", null, autor.getId());
                    }
                }
            }
        }
    }

    /**
     * Llegeix tots els autors de la base de dades.
     * @return Llista d'objectes Autor
     * @throws SQLException Si hi ha algun error durant la consulta
     */
    public List<Autor> llegirAutors() throws SQLException {
        List<Autor> autors = new ArrayList<>();
        String sql = "SELECT * FROM autors";

        try (Connection conn = Functions.establirConnexio("biblioteca");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Autor autor = new Autor();
                autor.setId(rs.getInt("id"));
                autor.setNom(rs.getString("nom"));
                autor.setNacionalitat(rs.getString("nacionalitat"));
                autors.add(autor);
            }
        }
        return autors;
    }

    /**
     * Actualitza la informació d'un autor existent a la base de dades.
     * @param autor L'objecte Autor amb les dades actualitzades
     * @throws SQLException Si hi ha algun error durant l'actualització
     */
    public void actualitzarAutor(Autor autor) throws SQLException {
        String sql = "UPDATE autors SET nom = ?, nacionalitat = ? WHERE id = ?";

        try (Connection conn = Functions.establirConnexio("biblioteca");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Validació de les dades de l'autor
            if (autor.getNom() == null || autor.getNom().trim().isEmpty()) {
                throw new IllegalArgumentException("El nom de l'autor no pot estar buit.");
            }
            if (autor.getNacionalitat() == null || autor.getNacionalitat().trim().isEmpty()) {
                throw new IllegalArgumentException("La nacionalitat de l'autor no pot estar buida.");
            }

            stmt.setString(1, autor.getNom());
            stmt.setString(2, autor.getNacionalitat());
            stmt.setInt(3, autor.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                pcs.firePropertyChange("actualitzarAutor", null, autor);
            }
        }
    }

    /**
     * Elimina un autor de la base de dades.
     * @param id L'ID de l'autor a eliminar
     * @throws SQLException Si hi ha algun error durant l'eliminació
     */
    public void eliminarAutor(int id) throws SQLException {
        String sql = "DELETE FROM autors WHERE id = ?";

        try (Connection conn = Functions.establirConnexio("biblioteca");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                pcs.firePropertyChange("eliminarAutor", id, null);
            }
        }
    }
}
