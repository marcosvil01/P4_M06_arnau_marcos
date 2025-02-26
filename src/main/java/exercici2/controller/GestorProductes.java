// Classe GestorProductes per gestionar operacions CRUD amb notificació de canvis
package exercici2.controller;

import exercici2.model.Producte;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe GestorProductes per gestionar productes emmagatzemats en una base de dades.
 * Aquesta classe implementa operacions CRUD (Crear, Llegir, Actualitzar, Eliminar)
 * utilitzant JDBC i notifica canvis mitjançant PropertyChangeSupport.
 */
public class GestorProductes {

    private String url; // URL de la base de dades
    private String usuari; // Usuari per a la connexió a la base de dades
    private String contrasenya; // Contrasenya per a la connexió a la base de dades
    private PropertyChangeSupport pcs; // Suport per a notificacions de canvis

    /**
     * Constructor per defecte.
     * Configura els paràmetres per a la connexió a la base de dades.
     * @param url URL de la base de dades (e.g., jdbc:mysql://localhost:3306/nombd)
     * @param usuari Nom d'usuari per a la base de dades
     * @param contrasenya Contrasenya per a la base de dades
     */
    public GestorProductes(String url, String usuari, String contrasenya) {
        this.url = url;
        this.usuari = usuari;
        this.contrasenya = contrasenya;
        this.pcs = new PropertyChangeSupport(this); // Inicialitza el suport per notificacions
    }

    /**
     * Afegeix un oient per a notificacions de canvis.
     * @param listener L'oient a registrar
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Elimina un oient registrat per notificacions de canvis.
     * @param listener L'oient a eliminar
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    /**
     * Crea un nou producte a la base de dades.
     * Valida les dades abans d'inserir-les i notifica els canvis.
     * @param producte L'objecte Producte a inserir
     * @throws SQLException Si hi ha errors en la inserció
     */
    public void crearProducte(Producte producte) throws SQLException {
        if (producte.getNom() == null || producte.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("El nom del producte no pot estar buit.");
        }
        if (producte.getPreu() <= 0) {
            throw new IllegalArgumentException("El preu del producte ha de ser positiu.");
        }

        String sql = "INSERT INTO productes (nom, preu) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, usuari, contrasenya);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, producte.getNom());
            stmt.setDouble(2, producte.getPreu());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int oldId = producte.getId();
                    producte.setId(generatedKeys.getInt(1));
                    pcs.firePropertyChange("crearProducte", oldId, producte.getId());
                }
            }
        }
    }

    /**
     * Llegeix tots els productes de la base de dades.
     * Mostra els resultats en format de taula amb columnes per facilitar la visualització.
     * @return Una llista de productes
     * @throws SQLException Si hi ha errors en la consulta
     */
    public List<Producte> llegirProductes() throws SQLException {
        List<Producte> productes = new ArrayList<>();
        String sql = "SELECT * FROM productes";

        try (Connection conn = DriverManager.getConnection(url, usuari, contrasenya);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-10s %-30s %-10s\n", "ID", "Nom", "Preu");
            System.out.println("---------------------------------------------");

            while (rs.next()) {
                Producte producte = new Producte();
                producte.setId(rs.getInt("id"));
                producte.setNom(rs.getString("nom"));
                producte.setPreu(rs.getDouble("preu"));
                productes.add(producte);

                // Mostra el producte en format de taula
                System.out.printf("%-10d %-30s %-10.2f\n", producte.getId(), producte.getNom(), producte.getPreu());
            }
        }

        return productes;
    }

    /**
     * Actualitza un producte existent a la base de dades.
     * Valida les dades abans d'actualitzar-les i notifica els canvis.
     * @param producte L'objecte Producte amb les dades actualitzades
     * @throws SQLException Si hi ha errors en l'actualització
     */
    public void actualitzarProducte(Producte producte) throws SQLException {
        if (producte.getNom() == null || producte.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("El nom del producte no pot estar buit.");
        }
        if (producte.getPreu() <= 0) {
            throw new IllegalArgumentException("El preu del producte ha de ser positiu.");
        }

        String sql = "UPDATE productes SET nom = ?, preu = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, usuari, contrasenya);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producte.getNom());
            stmt.setDouble(2, producte.getPreu());
            stmt.setInt(3, producte.getId());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                pcs.firePropertyChange("actualitzarProducte", null, producte);
            }
        }
    }

    /**
     * Elimina un producte de la base de dades a partir del seu ID.
     * Notifica els canvis si l'operació té èxit.
     * @param id L'identificador del producte a eliminar
     * @throws SQLException Si hi ha errors en l'eliminació
     */
    public void eliminarProducte(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("L'ID del producte no és vàlid.");
        }

        String sql = "DELETE FROM productes WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, usuari, contrasenya);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                pcs.firePropertyChange("eliminarProducte", id, null);
            }
        }
    }
}
