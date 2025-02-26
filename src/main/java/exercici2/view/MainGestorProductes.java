package exercici2.view;

import exercici2.controller.GestorProductes;
import exercici2.model.Producte;
import functions.Functions;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal per provar les funcionalitats del gestor de productes.
 * Permet interactuar amb la base de dades per afegir, llegir, actualitzar i eliminar productes.
 * Inclou validacions d'entrada i millores d'estil per a una millor experiència d'usuari.
 */
public class MainGestorProductes {
    public static void main(String[] args) {
        // Configuració de la base de dades utilitzant les funcions reutilitzables
        GestorProductes gestor = new GestorProductes(
                Functions.getDbUrl("productes"),
                Functions.getDbUser("productes"),
                Functions.getDbPassword("productes")
        );

        Scanner scanner = new Scanner(System.in);
        int opcio = 0;

        do {
            // Menú d'opcions amb estil millorat
            System.out.println(Functions.WHITE_BOLD + Functions.BLUE_BACKGROUND + "--- MENÚ GESTOR DE PRODUCTES ---" + Functions.RESET);
            System.out.println(Functions.BRIGHT_WHITE + "1. Afegir un nou producte" + Functions.RESET);
            System.out.println(Functions.BRIGHT_WHITE + "2. Llistar tots els productes" + Functions.RESET);
            System.out.println(Functions.BRIGHT_WHITE + "3. Actualitzar un producte" + Functions.RESET);
            System.out.println(Functions.BRIGHT_WHITE + "4. Eliminar un producte" + Functions.RESET);
            System.out.println(Functions.BRIGHT_WHITE + "0. Sortir" + Functions.RESET);
            System.out.print(Functions.CYAN + "Selecciona una opció: " + Functions.RESET);

            try {
                opcio = scanner.nextInt();
                scanner.nextLine(); // Netejar el buffer
            } catch (InputMismatchException e) {
                System.out.println(Functions.RED_BACKGROUND + Functions.BRIGHT_WHITE + "Entrada no vàlida. Introdueix un número." + Functions.RESET);
                scanner.nextLine(); // Netejar el buffer
                continue;
            }

            switch (opcio) {
                case 1:
                    // Afegir un nou producte
                    System.out.print(Functions.YELLOW + "Introdueix el nom del producte: " + Functions.RESET);
                    String nom = scanner.nextLine();
                    System.out.print(Functions.YELLOW + "Introdueix el preu del producte: " + Functions.RESET);
                    double preu;
                    try {
                        preu = scanner.nextDouble();
                        scanner.nextLine(); // Netejar el buffer
                        if (preu <= 0) {
                            throw new IllegalArgumentException("El preu ha de ser positiu.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println(Functions.RED_BACKGROUND + Functions.BRIGHT_WHITE + "El preu ha de ser un número positiu." + Functions.RESET);
                        scanner.nextLine(); // Netejar el buffer
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println(Functions.RED_BACKGROUND + Functions.BRIGHT_WHITE + e.getMessage() + Functions.RESET);
                        break;
                    }

                    try {
                        Producte nouProducte = new Producte(nom, preu);
                        gestor.crearProducte(nouProducte);
                        System.out.println(Functions.GREEN + "Producte afegit amb èxit: " + nouProducte + Functions.RESET);
                    } catch (SQLException e) {
                        System.out.println(Functions.RED_BACKGROUND + Functions.BRIGHT_WHITE + "Error en afegir el producte: " + e.getMessage() + Functions.RESET);
                    }
                    break;

                case 2:
                    // Llistar tots els productes
                    try {
                        List<Producte> productes = gestor.llegirProductes();
                        if (productes.isEmpty()) {
                            System.out.println(Functions.YELLOW + "No hi ha productes a mostrar." + Functions.RESET);
                        } else {
                            System.out.println(Functions.WHITE_BOLD + "\n--- Llista de Productes ---" + Functions.RESET);
                            for (Producte producte : productes) {
                                System.out.println(producte);
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println(Functions.RED_BACKGROUND + Functions.BRIGHT_WHITE + "Error en llegir els productes: " + e.getMessage() + Functions.RESET);
                    }
                    break;

                case 3:
                    // Actualitzar un producte
                    System.out.print(Functions.YELLOW + "Introdueix l'ID del producte a actualitzar: " + Functions.RESET);
                    int idActualitzar;
                    try {
                        idActualitzar = scanner.nextInt();
                        scanner.nextLine(); // Netejar el buffer
                    } catch (InputMismatchException e) {
                        System.out.println(Functions.RED_BACKGROUND + Functions.BRIGHT_WHITE + "L'ID ha de ser un número enter." + Functions.RESET);
                        scanner.nextLine(); // Netejar el buffer
                        break;
                    }

                    System.out.print(Functions.YELLOW + "Introdueix el nou nom del producte: " + Functions.RESET);
                    String nouNom = scanner.nextLine();
                    System.out.print(Functions.YELLOW + "Introdueix el nou preu del producte: " + Functions.RESET);
                    double nouPreu;
                    try {
                        nouPreu = scanner.nextDouble();
                        scanner.nextLine(); // Netejar el buffer
                        if (nouPreu <= 0) {
                            throw new IllegalArgumentException("El preu ha de ser positiu.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println(Functions.RED_BACKGROUND + Functions.BRIGHT_WHITE + "El preu ha de ser un número positiu." + Functions.RESET);
                        scanner.nextLine(); // Netejar el buffer
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println(Functions.RED_BACKGROUND + Functions.BRIGHT_WHITE + e.getMessage() + Functions.RESET);
                        break;
                    }

                    try {
                        Producte producteActualitzat = new Producte(nouNom, nouPreu);
                        producteActualitzat.setId(idActualitzar);
                        gestor.actualitzarProducte(producteActualitzat);
                        System.out.println(Functions.GREEN + "Producte actualitzat amb èxit." + Functions.RESET);
                    } catch (SQLException e) {
                        System.out.println(Functions.RED_BACKGROUND + Functions.BRIGHT_WHITE + "Error en actualitzar el producte: " + e.getMessage() + Functions.RESET);
                    }
                    break;

                case 4:
                    // Eliminar un producte
                    System.out.print(Functions.YELLOW + "Introdueix l'ID del producte a eliminar: " + Functions.RESET);
                    int idEliminar;
                    try {
                        idEliminar = scanner.nextInt();
                        scanner.nextLine(); // Netejar el buffer
                    } catch (InputMismatchException e) {
                        System.out.println(Functions.RED_BACKGROUND + Functions.BRIGHT_WHITE + "L'ID ha de ser un número enter." + Functions.RESET);
                        scanner.nextLine(); // Netejar el buffer
                        break;
                    }

                    try {
                        gestor.eliminarProducte(idEliminar);
                        System.out.println(Functions.GREEN + "Producte eliminat amb èxit." + Functions.RESET);
                    } catch (SQLException e) {
                        System.out.println(Functions.RED_BACKGROUND + Functions.BRIGHT_WHITE + "Error en eliminar el producte: " + e.getMessage() + Functions.RESET);
                    }
                    break;

                case 0:
                    // Sortir del programa
                    System.out.println(Functions.CYAN + "Sortint del gestor de productes..." + Functions.RESET);
                    break;

                default:
                    // Opció no vàlida
                    System.out.println(Functions.RED_BACKGROUND + Functions.BRIGHT_WHITE + "Opció no vàlida. Torna-ho a intentar." + Functions.RESET);
            }
        } while (opcio != 0);

        System.exit(0);
    }
}
