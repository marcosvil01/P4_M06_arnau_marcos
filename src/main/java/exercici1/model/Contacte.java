package exercici1.model;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Classe Contacte que representa un contacte amb informació bàsica.
 */
public class Contacte implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nom;
    private String email;
    private String telefon;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    public Contacte() {}

    public Contacte(String nom, String email, String telefon) {
        this.nom = nom;
        setEmail(email);
        this.telefon = telefon;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Correu electrònic no vàlid: " + email);
        }
        this.email = email;
    }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    @Override
    public String toString() {
        return String.format("%-20s %-30s %-10s", nom, email, telefon);
    }
}
