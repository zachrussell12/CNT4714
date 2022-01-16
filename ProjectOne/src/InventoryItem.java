public class InventoryItem {

    public String itemId;
    public String itemName;
    public boolean itemInStock;
    public double itemPrice;


    public InventoryItem(String id, String name, boolean inStock, double price) {

        this.itemId = id;
        this.itemName = name;
        this.itemInStock = inStock;
        this.itemPrice = price;
    }

}
