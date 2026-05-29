package nusextended.m426;

public class NumberFormatter {
    public static String formatCurrency(double amount) {
        if (amount >= 1_000_000) {
            return String.format("%.1fM", amount / 1_000_000);
        } else if (amount >= 1_000) {
            return String.format("%.1fK", amount / 1_000);
        } else {
            return String.format("%.0f", amount);
        }
    }

    public static String formatCurrencyWithLabel(double amount) {
        String formatted = formatCurrency(amount);
        String label = amount == 1 ? "Nusian" : "Nusians";
        return formatted + " " + label;
    }

    public static String formatProductionRate(double rate) {
        return String.format("%.2f", rate) + " per second";
    }
}
