import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ExpenseTracker {
    private ArrayList<Expense> expenses = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private final String FILE_NAME = "expenses.txt"; // The file where data is saved

    public void start() {
        loadExpenses(); // Load existing data when program starts
        boolean running = true;
        System.out.println("=== Welcome to Personal Expense Tracker ===");

        while (running) {
            System.out.println("\n1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. View Total Spent");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    calculateTotal();
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting tracker. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addExpense() {
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        
        System.out.print("Enter Category (e.g., Food, Transport, Rent): ");
        String category = scanner.nextLine();
        
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        expenses.add(new Expense(date, category, amount, description));
        saveExpenses(); // Save the updated list to the file immediately
        System.out.println("Expense added and saved successfully!");
    }

    private void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }
        System.out.println("\n--- Your Expenses ---");
        for (Expense expense : expenses) {
            System.out.println(expense.toString());
        }
    }

    private void calculateTotal() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        System.out.printf("\nTotal Amount Spent: Rs.%.2f\n", total);
    }

    // --- NEW FILE I/O METHODS ---

    private void saveExpenses() {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            for (Expense expense : expenses) {
                writer.println(expense.toCSV()); // Writes each expense as a line in the text file
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not save data to file.");
        }
    }

    private void loadExpenses() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                return; // If it's the first time running, no file exists yet
            }
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split(";"); // Split the text back into 4 pieces
                if (data.length == 4) {
                    String date = data[0];
                    String category = data[1];
                    double amount = Double.parseDouble(data[2]);
                    String description = data[3];
                    expenses.add(new Expense(date, category, amount, description));
                }
            }
            fileScanner.close();
            System.out.println("Loaded " + expenses.size() + " past expenses from file.");
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not load existing data.");
        }
    }

    public static void main(String[] args) {
        ExpenseTracker tracker = new ExpenseTracker();
        tracker.start();
    }
}