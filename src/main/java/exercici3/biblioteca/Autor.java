// Classe Autor
package exercici3.biblioteca;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 * Classe Autor que representa un autor de llibres.
 */
public class Autor implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nom;
    private String nacionalitat;

    private PropertyChangeSupport pcs;

    /**
     * Constructor per defecte.
     */
    public Autor() {
        this.pcs = new PropertyChangeSupport(this);
    }

    /**
     * Constructor amb paràmetres.
     * @param nom Nom de l'autor
     * @param nacionalitat Nacionalitat de l'autor
     */
    public Autor(String nom, String nacionalitat) {
        this.nom = nom;
        this.nacionalitat = nacionalitat;
        this.pcs = new PropertyChangeSupport(this);
    }

    // Getters i setters amb notificació de canvis

    public int getId() {
        return id;
    }

    public void setId(int id) {
        int oldId = this.id;
        this.id = id;
        pcs.firePropertyChange("id", oldId, id);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        String oldNom = this.nom;
        this.nom = nom;
        pcs.firePropertyChange("nom", oldNom, nom);
    }

    public String getNacionalitat() {
        return nacionalitat;
    }

    public void setNacionalitat(String nacionalitat) {
        String oldNacionalitat = this.nacionalitat;
        this.nacionalitat = nacionalitat;
        pcs.firePropertyChange("nacionalitat", oldNacionalitat, nacionalitat);
    }

    // Suport per PropertyChangeListener
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        return "Autor [id=" + id + ", nom=" + nom + ", nacionalitat=" + nacionalitat + "]";
    }
}