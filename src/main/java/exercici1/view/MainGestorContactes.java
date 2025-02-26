package exercici1.view;

import exercici1.controller.GestorContactes;
import exercici1.model.Contacte;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Vista principal per interactuar amb el gestor de contactes.
 */
public class MainGestorContactes {
    private static final String ERROR_STYLE = "\u001B[41m\u001B[1;97m"; // Fons vermell, lletra blanca brillant
    private static final String RESET = "\u001B[0m";

    public static void main(String[] args) {
        GestorContactes gestor = new GestorContactes();
        Scanner scanner = new Scanner(System.in);
        String fitxer = "src/main/resources/exercici1/exemples/contactes.csv";
        int opcio;

        do {
            System.out.println("\n\u001B[47m\u001B[30m--- MENÚ GESTOR DE CONTACTES ---\u001B[0m");
            System.out.println("1. Llegeix contactes des del fitxer");
            System.out.println("2. Mostra contactes");
            System.out.println("3. Afegeix contacte manualment");
            System.out.println("4. Escriu contactes al fitxer");
            System.out.println("0. Surt");
            System.out.print("Selecciona una opció: ");

            opcio = scanner.nextInt();
            scanner.nextLine();

            switch (opcio) {
                case 1 -> {
                    try {
                        gestor.llegirContactesCSV(fitxer);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 2 -> mostrarContactesResumits(gestor.getContactes());
                case 3 -> afegirContacteManualment(gestor, scanner);
                case 4 -> {
                    try {
                        gestor.escriureContactesCSV(fitxer);
                        System.out.println("Fitxer actualitzat.");
                    } catch (IOException e) {
                        System.err.println(ERROR_STYLE + "Error: " + e.getMessage() + RESET);
                    }
                }
                case 0 -> System.out.println("Sortint...");
                default -> System.out.println(ERROR_STYLE + "Opció no vàlida." + RESET);
            }
        } while (opcio != 0);
    }

    private static void afegirContacteManualment(GestorContactes gestor, Scanner scanner) {
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telèfon: ");
        String telefon = scanner.nextLine();

        try {
            Contacte contacte = new Contacte(nom, email, telefon);
            gestor.afegirContacte(contacte);
            System.out.println("Contacte afegit amb èxit.");
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
