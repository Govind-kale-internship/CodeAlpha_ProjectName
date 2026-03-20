package task2;

 
public class User {
    private String userId;
    private String name;
    private double cashBalance;
    
    public User(String userId, String name, double initialBalance) {
        this.userId = userId;
        this.name = name;
        this.cashBalance = initialBalance;
    }
    
    // Getters and Setters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public double getCashBalance() { return cashBalance; }
    
    public void setCashBalance(double cashBalance) { 
        this.cashBalance = cashBalance; 
    }
    
    public boolean deductCash(double amount) {
        if (cashBalance >= amount) {
            cashBalance -= amount;
            return true;
        }
        return false;
    }
    
    public void addCash(double amount) {
        cashBalance += amount;
    }
    
    @Override
    public String toString() {
        return String.format("User: %s | Balance: $%.2f", name, cashBalance);
    }
}