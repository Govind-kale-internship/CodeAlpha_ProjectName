package task2;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DataPersistenceService {
    private static final String DATA_DIR = "data";
    private static final String PORTFOLIO_FILE = DATA_DIR + "/portfolio_data.txt";
    private static final String TRANSACTIONS_FILE = DATA_DIR + "/transactions_data.txt";
    
    public DataPersistenceService() {
        createDataDirectory();
    }
    
    private void createDataDirectory() {
        File directory = new File(DATA_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    
    // Save portfolio data
    public void savePortfolio(Portfolio portfolio) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PORTFOLIO_FILE))) {
            // Save user info
            User user = portfolio.getUser();
            writer.println("USER:" + user.getUserId() + "," + user.getName() + "," + user.getCashBalance());
            
            // Save holdings
            writer.println("HOLDINGS:");
            for (Map.Entry<String, Integer> entry : portfolio.getHoldings().entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
            
            writer.println("INVESTMENT:" + portfolio.getTotalInvestment());
            
            System.out.println("✅ Portfolio saved successfully!");
        } catch (IOException e) {
            System.out.println("❌ Error saving portfolio: " + e.getMessage());
        }
    }
    
    // Save transactions
    public void saveTransactions(List<Transaction> transactions) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TRANSACTIONS_FILE))) {
            for (Transaction transaction : transactions) {
                writer.println(transaction.toFileString());
            }
            System.out.println("✅ Transactions saved successfully!");
        } catch (IOException e) {
            System.out.println("❌ Error saving transactions: " + e.getMessage());
        }
    }
    
    // Load portfolio data
    public Portfolio loadPortfolio() {
        File file = new File(PORTFOLIO_FILE);
        if (!file.exists()) {
            return null;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            User user = null;
            Map<String, Integer> holdings = new HashMap<>();
            double investment = 0;
            
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("USER:")) {
                    String[] parts = line.substring(5).split(",");
                    user = new User(parts[0], parts[1], Double.parseDouble(parts[2]));
                } else if (line.startsWith("HOLDINGS:")) {
                    // Skip header
                } else if (line.startsWith("INVESTMENT:")) {
                    investment = Double.parseDouble(line.substring(11));
                } else if (!line.isEmpty() && !line.startsWith("INVESTMENT")) {
                    String[] parts = line.split(",");
                    holdings.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
            
            if (user != null) {
                Portfolio portfolio = new Portfolio(user);
                // Reconstruct holdings (simplified)
                for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
                    portfolio.addStock(entry.getKey(), entry.getValue(), 0);
                }
                return portfolio;
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading portfolio: " + e.getMessage());
        }
        return null;
    }
    
    // Load transactions
    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(TRANSACTIONS_FILE);
        
        if (!file.exists()) {
            return transactions;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    // Simplified reconstruction
                    Transaction.TransactionType type = 
                        Transaction.TransactionType.valueOf(parts[2]);
                    Transaction transaction = new Transaction(
                        parts[1], type, Integer.parseInt(parts[3]), 
                        Double.parseDouble(parts[4])
                    );
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading transactions: " + e.getMessage());
        }
        return transactions;
    }
}