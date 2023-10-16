import java.math.BigDecimal;

/**
 * ShoppingCart class that represents a shopping cart with cart items.
 * The cart items are stored in a CustomMapCart.
 */
public class ShoppingCart {

    private CustomMapCart cartItems;

    // Constructor
    public ShoppingCart() {
        this.cartItems = new CustomMapCart();
    }

    public CustomMapCart getCartItems() {
        return cartItems;
    }

    /**
     * Method to add a product to the shopping cart.
     * If the product is already in the cart, update its quantity.
     * If an error occurs, print an error message.
     *
     * @param product  The product to add.
     * @param quantity The quantity of the product.
     */
    public void addProduct(Product product, BigDecimal quantity) {
        try {
            BigDecimal existingQuantity = this.cartItems.get(product);
            if (existingQuantity != null) {
                quantity = quantity.add(existingQuantity);
            }
            this.cartItems.put(product, quantity);
        } catch (Exception e) {
            System.out.println("Error adding product to cart: " + e.getMessage());
        }
    }

    /**
     * Method to remove a product from the shopping cart.
     * If an error occurs, print an error message.
     *
     * @param product The product to remove.
     */
    public void removeProduct(Product product) {
        try {
            this.cartItems.remove(product);
        } catch (Exception e) {
            System.out.println("Error removing product from cart: " + e.getMessage());
        }
    }

    /**
     * Method to calculate the total cost of items in the shopping cart.
     * If an error occurs, print an error message.
     *
     * @return The total cost of items in the shopping cart.
     */
    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        try {
            for (Product product : this.cartItems.getKeys()) {
                BigDecimal price = product.getPrice();
                BigDecimal quantity = this.cartItems.get(product);
                total = total.add(price.multiply(quantity));
            }
        } catch (Exception e) {
            System.out.println("Error calculating total: " + e.getMessage());
        }
        return total;
    }
}