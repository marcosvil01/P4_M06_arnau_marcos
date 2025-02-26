package exercici3.view;

import exercici3.model.*;
import exercici3.controller.GestorAutors;
import exercici3.controller.GestorLlibres;
import exercici3.controller.GestorPrestecs;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal per gestionar la biblioteca amb un menú interactiu.
 */
public class MainBiblioteca {
    public static void main(String[] args) {
        // Inicialització dels gestors utilitzant connexions centralitzades
        GestorLlibres gestorLlibres = new GestorLlibres();
        GestorAutors gestorAutors = new GestorAutors();
        GestorPrestecs gestorPrestecs = new GestorPrestecs();

        Scanner scanner = new Scanner(System.in);
        int opcio;

        do {
            System.out.println("\n--- Menú Biblioteca ---");
            System.out.println("1. Gestió de Llibres");
            System.out.println("2. Gestió d'Autors");
            System.out.println("3. Gestió de Préstecs");
            System.out.println("0. Sortir");
            System.out.print("Selecciona una opció: ");
            opcio = scanner.nextInt();
            scanner.nextLine(); // Netejar el buffer

            switch (opcio) {
                case 1:
                    gestionarLlibres(gestorLlibres, scanner);
                    break;
                case 2:
                    gestionarAutors(gestorAutors, scanner);
                    break;
                case 3:
                    gestionarPrestecs(gestorPrestecs, gestorLlibres, scanner);
                    break;
                case 0:
                    System.out.println("Sortint del sistema de biblioteca...");
                    break;
                default:
                    System.out.println("Opció no vàlida. Torna-ho a intentar.");
            }
        } while (opcio != 0);

        scanner.close();
    }

    /**
     * Gestió de llibres amb un submenú.
     * @param gestorLlibres GestorLlibres
     * @param scanner Scanner per a l'entrada de l'usuari
     */
    private static void gestionarLlibres(GestorLlibres gestorLlibres, Scanner scanner) {
        int opcio;
        do {
            System.out.println("\n--- Gestió de Llibres ---");
            System.out.println("1. Afegir Llibre");
            System.out.println("2. Llistar Llibres");
            System.out.println("3. Actualitzar Llibre");
            System.out.println("4. Eliminar Llibre");
            System.out.println("0. Tornar al menú principal");
            System.out.print("Selecciona una opció: ");
            opcio = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcio) {
                    case 1:
                        System.out.print("Introdueix el títol del llibre: ");
                        String titol = scanner.nextLine();
                        System.out.print("Introdueix l'any de publicació: ");
                        int anyPublicacio = scanner.nextInt();
                        scanner.nextLine();

                        Llibre llibre = new Llibre(titol, anyPublicacio);
                        gestorLlibres.crearLlibre(llibre);
                        System.out.println("Llibre afegit: " + llibre);
                        break;

                    case 2:
                        List<Llibre> llibres = gestorLlibres.llegirLlibres();
                        System.out.println("\n--- Llista de Llibres ---");
                        for (Llibre l : llibres) {
                            System.out.println(l);
                        }
                        break;

                    case 3:
                        System.out.print("Introdueix l'ID del llibre a actualitzar: ");
                        int idActualitzar = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Introdueix el nou títol: ");
                        String nouTitol = scanner.nextLine();
                        System.out.print("Introdueix el nou any de publicació: ");
                        int nouAny = scanner.nextInt();
                        scanner.nextLine();

                        Llibre llibreActualitzat = new Llibre(nouTitol, nouAny);
                        llibreActualitzat.setId(idActualitzar);
                        gestorLlibres.actualitzarLlibre(llibreActualitzat);
                        System.out.println("Llibre actualitzat: " + llibreActualitzat);
                        break;

                    case 4:
                        System.out.print("Introdueix l'ID del llibre a eliminar: ");
                        int idEliminar = scanner.nextInt();
                        scanner.nextLine();
                        gestorLlibres.eliminarLlibre(idEliminar);
                        System.out.println("Llibre eliminat amb èxit.");
                        break;

                    case 0:
                        System.out.println("Tornant al menú principal...");
                        break;

                    default:
                        System.out.println("Opció no vàlida.");
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        } while (opcio != 0);
    }

    /**
     * Gestió d'autors amb un submenú.
     * @param gestorAutors GestorAutors
     * @param scanner Scanner per a l'entrada de l'usuari
     */
    private static void gestionarAutors(GestorAutors gestorAutors, Scanner scanner) {
        int opcio;
        do {
            System.out.println("\n--- Gestió d'Autors ---");
            System.out.println("1. Afegir Autor");
            System.out.println("2. Llistar Autors");
            System.out.println("3. Actualitzar Autor");
            System.out.println("4. Eliminar Autor");
            System.out.println("0. Tornar al menú principal");
            System.out.print("Selecciona una opció: ");
            opcio = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcio) {
                    case 1:
                        System.out.print("Introdueix el nom de l'autor: ");
                        String nom = scanner.nextLine();
                        System.out.print("Introdueix la nacionalitat: ");
                        String nacionalitat = scanner.nextLine();

                        Autor autor = new Autor(nom, nacionalitat);
                        gestorAutors.crearAutor(autor);
                        System.out.println("Autor afegit: " + autor);
                        break;

                    case 2:
                        List<Autor> autors = gestorAutors.llegirAutors();
                        System.out.println("\n--- Llista d'Autors ---");
                        for (Autor a : autors) {
                            System.out.println(a);
                        }
                        break;

                    case 3:
                        System.out.print("Introdueix l'ID de l'autor a actualitzar: ");
                        int idActualitzar = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Introdueix el nou nom: ");
                        String nouNom = scanner.nextLine();
                        System.out.print("Introdueix la nova nacionalitat: ");
                        String novaNacionalitat = scanner.nextLine();

                        Autor autorActualitzat = new Autor(nouNom, novaNacionalitat);
                        autorActualitzat.setId(idActualitzar);
                        gestorAutors.actualitzarAutor(autorActualitzat);
                        System.out.println("Autor actualitzat: " + autorActualitzat);
                        break;

                    case 4:
                        System.out.print("Introdueix l'ID de l'autor a eliminar: ");
                        int idEliminar = scanner.nextInt();
                        scanner.nextLine();
                        gestorAutors.eliminarAutor(idEliminar);
                        System.out.println("Autor eliminat amb èxit.");
                        break;

                    case 0:
                        System.out.println("Tornant al menú principal...");
                        break;

                    default:
                        System.out.println("Opció no vàlida.");
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        } while (opcio != 0);
    }

    /**
     * Gestió de préstecs amb un submenú.
     * @param gestorPrestecs GestorPrestecs
     * @param gestorLlibres GestorLlibres (necessari per relacionar préstecs amb llibres)
     * @param scanner Scanner per a l'entrada de l'usuari
     */
    private static void gestionarPrestecs(GestorPrestecs gestorPrestecs, GestorLlibres gestorLlibres, Scanner scanner) {
        int opcio;
        do {
            System.out.println("\n--- Gestió de Préstecs ---");
            System.out.println("1. Registrar Préstec");
            System.out.println("2. Llistar Préstecs");
            System.out.println("3. Actualitzar Préstec");
            System.out.println("4. Eliminar Préstec");
            System.out.println("0. Tornar al menú principal");
            System.out.print("Selecciona una opció: ");
            opcio = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcio) {
                    case 1:
                        System.out.print("Introdueix l'ID del llibre: ");
                        int llibreId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Introdueix el nom de l'usuari: ");
                        String usuari = scanner.nextLine();
                        System.out.print("Introdueix la data de préstec (YYYY-MM-DD): ");
                        String dataPrestec = scanner.nextLine();
                        System.out.print("Introdueix la data de devolució (YYYY-MM-DD) o deixa en blanc: ");
                        String dataDevolucio = scanner.nextLine();

                        Prestec prestec = new Prestec(llibreId, usuari, LocalDate.parse(dataPrestec),
                                dataDevolucio.isEmpty() ? null : LocalDate.parse(dataDevolucio));
                        gestorPrestecs.crearPrestec(prestec);
                        System.out.println("Préstec registrat: " + prestec);
                        break;

                    case 2:
                        List<Prestec> prestecs = gestorPrestecs.llegirPrestecs();
                        System.out.println("\n--- Llista de Préstecs ---");
                        for (Prestec p : prestecs) {
                            System.out.println(p);
                        }
                        break;

                    case 3:
                        System.out.print("Introdueix l'ID del préstec a actualitzar: ");
                        int idActualitzar = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Introdueix el nom del nou usuari: ");
                        String nouUsuari = scanner.nextLine();
                        System.out.print("Introdueix la nova data de préstec (YYYY-MM-DD): ");
                        String novaDataPrestec = scanner.nextLine();
                        System.out.print("Introdueix la nova data de devolució (YYYY-MM-DD) o deixa en blanc: ");
                        String novaDataDevolucio = scanner.nextLine();

                        Prestec prestecActualitzat = new Prestec(idActualitzar, nouUsuari, LocalDate.parse(novaDataPrestec),
                                novaDataDevolucio.isEmpty() ? null : LocalDate.parse(novaDataDevolucio));
                        prestecActualitzat.setId(idActualitzar);
                        gestorPrestecs.actualitzarPrestec(prestecActualitzat);
                        System.out.println("Préstec actualitzat: " + prestecActualitzat);
                        break;

                    case 4:
                        System.out.print("Introdueix l'ID del préstec a eliminar: ");
                        int idEliminar = scanner.nextInt();
                        scanner.nextLine();
                        gestorPrestecs.eliminarPrestec(idEliminar);
                        System.out.println("Préstec eliminat amb èxit.");
                        break;

                    case 0:
                        System.out.println("Tornant al menú principal...");
                        break;

                    default:
                        System.out.println("Opció no vàlida.");
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        } while (opcio != 0);
    }
}
