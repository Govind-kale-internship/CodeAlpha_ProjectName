package task2;
import java.util.*;

public class MarketDataService {
    private Map<String, Stock> stocks;
    private Random random;
    
    public MarketDataService() {
        this.stocks = new HashMap<>();
        this.random = new Random();
        initializeStocks();
    }
    
    private void initializeStocks() {
        // Initialize with popular stocks
        addStock(new Stock("AAPL", "Apple Inc.", 175.50));
        addStock(new Stock("GOOGL", "Alphabet Inc.", 140.25));
        addStock(new Stock("MSFT", "Microsoft Corp.", 380.75));
        addStock(new Stock("AMZN", "Amazon.com Inc.", 145.30));
        addStock(new Stock("TSLA", "Tesla Inc.", 240.15));
        addStock(new Stock("META", "Meta Platforms", 315.60));
        addStock(new Stock("NVDA", "NVIDIA Corp.", 850.25));
        addStock(new Stock("JPM", "JPMorgan Chase", 155.40));
    }
    
    public void addStock(Stock stock) {
        stocks.put(stock.getSymbol(), stock);
    }
    
    // Simulate market movement
    public void updateMarketPrices() {
        for (Stock stock : stocks.values()) {
            // Random price change between -5% and +5%
            double changePercent = (random.nextDouble() * 10) - 5;
            double newPrice = stock.getCurrentPrice() * (1 + changePercent / 100);
            // Ensure price doesn't go below $1
            newPrice = Math.max(1.0, newPrice);
            stock.updatePrice(newPrice);
        }
    }
    
    public Stock getStock(String symbol) {
        return stocks.get(symbol);
    }
    
    public List<Stock> getAllStocks() {
        return new ArrayList<>(stocks.values());
    }
    
    public void displayMarketData() {
        System.out.println("\n📈 === MARKET DATA ===");
        System.out.println("Symbol | Company Name          | Price    | Change");
        System.out.println("──────────────────────────────────────────────────");
        for (Stock stock : stocks.values()) {
            System.out.println(stock);
        }
        System.out.println("══════════════════════════════════════════════════");
    }
}
