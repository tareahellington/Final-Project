package reservation;

public class Food {
    private String foodName;
    private Double foodPrice;
    private int foodQuantity;

    public Food(String foodName, Double foodPrice, int foodQuantity) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodQuantity = foodQuantity;
    }

    public String getFoodName() {
        return foodName;
    }
    
    public Double getFoodPrice() {
        return foodPrice;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    @Override
    public String toString() {
        return foodName + " - $" + foodPrice + " - Quantity: " + foodQuantity;
    }
}
