public class Expense {
    private String date;
    private String category;
    private double amount;
    private String description;

    public Expense(String date, String category, double amount, String description) {
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    // New method: Formats the data for saving to a file
    public String toCSV() {
        return date + ";" + category + ";" + amount + ";" + description;
    }

    @Override
    public String toString() {
        return String.format("Date: %s | Category: %-10s | Amount: Rs.%-7.2f | Desc: %s", 
                             date, category, amount, description);
    }
}