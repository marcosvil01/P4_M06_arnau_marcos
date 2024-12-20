package main;

import exercici1.main.MainGestorContactes; // Importació del punt d'entrada de l'exercici 1
import exercici2.main.MainGestorProductes; // Importació del punt d'entrada de l'exercici 2
import exercici3.main.MainBiblioteca; // Importació del punt d'entrada de l'exercici 3
import functions.Functions;

import java.util.Scanner;

/**
 * Classe principal per gestionar el menú d'execució d'exercicis.
 * Aquest menú permet seleccionar quin exercici executar, i facilita la navegació entre opcions.
 */
public class MenuPrincipal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner per llegir l'entrada de l'usuari
        int opcio; // Variable per emmagatzemar l'opció seleccionada

        // Bucle que mostra el menú fins que l'usuari seleccioni sortir (opció 0)
        do {
            // Mostra el menú a la consola
            // Opcions del menú
            System.out.println(Functions.BLACK + Functions.WHITE_BACKGROUND + "========================" + Functions.RESET);
            System.out.println(Functions.BLACK + Functions.WHITE_BACKGROUND + "    MENÚ PRINCIPAL      " + Functions.RESET);
            System.out.println(Functions.BLACK + Functions.WHITE_BACKGROUND + "========================" + Functions.RESET);
            System.out.println();
            System.out.println(Functions.BLUE + "1. " + Functions.RESET + Functions.GREEN + "Executar Exercici 1 (Gestor de Contactes)" + Functions.RESET);
            System.out.println(Functions.BLUE + "2. " + Functions.RESET + Functions.YELLOW + "Executar Exercici 2 (Gestor de Productes)" + Functions.RESET);
            System.out.println(Functions.BLUE + "3. " + Functions.RESET + Functions.CYAN + "Executar Exercici 3 (Gestor de Biblioteca)" + Functions.RESET);
            System.out.println(Functions.BLUE + "0. " + Functions.RESET + Functions.RED + "Sortir" + Functions.RESET);

            // Instrucció per seleccionar una opció
            System.out.print(Functions.PURPLE + "Selecciona una opció: " + Functions.RESET);

            // Llegeix l'opció seleccionada
            opcio = scanner.nextInt();

            // Processa l'opció seleccionada amb un switch
            switch (opcio) {
                case 1:
                    // Executa l'exercici 1: Gestor de Contactes
                    System.out.println("Executant l'exercici 1...");
                    MainGestorContactes.main(null); // Crida al punt d'entrada de l'exercici 1
                    break;
                case 2:
                    // Executa l'exercici 2: Gestor de Productes
                    System.out.println("Executant l'exercici 2...");
                    MainGestorProductes.main(null); // Crida al punt d'entrada de l'exercici 2
                    break;
                case 3:
                    // Executa l'exercici 3: Gestor de Biblioteca
                    System.out.println("Executant l'exercici 3...");
                    MainBiblioteca.main(null); // Crida al punt d'entrada de l'exercici 3
                    break;
                case 0:
                    // Opció per sortir del programa
                    System.out.println("Sortint del programa...");
                    break;
                default:
                    // Gestió d'errors per opcions no vàlides
                    System.out.println(Functions.RED_BACKGROUND + Functions.BRIGHT_WHITE + "\u001B[1m" + "Opció no vàlida. Torna-ho a intentar.");
            }
        } while (opcio != 0); // El bucle es repeteix fins que l'opció és 0 (sortir)

        scanner.close(); // Tanca el Scanner per alliberar recursos
    }
}
