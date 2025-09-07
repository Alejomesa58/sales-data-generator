package edu.project.datagen;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * GenerateInfoFiles (CSV)
 *
 * <p>This version generates small CSV files (UTF-8, comma-separated).
 * No user input is required. Files are created under the project folder.</p>
 *
 * <p>Outputs:</p>
 * <ul>
 *   <li>data/products.csv → ProductID,ProductName,UnitPrice</li>
 *   <li>data/salesmen_info.csv → DocType,DocNumber,FirstName,LastName</li>
 *   <li>data/salesmen/{DocNumber}.csv → first line DocType,DocNumber;
 *       then a few sales: ProductID,Quantity</li>
 * </ul>
 *
 */
public final class GenerateInfoFiles {

    // --- Folders and files ---
    private static final Path DATA_PATH = Paths.get("data").toAbsolutePath();
    private static final Path SALESMEN_PATH = DATA_PATH.resolve("salesmen");
    private static final Path PRODUCTS_FILE = DATA_PATH.resolve("products.csv");
    private static final Path SALESMEN_INFO_FILE = DATA_PATH.resolve("salesmen_info.csv");

    // CSV delimiter
    private static final String DELIM = ",";

    // ProductID , Name , UnitPrice
    private static final String[][] PRODUCTS = {
        {"1001","Notebook","12.50"},
        {"1002","Pen","2.00"},
        {"1003","Ruler","3.25"},
        {"1004","Stapler","15.90"},
        {"1005","USB 16GB","25.00"},
        {"1006","USB 32GB","32.00"},
        {"1007","Headphones","45.00"},
        {"1008","Backpack","39.90"},
        {"1009","Water Bottle","9.50"},
        {"1010","Calculator","29.00"},
        {"1011","Mouse","11.90"},
        {"1012","Keyboard","24.90"},
        {"1013","Paper A4","6.50"},
        {"1014","Paper A5","5.20"},
        {"1015","Envelope Pack","4.75"},
        {"1016","Marker","3.10"},
        {"1017","Glue Stick","2.80"},
        {"1018","Scissors","7.40"},
        {"1019","Tape","3.60"},
        {"1020","Folder","2.90"}
    };

    // DocType , DocNumber , FirstName , LastName
    private static final String[][] SALESMEN = {
        {"CC","1010","Ana","Lopez"},
        {"TI","2020","Juan","Gomez"},
        {"PP","3030","Luisa","Perez"},
        {"CC","4040","Carlos","Ruiz"},
        {"TI","5050","Marta","Diaz"},
        {"CC","6060","Pedro","Torres"},
        {"PP","7070","Sofia","Castro"},
        {"CC","8080","Diego","Ramirez"}
    };

    // ProductID , Quantity
    private static final String[][] SAMPLE_SALES = {
        {"1001","2"},
        {"1005","1"},
        {"1012","3"},
        {"1003","4"},
        {"1010","1"},
        {"1008","2"}
    };

    private GenerateInfoFiles() { /* utility class */ }

    /** Entry point: creates folders, writes CSV files, prints a success message. */
    public static void main(String[] args) {
        try {
            prepareFolders();
            createProductsFile(PRODUCTS.length);
            createSalesManInfoFile(SALESMEN.length);

            // One small CSV per salesman (6 lines each, for simplicity)
            for (String[] s : SALESMEN) {
                String docType   = s[0];
                String docNumber = s[1];
                String fullName  = s[2] + " " + s[3];
                createSalesMenFile(6, fullName, Long.parseLong(docNumber), docType);
            }

            System.out.println("[OK] CSV files generated under /data");
        } catch (Exception e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
    }

    /** Ensures the folders (data/ and data/salesmen/) exist. */
    private static void prepareFolders() throws IOException {
        Files.createDirectories(DATA_PATH);
        Files.createDirectories(SALESMEN_PATH);
        // Optional: show where files will go
        System.out.println("DATA_PATH:     " + DATA_PATH);
        System.out.println("SALESMEN_PATH: " + SALESMEN_PATH);
    }

    /** Writes up to {@code productsCount} products as CSV: ProductID,ProductName,UnitPrice */
    public static void createProductsFile(int productsCount) throws IOException {
        int n = Math.min(productsCount, PRODUCTS.length);
        try (BufferedWriter bw = Files.newBufferedWriter(PRODUCTS_FILE, StandardCharsets.UTF_8)) {
            for (int i = 0; i < n; i++) {
                String[] p = PRODUCTS[i];
                bw.write(String.join(DELIM, p[0], p[1], p[2]));
                bw.newLine();
            }
        }
    }

    /** Writes up to {@code salesmanCount} lines as CSV: DocType,DocNumber,FirstName,LastName */
    public static void createSalesManInfoFile(int salesmanCount) throws IOException {
        int n = Math.min(salesmanCount, SALESMEN.length);
        try (BufferedWriter bw = Files.newBufferedWriter(SALESMEN_INFO_FILE, StandardCharsets.UTF_8)) {
            for (int i = 0; i < n; i++) {
                String[] s = SALESMEN[i];
                bw.write(String.join(DELIM, s[0], s[1], s[2], s[3]));
                bw.newLine();
            }
        }
    }

    /**
     * Creates a per-salesman CSV.
     * First line: DocType,DocNumber
     * Then N sales lines: ProductID,Quantity
     */
    public static void createSalesMenFile(int lines, String name, long id, String docType) throws IOException {
        Path out = SALESMEN_PATH.resolve(id + ".csv");
        try (BufferedWriter bw = Files.newBufferedWriter(out, StandardCharsets.UTF_8)) {
            bw.write(String.join(DELIM, docType, String.valueOf(id))); // header
            bw.newLine();
            for (int i = 0; i < lines && i < SAMPLE_SALES.length; i++) {
                String[] sale = SAMPLE_SALES[i];
                bw.write(String.join(DELIM, sale[0], sale[1]));
                bw.newLine();
            }
        }
    }
}