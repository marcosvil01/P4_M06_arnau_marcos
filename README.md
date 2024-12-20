# Gestió de Biblioteca - Projecte DAM

## 📁 Estructura del Projecte

Aquest projecte està organitzat en diferents exercicis, cadascun representant una funcionalitat específica i utilitzant tècniques i tecnologies concretes. A continuació es detalla l'estructura:

```
projecte-biblioteca/
├── src/
│   ├── exercici1/               # Gestió de Contactes
│   │   ├── components/          # Classes Contacte i GestorContactes
│   │   └── main/                # MainGestorContactes
│   ├── exercici2/               # Gestió de Productes
│   │   ├── jdbc/                # Classes Producte i GestorProductes (JDBC)
│   │   └── main/                # MainGestorProductes
│   ├── exercici3/               # Gestió de Biblioteca
│   │   ├── biblioteca/          # Classes Llibre, Autor, Prestec i gestors associats
│   │   └── main/                # MainBiblioteca
│   ├── functions/               # Funcions reutilitzables (Colors, Configuració)
│   ├── main/                    # MenuPrincipal (punt d'entrada principal)
├── resources/
│   ├── exercici1/               # Fitxers d'exemple per a contactes
│   │   └── exemples/
│   │       └── contactes.csv    # Fitxer d'exemple de contactes
├── .env                         # Fitxer de configuració d'entorn
└── README.md                    # Documentació del projecte
```

---

## 🏗️ Configuració del .env

El fitxer `.env` és essencial per configurar la connexió a les bases de dades utilitzades en aquest projecte. Ha de col·locar-se a `src/.env` i ha de contenir la següent configuració:

```env
# Configuració de la base de dades per als productes
DB_PRODUCTES_URL=jdbc:mysql://localhost:3306/productes_db
DB_PRODUCTES_USER=usuari_productes
DB_PRODUCTES_PASSWORD=contrasenya_productes

# Configuració de la base de dades per a la biblioteca
DB_BIBLIOTECA_URL=jdbc:mysql://localhost:3306/biblioteca_db
DB_BIBLIOTECA_USER=usuari_biblioteca
DB_BIBLIOTECA_PASSWORD=contrasenya_biblioteca
```

**Nota:** Substitueix les credencials d'exemple per les teves pròpies configuracions.

---

## 📜 Scripts SQL Necessaris

Per utilitzar aquest projecte, és important configurar prèviament les bases de dades amb els scripts proporcionats. Els scripts es troben al directori `resources/sql/`.

### Script per a `productes`

```sql
CREATE DATABASE IF NOT EXISTS productes_db;
USE productes_db;

CREATE TABLE productes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    preu DECIMAL(10, 2) NOT NULL
);
```

### Script per a `biblioteca`

```sql
CREATE DATABASE IF NOT EXISTS biblioteca_db;
USE biblioteca_db;

CREATE TABLE llibres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titol VARCHAR(200) NOT NULL,
    any_publicacio INT NOT NULL
);

CREATE TABLE autors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    nacionalitat VARCHAR(100) NOT NULL
);

CREATE TABLE prestecs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    llibre_id INT NOT NULL,
    usuari VARCHAR(100) NOT NULL,
    data_prestec DATE NOT NULL,
    data_devolucio DATE,
    FOREIGN KEY (llibre_id) REFERENCES llibres(id) ON DELETE CASCADE
);
```

---

## 📂 Exercicis Inclosos

### Exercici 1: Gestió de Contactes
- Gestiona una llista de contactes emmagatzemats en un fitxer CSV.
- Classes clau:
    - `Contacte`: Representa un contacte.
    - `GestorContactes`: Permet afegir, eliminar, llegir i escriure contactes.
- Fitxer d'exemple: `resources/exercici1/exemples/contactes.csv`.

### Exercici 2: Gestió de Productes
- Gestiona productes emmagatzemats en una base de dades.
- Classes clau:
    - `Producte`: Representa un producte.
    - `GestorProductes`: Permet operacions CRUD amb notificacions de canvis.
- Script SQL: `resources/sql/productes.sql`.

### Exercici 3: Gestió de Biblioteca
- Gestiona llibres, autors i préstecs utilitzant relacions entre taules de bases de dades.
- Classes clau:
    - `Llibre`, `Autor`, `Prestec`: Representen els elements bàsics de la biblioteca.
    - `GestorLlibres`, `GestorAutors`, `GestorPrestecs`: Permeten operacions CRUD per a cada entitat.
- Script SQL: `resources/sql/biblioteca.sql`.

---

## 🌟 Funcionalitats Addicionals

- **Menús interactius amb estils visuals:**
    - Ús de colors per fer més atractiva la interfície (definits a `functions/Functions`).
- **Notificació de canvis:**
    - Ús de `PropertyChangeSupport` per gestionar notificacions.
- **Validacions avançades:**
    - Validació d'entrades per evitar errors comuns.

---

## 🚀 Instruccions d'Execució

1. Clona el repositori del projecte.
2. Configura el fitxer `.env` a `src/.env`.
3. Crea les bases de dades utilitzant els scripts SQL proporcionats.
4. Compila i executa el projecte des del punt d'entrada principal: `MenuPrincipal`.

```bash
javac -d bin src/**/*.java
java -cp bin main.MenuPrincipal
```

