package exercici1.controller;

import exercici1.model.Contacte;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador per gestionar la llista de contactes.
 */
public class GestorContactes {
    private List<Contacte> contactes;
    private PropertyChangeSupport propertyChangeSupport;

    public GestorContactes() {
        this.contactes = new ArrayList<>();
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void afegirContacte(Contacte contacte) {
        if (esContacteDuplicat(contacte)) {
            throw new IllegalArgumentException("El contacte ja existeix.");
        }
        List<Contacte> antigaLlista = new ArrayList<>(this.contactes);
        this.contactes.add(contacte);
        propertyChangeSupport.firePropertyChange("contactes", antigaLlista, this.contactes);
    }

    public List<Contacte> getContactes() {
        return new ArrayList<>(contactes);
    }

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

    public void escriureContactesCSV(String fitxer) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fitxer))) {
            for (Contacte contacte : this.contactes) {
                writer.write(contacte.getNom() + "," + contacte.getEmail() + "," + contacte.getTelefon());
                writer.newLine();
            }
        }
    }

    private boolean esContacteDuplicat(Contacte contacte) {
        return contactes.stream().anyMatch(c ->
                c.getNom().equalsIgnoreCase(contacte.getNom()) &&
                        c.getEmail().equalsIgnoreCase(contacte.getEmail()) &&
                        c.getTelefon().equals(contacte.getTelefon()));
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
