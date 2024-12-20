// Classe Producte que representa un producte a la base de dades
// Inclou validacions per assegurar que les dades són vàlides
package exercici2.jdbc;

import java.io.Serializable;

/**
 * Classe Producte que representa un producte emmagatzemat en una base de dades.
 * Aquesta classe inclou informació bàsica sobre els productes, com ara identificador, nom i preu.
 * Implementa Serializable per possibilitar la serialització dels objectes Producte.
 */
public class Producte implements Serializable {
    private static final long serialVersionUID = 1L; // Identificador per a la serialització

    private int id;        // Identificador únic del producte
    private String nom;    // Nom del producte
    private double preu;   // Preu del producte

    /**
     * Constructor per defecte.
     * Es permet crear un objecte Producte sense inicialitzar les propietats.
     */
    public Producte() {}

    /**
     * Constructor amb paràmetres per inicialitzar el nom i el preu del producte.
     * Inclou validacions per assegurar que el nom no és buit i el preu és positiu.
     * @param nom Nom del producte
     * @param preu Preu del producte
     * @throws IllegalArgumentException Si el nom és buit o el preu no és vàlid
     */
    public Producte(String nom, double preu) {
        setNom(nom); // Valida el nom abans d'establir-lo
        setPreu(preu); // Valida el preu abans d'establir-lo
    }

    /**
     * Retorna l'identificador únic del producte.
     * @return Identificador del producte
     */
    public int getId() {
        return id;
    }

    /**
     * Estableix l'identificador únic del producte.
     * Aquesta propietat normalment es defineix automàticament per la base de dades.
     * @param id Identificador a assignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna el nom del producte.
     * @return Nom del producte
     */
    public String getNom() {
        return nom;
    }

    /**
     * Estableix el nom del producte amb validació.
     * No es permeten noms buits o només espais en blanc.
     * @param nom Nom del producte
     * @throws IllegalArgumentException Si el nom és buit
     */
    public void setNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("El nom del producte no pot estar buit.");
        }
        this.nom = nom;
    }

    /**
     * Retorna el preu del producte.
     * @return Preu del producte
     */
    public double getPreu() {
        return preu;
    }

    /**
     * Estableix el preu del producte amb validació.
     * Només es permeten valors positius.
     * @param preu Preu del producte
     * @throws IllegalArgumentException Si el preu és negatiu o zero
     */
    public void setPreu(double preu) {
        if (preu <= 0) {
            throw new IllegalArgumentException("El preu del producte ha de ser positiu.");
        }
        this.preu = preu;
    }

    /**
     * Retorna una representació en cadena del producte.
     * Inclou l'identificador, el nom i el preu per facilitar la visualització.
     * @return Representació en cadena del producte
     */
    @Override
    public String toString() {
        return "Producte [id=" + id + ", nom=" + nom + ", preu=" + preu + "]";
    }
}