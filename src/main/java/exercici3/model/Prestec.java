// Classe Prestec
package exercici3.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe Prestec que representa un préstec de llibres.
 */
public class Prestec implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int llibreId; // Identificador del llibre prestat
    private String usuari; // Nom de l'usuari que ha demanat el préstec
    private LocalDate dataPrestec; // Data en què s'ha prestat el llibre
    private LocalDate dataDevolucio; // Data de devolució del llibre (pot ser null)

    private PropertyChangeSupport pcs;

    /**
     * Constructor per defecte.
     */
    public Prestec() {
        this.pcs = new PropertyChangeSupport(this);
    }

    /**
     * Constructor amb paràmetres.
     * @param llibreId ID del llibre prestat
     * @param usuari Usuari que ha demanat el préstec
     * @param dataPrestec Data del préstec
     * @param dataDevolucio Data de devolució (pot ser null si encara no s'ha retornat)
     */
    public Prestec(int llibreId, String usuari, LocalDate dataPrestec, LocalDate dataDevolucio) {
        this.llibreId = llibreId;
        this.usuari = usuari;
        this.dataPrestec = dataPrestec;
        this.dataDevolucio = dataDevolucio;
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

    public int getLlibreId() {
        return llibreId;
    }

    public void setLlibreId(int llibreId) {
        int oldLlibreId = this.llibreId;
        this.llibreId = llibreId;
        pcs.firePropertyChange("llibreId", oldLlibreId, llibreId);
    }

    public String getUsuari() {
        return usuari;
    }

    public void setUsuari(String usuari) {
        String oldUsuari = this.usuari;
        this.usuari = usuari;
        pcs.firePropertyChange("usuari", oldUsuari, usuari);
    }

    public LocalDate getDataPrestec() {
        return dataPrestec;
    }

    public void setDataPrestec(LocalDate dataPrestec) {
        LocalDate oldDataPrestec = this.dataPrestec;
        this.dataPrestec = dataPrestec;
        pcs.firePropertyChange("dataPrestec", oldDataPrestec, dataPrestec);
    }

    public LocalDate getDataDevolucio() {
        return dataDevolucio;
    }

    public void setDataDevolucio(LocalDate dataDevolucio) {
        LocalDate oldDataDevolucio = this.dataDevolucio;
        this.dataDevolucio = dataDevolucio;
        pcs.firePropertyChange("dataDevolucio", oldDataDevolucio, dataDevolucio);
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
        return "Prestec [id=" + id + ", llibreId=" + llibreId + ", usuari=" + usuari + ", dataPrestec=" + dataPrestec + ", dataDevolucio=" + dataDevolucio + "]";
    }
}
