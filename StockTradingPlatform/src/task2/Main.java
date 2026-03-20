package task2;

 
import java.util.*;

public class Main {
    private static MarketDataService marketData;
    private static Portfolio portfolio;
    private static TradingService trading;
    private static DataPersistenceService persistence;
    private static Scanner scanner;
    private static boolean running = true;
    
    public static void main(String[] args) {
        initialize();
        
        System.out.println("==========================================");
        System.out.println("📈 STOCK TRADING PLATFORM");
        System.out.println("==========================================");
        
        while (running) {
            displayMainMenu();
            
            System.out.print("Enter your choice (1-9): ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    viewMarketData();
                    break;
                case "2":
                    buyStock();
                    break;
                case "3":
                    sellStock();
                    break;
                case "4":
                    viewPortfolio();
                    break;
                case "5":
                    viewTransactionHistory();
                    break;
                case "6":
                    simulateMarketUpdate();
                    break;
                case "7":
                    saveData();
                    break;
                case "8":
                    loadData();
                    break;
                case "9":
                    exit();
                    break;
                default:
                    System.out.println("❌ Invalid choice!");
            }
            
            if (running && !choice.equals("9")) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void initialize() {
        scanner = new Scanner(System.in);
        persistence = new DataPersistenceService();
        marketData = new MarketDataService();
        
        // Try to load existing portfolio
        Portfolio loaded = persistence.loadPortfolio();
        if (loaded != null) {
            portfolio = loaded;
            List<Transaction> transactions = persistence.loadTransactions();
            trading = new TradingService(marketData, portfolio);
            System.out.println("✅ Previous data loaded successfully!");
        } else {
            // Create new user
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            User user = new User("U" + System.currentTimeMillis(), name, 10000.00);
            portfolio = new Portfolio(user);
            trading = new TradingService(marketData, portfolio);
        }
    }
    
    private static void displayMainMenu() {
        System.out.println("\n🔧 === MAIN MENU ===");
        System.out.println("1. 📊 View Market Data");
        System.out.println("2. 💰 Buy Stock");
        System.out.println("3. 💸 Sell Stock");
        System.out.println("4. 📋 View Portfolio");
        System.out.println("5. 📝 Transaction History");
        System.out.println("6. 🔄 Simulate Market Update");
        System.out.println("7. 💾 Save Data");
        System.out.println("8. 📂 Load Data");
        System.out.println("9. 🚪 Exit");
        System.out.println("===================");
    }
    
    private static void viewMarketData() {
        marketData.displayMarketData();
    }
    
    private static void buyStock() {
        System.out.println("\n💰 === BUY STOCKS ===");
        marketData.displayMarketData();
        
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.nextLine().toUpperCase();
        
        Stock stock = marketData.getStock(symbol);
        if (stock == null) {
            System.out.println("❌ Invalid symbol!");
            return;
        }
        
        System.out.print("Enter quantity: ");
        try {
            int quantity = Integer.parseInt(scanner.nextLine());
            if (quantity <= 0) {
                System.out.println("❌ Quantity must be positive!");
                return;
            }
            
            trading.buyStock(symbol, quantity);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid quantity!");
        }
    }
    
    private static void sellStock() {
        System.out.println("\n💸 === SELL STOCKS ===");
        
        if (portfolio.getHoldings().isEmpty()) {
            System.out.println("No stocks to sell!");
            return;
        }
        
        portfolio.displayPortfolio(marketData.getAllStocks().stream()
            .collect(HashMap::new, (m, s) -> m.put(s.getSymbol(), s), HashMap::putAll));
        
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.nextLine().toUpperCase();
        
        if (portfolio.getShares(symbol) == 0) {
            System.out.println("❌ You don't own this stock!");
            return;
        }
        
        System.out.print("Enter quantity: ");
        try {
            int quantity = Integer.parseInt(scanner.nextLine());
            if (quantity <= 0) {
                System.out.println("❌ Quantity must be positive!");
                return;
            }
            
            trading.sellStock(symbol, quantity);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid quantity!");
        }
    }
    
    private static void viewPortfolio() {
        portfolio.updateCurrentValue(marketData.getAllStocks().stream()
            .collect(HashMap::new, (m, s) -> m.put(s.getSymbol(), s), HashMap::putAll));
        portfolio.displayPortfolio(marketData.getAllStocks().stream()
            .collect(HashMap::new, (m, s) -> m.put(s.getSymbol(), s), HashMap::putAll));
    }
    
    private static void viewTransactionHistory() {
        trading.displayTransactionHistory();
    }
    
    private static void simulateMarketUpdate() {
        System.out.println("\n🔄 Updating market prices...");
        marketData.updateMarketPrices();
        portfolio.updateCurrentValue(marketData.getAllStocks().stream()
            .collect(HashMap::new, (m, s) -> m.put(s.getSymbol(), s), HashMap::putAll));
        marketData.displayMarketData();
        System.out.println("✅ Market updated!");
    }
    
    private static void saveData() {
        persistence.savePortfolio(portfolio);
        persistence.saveTransactions(trading.getTransactionHistory());
    }
    
    private static void loadData() {
        Portfolio loaded = persistence.loadPortfolio();
        if (loaded != null) {
            portfolio = loaded;
            List<Transaction> transactions = persistence.loadTransactions();
            trading = new TradingService(marketData, portfolio);
            System.out.println("✅ Data loaded successfully!");
        } else {
            System.out.println("❌ No saved data found!");
        }
    }
    
    private static void exit() {
        System.out.print("\nSave before exiting? (y/n): ");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("y")) {
            saveData();
        }
        System.out.println("\nThank you for using Stock Trading Platform!");
        System.out.println("Goodbye! 👋");
        running = false;
    }
}