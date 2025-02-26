// Classe GestorPrestecs per gestionar operacions CRUD (Create, Read, Update, Delete)
// relacionades amb la taula "prestecs" de la base de dades.
package exercici3.controller;

import exercici3.model.Prestec;
import functions.Functions;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe GestorPrestecs per gestionar operacions CRUD (Create, Read, Update, Delete)
 * relacionades amb la taula "prestecs" de la base de dades.
 */
public class GestorPrestecs {

    private PropertyChangeSupport pcs; // Suport per a notificacions de canvis

    /**
     * Constructor de GestorPrestecs.
     * Inicialitza el suport per a notificacions de canvis.
     */
    public GestorPrestecs() {
        this.pcs = new PropertyChangeSupport(this);
    }

    /**
     * Afegeix un nou préstec a la base de dades.
     * @param prestec L'objecte Prestec que es vol inserir
     * @throws SQLException Si hi ha algun error durant la inserció
     */
    public void crearPrestec(Prestec prestec) throws SQLException {
        String sql = "INSERT INTO prestecs (llibre_id, usuari, data_prestec, data_devolucio) VALUES (?, ?, ?, ?)";

        try (Connection conn = Functions.establirConnexio("biblioteca");
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Validació de les dades del préstec
            if (prestec.getUsuari() == null || prestec.getUsuari().trim().isEmpty()) {
                throw new IllegalArgumentException("El nom de l'usuari no pot estar buit.");
            }
            if (prestec.getDataPrestec() == null) {
                throw new IllegalArgumentException("La data del préstec no pot estar buida.");
            }

            stmt.setInt(1, prestec.getLlibreId());
            stmt.setString(2, prestec.getUsuari());
            stmt.setDate(3, Date.valueOf(prestec.getDataPrestec()));
            stmt.setDate(4, prestec.getDataDevolucio() != null ? Date.valueOf(prestec.getDataDevolucio()) : null);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        prestec.setId(generatedKeys.getInt(1));
                        pcs.firePropertyChange("crearPrestec", null, prestec.getId());
                    }
                }
            }
        }
    }

    /**
     * Llegeix tots els préstecs de la base de dades.
     * @return Llista d'objectes Prestec
     * @throws SQLException Si hi ha algun error durant la consulta
     */
    public List<Prestec> llegirPrestecs() throws SQLException {
        List<Prestec> prestecs = new ArrayList<>();
        String sql = "SELECT * FROM prestecs";

        try (Connection conn = Functions.establirConnexio("biblioteca");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Prestec prestec = new Prestec();
                prestec.setId(rs.getInt("id"));
                prestec.setLlibreId(rs.getInt("llibre_id"));
                prestec.setUsuari(rs.getString("usuari"));
                prestec.setDataPrestec(rs.getDate("data_prestec").toLocalDate());
                prestec.setDataDevolucio(rs.getDate("data_devolucio") != null ? rs.getDate("data_devolucio").toLocalDate() : null);
                prestecs.add(prestec);
            }
        }
        return prestecs;
    }

    /**
     * Actualitza la informació d'un préstec existent a la base de dades.
     * @param prestec L'objecte Prestec amb les dades actualitzades
     * @throws SQLException Si hi ha algun error durant l'actualització
     */
    public void actualitzarPrestec(Prestec prestec) throws SQLException {
        String sql = "UPDATE prestecs SET llibre_id = ?, usuari = ?, data_prestec = ?, data_devolucio = ? WHERE id = ?";

        try (Connection conn = Functions.establirConnexio("biblioteca");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Validació de les dades del préstec
            if (prestec.getUsuari() == null || prestec.getUsuari().trim().isEmpty()) {
                throw new IllegalArgumentException("El nom de l'usuari no pot estar buit.");
            }
            if (prestec.getDataPrestec() == null) {
                throw new IllegalArgumentException("La data del préstec no pot estar buida.");
            }

            stmt.setInt(1, prestec.getLlibreId());
            stmt.setString(2, prestec.getUsuari());
            stmt.setDate(3, Date.valueOf(prestec.getDataPrestec()));
            stmt.setDate(4, prestec.getDataDevolucio() != null ? Date.valueOf(prestec.getDataDevolucio()) : null);
            stmt.setInt(5, prestec.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                pcs.firePropertyChange("actualitzarPrestec", null, prestec);
            }
        }
    }

    /**
     * Elimina un préstec de la base de dades.
     * @param id L'ID del préstec a eliminar
     * @throws SQLException Si hi ha algun error durant l'eliminació
     */
    public void eliminarPrestec(int id) throws SQLException {
        String sql = "DELETE FROM prestecs WHERE id = ?";

        try (Connection conn = Functions.establirConnexio("biblioteca");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                pcs.firePropertyChange("eliminarPrestec", id, null);
            }
        }
    }
}
