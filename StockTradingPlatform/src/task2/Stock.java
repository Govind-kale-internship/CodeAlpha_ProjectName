package task2;

 
import java.text.DecimalFormat;

public class Stock {
    private String symbol;
    private String companyName;
    private double currentPrice;
    private double previousPrice;
    private double changePercentage;
    
    public Stock(String symbol, String companyName, double currentPrice) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.previousPrice = currentPrice;
        this.changePercentage = 0.0;
    }
    
    // Update stock price with random fluctuation
    public void updatePrice(double newPrice) {
        this.previousPrice = this.currentPrice;
        this.currentPrice = newPrice;
        this.changePercentage = ((currentPrice - previousPrice) / previousPrice) * 100;
    }
    
    // Getters and Setters
    public String getSymbol() { return symbol; }
    public String getCompanyName() { return companyName; }
    public double getCurrentPrice() { return currentPrice; }
    public double getPreviousPrice() { return previousPrice; }
    public double getChangePercentage() { return changePercentage; }
    
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String change = (changePercentage >= 0 ? "↑" : "↓");
        return String.format("%-6s | %-20s | $%-8s | %s %.2f%%", 
            symbol, companyName, df.format(currentPrice), change, changePercentage);
    }
    
    public String toFileString() {
        return symbol + "," + companyName + "," + currentPrice;
    }
}