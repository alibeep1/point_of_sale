import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * ProductInventory class that represents an inventory of products.
 * The inventory uses a CustomMap to store Product objects with their ids as keys.
 */
public class ProductInventory {

    private final CustomMap inventory;

    /**
     * ProductInventory class that represents an inventory of products.
     * The inventory uses a CustomMap to store Product objects with their ids as keys.
     */
    public ProductInventory() {
        this.inventory = new CustomMap();
    }

    public CustomMap getInventory() {
        return inventory;
    }

    /**
     * Method to check the availability of a product in the inventory.
     * If the product is available and its quantity is greater than or equal to the requested quantity, return the product.
     * If the product is available but its quantity is less than the requested quantity, throw an InsufficientQuantityException.
     * If the product is not available, return null.
     *
     * @param id       The id of the product to check.
     * @param quantity The requested quantity.
     * @return The product if it is available and its quantity is sufficient.
     * @throws InsufficientQuantityException If the product's quantity is insufficient.
     */
    public Product getProductAvailability(String id, BigDecimal quantity) throws InsufficientQuantityException {
        Product product = this.inventory.get(id);
        if (product != null && product.getQuantity().compareTo(quantity) >= 0) {
            return product;
        } else if (product != null && !(product.getQuantity().compareTo(quantity) >= 0)) {
//            assert product != null;
            throw new
                    InsufficientQuantityException("The quantity you specified (" + quantity + ")" +
                    " is more than the available quantity (" + product.getQuantity() + ")");
        } else {
            return null;
        }
    }


    // Method to add a new product to the inventory
    public void addProduct(Product product) {
//        Product product = new Product(id, name, quantity, price);
        this.inventory.put(product.getId(), product);
    }

    /**
     * Method to add a new product to the inventory.
     *
     * @param product The product to add.
     */
    public void updateProduct(String id, BigDecimal price, BigDecimal quantity, String name) {
        Product product = inventory.get(id);
        if (product != null) {
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setName(name);
        } else {
            System.out.println("Product not found in inventory.");
        }
    }

    /**
     * Method to decrement the stock of a product in the inventory.
     * If the product exists, decrement its quantity.
     * If the product does not exist, print a message indicating that the product was not found.
     *
     * @param id       The id of the product.
     * @param quantity The quantity to decrement.
     */
    public void decrementProductStock(String id, BigDecimal quantity) {
        Product product = inventory.get(id);
        if (product != null) {
//            product.setPrice(price);
            product.setQuantity(product.getQuantity().subtract(quantity));
//            product.setName(name);
        } else {
            System.out.println("Product not found in inventory.");
        }
    }

    /**
     * Method to remove a product from the inventory.
     * If the product exists, remove it and print a success message.
     * If the product does not exist, print a message indicating that the product was not found.
     *
     * @param id The id of the product to remove.
     */
    public void removeProduct(String id) {
        Product removedProduct = inventory.remove(id);
        if (removedProduct == null) {
            System.out.println("Product not found in inventory.");
        } else {
            System.out.println("Product Removed Successfully!");
        }
    }

    /**
     * Method to print all the products in the inventory.
     * Prints the id, name, quantity, price, and tax rate of each product.
     * Also prints the total number of products.
     */
    public void printProducts() {
        System.out.println("Printing Inventory Products\n");
        System.out.format("%-10s%-20s%-10s%-10s%-10s", "ID", "Name", "Quantity", "Price", "Tax Rate");
        System.out.println("\n------------------------------------------------------------------------------");
        for (Product product : this.getInventory().getValues()) {
//        for (Product product : this.getInventory().values()) {
            System.out.format("%-10s%-20s%-10s%-10s%-10s", product.getId(), product.getName(), product.getQuantity(), product.getPrice(), product.getTaxRate());
            System.out.println();
        }
        System.out.println("Showing " + Product.getProductCount() + " Products");


    }
}
