package exercici1.main;

import exercici1.components.Contacte;
import exercici1.components.GestorContactes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal per provar el gestor de contactes amb notificacions de canvis.
 */
public class MainGestorContactes {
    private static final String ERROR_STYLE = "\u001B[41m\u001B[1;97m"; // Fons vermell, lletra blanca brillant, negreta
    private static final String RESET = "\u001B[0m"; // Per restablir l'estil

    public static void main(String[] args) {
        GestorContactes gestor = new GestorContactes();

        // Afegim un listener per detectar canvis en la llista de contactes
        gestor.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("\u001B[32mCanvi detectat en la propietat:\u001B[0m " + evt.getPropertyName());
                mostrarContactesResumits((List<Contacte>) evt.getNewValue());
            }
        });

        Scanner scanner = new Scanner(System.in);
        try {
            String fitxer = "src/main/resources/exercici1/exemples/contactes.csv";

            int opcio;
            do {
                System.out.println("\n\u001B[47m\u001B[30m--- MENÚ GESTOR DE CONTACTES ---\u001B[0m");
                System.out.println("\u001B[36m1. \u001B[0mLlegeix contactes des del fitxer");
                System.out.println("\u001B[36m2. \u001B[0mMostra contactes");
                System.out.println("\u001B[36m3. \u001B[0mAfegeix contacte manualment");
                System.out.println("\u001B[36m4. \u001B[0mEscriu contactes al fitxer");
                System.out.println("\u001B[36m5. \u001B[0mEliminar contactes duplicats");
                System.out.println("\u001B[36m0. \u001B[0mSurt");
                System.out.print("\u001B[34mSelecciona una opció: \u001B[0m");
                opcio = scanner.nextInt();
                scanner.nextLine(); // Netejar el buffer d'entrada

                switch (opcio) {
                    case 1:
                        System.out.println("\u001B[36mLlegeix els contactes del fitxer...\u001B[0m");
                        gestor.llegirContactesCSV(fitxer);
                        break;

                    case 2:
                        System.out.println("\u001B[36mContactes actuals:\u001B[0m");
                        mostrarContactesResumits(gestor.getContactes());
                        break;

                    case 3:
                        System.out.println("\u001B[36mIntrodueix les dades del nou contacte:\u001B[0m");
                        afegirContacteManualment(gestor, scanner);
                        break;

                    case 4:
                        System.out.println("\u001B[36mEscrivint els contactes al fitxer...\u001B[0m");
                        gestor.escriureContactesCSV(fitxer);
                        System.out.println("\u001B[32mFitxer actualitzat.\u001B[0m");
                        break;

                    case 5:
                        System.out.println("\u001B[36mEliminant contactes duplicats...\u001B[0m");
                        gestor.eliminarDuplicats();
                        System.out.println("\u001B[32mDuplicats eliminats.\u001B[0m");
                        break;

                    case 0:
                        System.out.println("\u001B[36mSortint...\u001B[0m");
                        System.exit(0);
                        break;

                    default:
                        System.out.println(ERROR_STYLE + "Opció no vàlida. Torna-ho a intentar." + RESET);
                }
            } while (opcio != 0);

        } catch (IOException e) {
            System.err.println(ERROR_STYLE + "Error gestionant el fitxer CSV: " + e.getMessage() + RESET);
        }
    }

    private static void afegirContacteManualment(GestorContactes gestor, Scanner scanner) {
        try {
            System.out.print("\u001B[33mNom: \u001B[0m");
            String nom = scanner.nextLine();
            if (nom.isEmpty()) {
                throw new IllegalArgumentException("El nom no pot estar buit.");
            }

            System.out.print("\u001B[33mEmail: \u001B[0m");
            String email = scanner.nextLine();
            if (!email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                throw new IllegalArgumentException("El correu electrònic no és vàlid.");
            }

            System.out.print("\u001B[33mTelèfon: \u001B[0m");
            String telefon = scanner.nextLine();
            if (!telefon.matches("\\d{9}")) {
                throw new IllegalArgumentException("El telèfon ha de tenir 9 dígits numèrics.");
            }

            Contacte contacte = new Contacte(nom, email, telefon);
            gestor.afegirContacte(contacte);
            System.out.println("\u001B[32mContacte afegit amb èxit:\u001B[0m " + contacte);

        } catch (IllegalArgumentException e) {
            System.err.println(ERROR_STYLE + "Error: " + e.getMessage() + RESET);
        }
    }

    private static void mostrarContactesResumits(List<Contacte> contactes) {
        System.out.println("\u001B[47m\u001B[30mNom                  Email                          Telèfon        \u001B[0m");
        for (Contacte contacte : contactes) {
            System.out.printf("\u001B[36m%-20s %-30s %-15s\u001B[0m\n", contacte.getNom(), contacte.getEmail(), contacte.getTelefon());
        }
    }
}
