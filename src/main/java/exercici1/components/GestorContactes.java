package exercici1.components;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe GestorContactes per gestionar una llista de contactes.
 * Aquesta classe ofereix funcionalitats per afegir, eliminar, llegir i escriure contactes en fitxers.
 * A més, utilitza PropertyChangeSupport per notificar canvis als oients registrats.
 */
public class GestorContactes {

    // Llista que conté tots els contactes gestionats
    private List<Contacte> contactes;

    // Suport per notificar als oients quan hi ha canvis en la llista de contactes
    private PropertyChangeSupport propertyChangeSupport;

    /**
     * Constructor per defecte.
     * Inicialitza la llista de contactes com una ArrayList buida.
     * Inicialitza PropertyChangeSupport per gestionar notificacions de canvis.
     */
    public GestorContactes() {
        this.contactes = new ArrayList<>();
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Afegeix un contacte a la llista.
     * Abans d'afegir-lo, comprova si ja existeix per evitar duplicats.
     * Notifica als oients si s'ha afegit amb èxit.
     *
     * @param contacte El contacte a afegir
     * @throws IllegalArgumentException Si el contacte ja existeix
     */
    public void afegirContacte(Contacte contacte) {
        if (esContacteDuplicat(contacte)) {
            throw new IllegalArgumentException("El contacte ja existeix.");
        }
        List<Contacte> antigaLlista = new ArrayList<>(this.contactes);
        this.contactes.add(contacte);
        // Notifiquem el canvi de la llista als oients
        propertyChangeSupport.firePropertyChange("contactes", antigaLlista, this.contactes);
    }

    /**
     * Retorna una còpia de la llista de contactes.
     * Això garanteix que la llista interna no es pugui modificar directament.
     *
     * @return Una còpia de la llista de contactes
     */
    public List<Contacte> getContactes() {
        return new ArrayList<>(contactes);
    }

    /**
     * Llegeix contactes d'un fitxer CSV.
     * Cada línia del fitxer ha de tenir el format: nom,email,telefon.
     * Els contactes duplicats no es carreguen.
     *
     * @param fitxer Ruta al fitxer CSV
     * @throws IOException Si hi ha un problema amb la lectura del fitxer
     */
    public void llegirContactesCSV(String fitxer) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fitxer))) {
            String linia;
            while ((linia = reader.readLine()) != null) {
                String[] dades = linia.split(",");
                if (dades.length == 3) {
                    Contacte contacte = new Contacte(dades[0], dades[1], dades[2]);
                    if (!esContacteDuplicat(contacte)) {
                        afegirContacte(contacte);
                    }
                }
            }
        }
    }

    /**
     * Escriu tots els contactes al fitxer especificat en format CSV.
     *
     * @param fitxer Ruta del fitxer on es guardaran els contactes
     * @throws IOException Si hi ha un problema amb l'escriptura del fitxer
     */
    public void escriureContactesCSV(String fitxer) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fitxer))) {
            for (Contacte contacte : this.contactes) {
                writer.write(contacte.getNom() + "," + contacte.getEmail() + "," + contacte.getTelefon());
                writer.newLine();
            }
        }
    }

    /**
     * Elimina contactes duplicats de la llista.
     * Només conserva el primer contacte únic.
     * Notifica als oients si hi ha canvis en la llista.
     */
    public void eliminarDuplicats() {
        List<Contacte> contactesUnics = new ArrayList<>();
        for (Contacte contacte : contactes) {
            if (!contactesUnics.contains(contacte)) {
                contactesUnics.add(contacte);
            }
        }
        List<Contacte> antigaLlista = new ArrayList<>(this.contactes);
        this.contactes.clear();
        this.contactes.addAll(contactesUnics);
        // Notifiquem el canvi de la llista als oients
        propertyChangeSupport.firePropertyChange("contactes", antigaLlista, this.contactes);
    }

    /**
     * Comprova si un contacte ja existeix en la llista.
     * Es considera duplicat si nom, email i telèfon són iguals.
     *
     * @param contacte El contacte a comprovar
     * @return True si el contacte ja existeix, False altrament
     */
    private boolean esContacteDuplicat(Contacte contacte) {
        for (Contacte c : contactes) {
            if (c.getNom().equalsIgnoreCase(contacte.getNom())
                    && c.getEmail().equalsIgnoreCase(contacte.getEmail())
                    && c.getTelefon().equals(contacte.getTelefon())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Afegeix un oient que serà notificat de canvis en la llista de contactes.
     * Aquesta funcionalitat és útil per actualitzar la UI o generar registres de canvis.
     *
     * @param listener L'oient a afegir
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Elimina un oient registrat per a notificacions de canvis.
     * Encara que no s'utilitza actualment, es proporciona per complir amb el patró de disseny.
     *
     * @param listener L'oient a eliminar
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
