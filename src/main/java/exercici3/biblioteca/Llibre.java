// Classe Llibre
package exercici3.biblioteca;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Llibre que representa un llibre a la biblioteca.
 * Inclou suport per PropertyChangeSupport per notificar canvis en les seves propietats.
 */
public class Llibre implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String titol;
    private int anyPublicacio;
    private List<Autor> autors;

    private PropertyChangeSupport pcs; // Suport per a notificacions de canvis

    /**
     * Constructor per defecte.
     */
    public Llibre() {
        this.autors = new ArrayList<>();
        this.pcs = new PropertyChangeSupport(this);
    }

    /**
     * Constructor amb paràmetres.
     * @param titol Títol del llibre
     * @param anyPublicacio Any de publicació del llibre
     */
    public Llibre(String titol, int anyPublicacio) {
        this.titol = titol;
        this.anyPublicacio = anyPublicacio;
        this.autors = new ArrayList<>();
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

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        String oldTitol = this.titol;
        this.titol = titol;
        pcs.firePropertyChange("titol", oldTitol, titol);
    }

    public int getAnyPublicacio() {
        return anyPublicacio;
    }

    public void setAnyPublicacio(int anyPublicacio) {
        int oldAnyPublicacio = this.anyPublicacio;
        this.anyPublicacio = anyPublicacio;
        pcs.firePropertyChange("anyPublicacio", oldAnyPublicacio, anyPublicacio);
    }

    public List<Autor> getAutors() {
        return autors;
    }

    public void addAutor(Autor autor) {
        List<Autor> oldAutors = new ArrayList<>(this.autors);
        this.autors.add(autor);
        pcs.firePropertyChange("autors", oldAutors, this.autors);
    }

    public void removeAutor(Autor autor) {
        List<Autor> oldAutors = new ArrayList<>(this.autors);
        this.autors.remove(autor);
        pcs.firePropertyChange("autors", oldAutors, this.autors);
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
        return "Llibre [id=" + id + ", titol=" + titol + ", anyPublicacio=" + anyPublicacio + "]";
    }
}
