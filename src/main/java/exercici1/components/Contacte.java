package exercici1.components;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Classe Contacte que representa un contacte amb informació bàsica.
 * Aquesta classe és part del sistema per gestionar contactes en fitxers i inclou validacions per assegurar la integritat de les dades.
 */
public class Contacte implements Serializable {

    // Identificador únic per garantir compatibilitat durant la serialització
    private static final long serialVersionUID = 1L;

    // Propietats bàsiques del contacte
    private String nom;       // Nom del contacte
    private String email;     // Correu electrònic del contacte
    private String telefon;   // Telèfon del contacte

    // Patró d'expressió regular per validar el format del correu electrònic
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    /**
     * Constructor per defecte.
     * Permet crear un objecte contacte sense inicialitzar les propietats.
     * És útil en situacions on es necessita un objecte buit per després assignar les propietats.
     */
    public Contacte() {}

    /**
     * Constructor amb paràmetres per inicialitzar les propietats del contacte.
     * Inclou validació del correu electrònic per garantir que és vàlid abans de guardar-lo.
     *
     * @param nom    El nom del contacte
     * @param email  El correu electrònic del contacte
     * @param telefon El telèfon del contacte
     * @throws IllegalArgumentException Si el correu electrònic no compleix el patró definit
     */
    public Contacte(String nom, String email, String telefon) {
        this.nom = nom;            // Assigna el nom directament
        setEmail(email);           // Valida i assigna el correu electrònic
        this.telefon = telefon;    // Assigna el telèfon directament
    }

    /**
     * Retorna el nom del contacte.
     *
     * @return El nom del contacte
     */
    public String getNom() {
        return nom;
    }

    /**
     * Estableix el nom del contacte.
     *
     * @param nom El nou nom del contacte
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retorna el correu electrònic del contacte.
     *
     * @return El correu electrònic del contacte
     */
    public String getEmail() {
        return email;
    }

    /**
     * Estableix el correu electrònic del contacte.
     * Realitza una validació per assegurar que el correu compleix el format esperat.
     *
     * @param email El nou correu electrònic del contacte
     * @throws IllegalArgumentException Si el correu electrònic no és vàlid
     */
    public void setEmail(String email) {
        // Comprova si el correu electrònic compleix el patró definit
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Correu electrònic no vàlid: " + email);
        }
        this.email = email; // Assigna el correu electrònic validat
    }

    /**
     * Retorna el telèfon del contacte.
     *
     * @return El telèfon del contacte
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * Estableix el telèfon del contacte.
     * Actualment no valida el format del telèfon, però es podria afegir una validació en el futur.
     *
     * @param telefon El nou telèfon del contacte
     */
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    /**
     * Retorna una representació en format de text del contacte.
     * S'utilitza principalment per mostrar la informació del contacte en format llegible.
     *
     * @return Una cadena que representa el contacte amb format "Nom: X | Email: X | Telèfon: X"
     */
    @Override
    public String toString() {
        return String.format("Nom: %s | Email: %s | Telèfon: %s", nom, email, telefon);
    }
}
