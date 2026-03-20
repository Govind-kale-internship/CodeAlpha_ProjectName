package task2;
 
import java.util.*;

public class TradingService {
    private MarketDataService marketData;
    private Portfolio portfolio;
    private List<Transaction> transactionHistory;
    
    public TradingService(MarketDataService marketData, Portfolio portfolio) {
        this.marketData = marketData;
        this.portfolio = portfolio;
        this.transactionHistory = new ArrayList<>();
    }
    
    public boolean buyStock(String symbol, int quantity) {
        Stock stock = marketData.getStock(symbol);
        if (stock == null) {
            System.out.println("❌ Stock not found!");
            return false;
        }
        
        double totalCost = stock.getCurrentPrice() * quantity;
        User user = portfolio.getUser();
        
        if (user.getCashBalance() < totalCost) {
            System.out.printf("❌ Insufficient funds! Need $%.2f, have $%.2f\n", 
                totalCost, user.getCashBalance());
            return false;
        }
        
        // Process transaction
        user.deductCash(totalCost);
        portfolio.addStock(symbol, quantity, stock.getCurrentPrice());
        
        // Record transaction
        Transaction transaction = new Transaction(symbol, 
            Transaction.TransactionType.BUY, quantity, stock.getCurrentPrice());
        transactionHistory.add(transaction);
        
        System.out.printf("✅ Bought %d shares of %s at $%.2f each (Total: $%.2f)\n",
            quantity, symbol, stock.getCurrentPrice(), totalCost);
        return true;
    }
    
    public boolean sellStock(String symbol, int quantity) {
        Stock stock = marketData.getStock(symbol);
        if (stock == null) {
            System.out.println("❌ Stock not found!");
            return false;
        }
        
        int ownedShares = portfolio.getShares(symbol);
        if (ownedShares < quantity) {
            System.out.printf("❌ Insufficient shares! You own %d, trying to sell %d\n",
                ownedShares, quantity);
            return false;
        }
        
        double totalRevenue = stock.getCurrentPrice() * quantity;
        
        // Process transaction
        portfolio.getUser().addCash(totalRevenue);
        portfolio.removeStock(symbol, quantity, stock.getCurrentPrice());
        
        // Record transaction
        Transaction transaction = new Transaction(symbol, 
            Transaction.TransactionType.SELL, quantity, stock.getCurrentPrice());
        transactionHistory.add(transaction);
        
        System.out.printf("✅ Sold %d shares of %s at $%.2f each (Total: $%.2f)\n",
            quantity, symbol, stock.getCurrentPrice(), totalRevenue);
        return true;
    }
    
    public void displayTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("\n📝 No transactions yet");
            return;
        }
        
        System.out.println("\n📝 === TRANSACTION HISTORY ===");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
        System.out.println("══════════════════════════════════");
    }
    
    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}