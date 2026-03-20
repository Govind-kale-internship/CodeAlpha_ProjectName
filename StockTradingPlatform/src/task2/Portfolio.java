package task2;

 

import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private User user;
    private Map<String, Integer> holdings; // Stock symbol -> Quantity
    private double totalInvestment;
    private double currentValue;
    
    public Portfolio(User user) {
        this.user = user;
        this.holdings = new HashMap<>();
        this.totalInvestment = 0.0;
        this.currentValue = 0.0;
    }
    
    public void addStock(String symbol, int quantity, double purchasePrice) {
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
        totalInvestment += quantity * purchasePrice;
    }
    
    public boolean removeStock(String symbol, int quantity, double sellPrice) {
        if (!holdings.containsKey(symbol) || holdings.get(symbol) < quantity) {
            return false;
        }
        
        int currentQty = holdings.get(symbol);
        if (currentQty == quantity) {
            holdings.remove(symbol);
        } else {
            holdings.put(symbol, currentQty - quantity);
        }
        
        // Update total investment (remove cost of sold shares)
        double avgCost = getAverageCost(symbol);
        totalInvestment -= quantity * avgCost;
        
        return true;
    }
    
    public double getAverageCost(String symbol) {
        // In a real app, you'd track this per purchase
        // Simplified for demo
        return totalInvestment / getTotalShares();
    }
    
    public int getShares(String symbol) {
        return holdings.getOrDefault(symbol, 0);
    }
    
    public int getTotalShares() {
        return holdings.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    public void updateCurrentValue(Map<String, Stock> stocks) {
        currentValue = 0;
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            Stock stock = stocks.get(entry.getKey());
            if (stock != null) {
                currentValue += entry.getValue() * stock.getCurrentPrice();
            }
        }
    }
    
    public double getProfitLoss() {
        return currentValue - totalInvestment;
    }
    
    public double getProfitLossPercentage() {
        if (totalInvestment == 0) return 0;
        return (getProfitLoss() / totalInvestment) * 100;
    }
    
    // Getters
    public User getUser() { return user; }
    public Map<String, Integer> getHoldings() { return holdings; }
    public double getTotalInvestment() { return totalInvestment; }
    public double getCurrentValue() { return currentValue; }
    
    public void displayPortfolio(Map<String, Stock> stocks) {
        System.out.println("\n📊 === PORTFOLIO SUMMARY ===");
        System.out.println(user);
        System.out.println("────────────────────────────");
        
        if (holdings.isEmpty()) {
            System.out.println("No stocks in portfolio");
        } else {
            System.out.printf("%-6s | %-8s | %-10s | %-10s | %-10s\n", 
                "Symbol", "Shares", "Avg Cost", "Current", "P/L");
            System.out.println("──────────────────────────────────────────────────");
            
            for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
                String symbol = entry.getKey();
                int shares = entry.getValue();
                Stock stock = stocks.get(symbol);
                double avgCost = totalInvestment / getTotalShares(); // Simplified
                double currentPrice = stock.getCurrentPrice();
                double stockValue = shares * currentPrice;
                double profitLoss = stockValue - (shares * avgCost);
                
                System.out.printf("%-6s | %-8d | $%-9.2f | $%-9.2f | %s$%-8.2f\n",
                    symbol, shares, avgCost, currentPrice, 
                    profitLoss >= 0 ? "+" : "-", Math.abs(profitLoss));
            }
        }
        
        System.out.println("────────────────────────────");
        System.out.printf("Total Investment: $%.2f\n", totalInvestment);
        System.out.printf("Current Value:    $%.2f\n", currentValue);
        System.out.printf("Total P/L:        %s$%.2f (%.2f%%)\n",
            getProfitLoss() >= 0 ? "+" : "-", 
            Math.abs(getProfitLoss()), 
            getProfitLossPercentage());
        System.out.println("════════════════════════════════");
    }
}