import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductInventory productInventory = new ProductInventory();
        ShoppingCart cart = new ShoppingCart();

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("1. Add products");
            System.out.println("2. Shop");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline left-over
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                scanner.nextLine(); // consume the invalid input

                continue;
            }


            switch (choice) {
                case 1:
                    String cont = "y";
                    while (cont.equalsIgnoreCase("y")) {
                        try {

                            System.out.print("Enter product ID: ");
                            String id = scanner.nextLine();

                            System.out.print("Enter product name: ");
                            String name = scanner.nextLine();

                            System.out.print("Enter product quantity: ");
                            BigDecimal quantity = new BigDecimal(scanner.nextLine());

                            System.out.print("Enter product price: ");
                            BigDecimal price = new BigDecimal(scanner.nextLine());

                            System.out.print("Enter product tax rate: ");
                            BigDecimal taxRate = new BigDecimal(scanner.nextLine());
//                        System.out.print("Entered product tax rate: " + taxRate);


//                            Product product = productFactory.createProduct(id, name, quantity,price, taxRate);
                            Product product = new Product(id, name, quantity, price, taxRate);
                            productInventory.addProduct(product);
                            System.out.println("Product added successfully.");

                        } catch (IllegalArgumentException | InputMismatchException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                        System.out.print("Add another product? (y/n): ");
                        cont = scanner.nextLine();
                    }
                    break;
                case 2:
                    cont = "y";
                    try {
                        if (Product.getProductCount() < 1) {
                            throw new
                                    InsufficientQuantityException("No products exist. Please, add products first.");
                        }
                        while (cont.equalsIgnoreCase("y")) {
//                        System.out.println("Inventory:");
                            try {

                                productInventory.printProducts();
                                System.out.println("Choose products and quantities, along with your payment method, then confirm and make payment:");

                                System.out.print("Enter product ID: ");
                                String id = scanner.nextLine();

                                System.out.print("Enter quantity: ");
                                BigDecimal quantity = new BigDecimal(scanner.nextLine());


                                Product product = productInventory.getProductAvailability(id, quantity);
                                cart.addProduct(product, quantity);

                                System.out.println("Product added to cart successfully.");

                            } catch (IllegalArgumentException | InputMismatchException e) {
                                System.out.println("Error adding product to cart: " + e);
                                continue;
//                            throw new RuntimeException(e);
                            } catch (InsufficientQuantityException e) {
                                System.out.println("Error: " + e);
//                            System.out.println(": " + e);

//                            continue;
                            }


                            System.out.print("Add another product to cart? (y/n): ");
                            cont = scanner.nextLine();
                        }
//                    System.out.print("Enter payment method: ");
                        System.out.print("Enter your payment method (CASH, CREDIT_CARD, MOBILE_PAYMENT): ");
                        PaymentMethod paymentMethod = PaymentMethod.CASH;
                        try {
                            String paymentMethodInput = scanner.nextLine();

                            paymentMethod = PaymentMethod.valueOf(paymentMethodInput.toUpperCase());

                        } catch (IllegalArgumentException | InputMismatchException e) {
                            System.out.println("Payment Error. Please make sure you use one of the allowed payment methods: " + e);
//                        System.out.println("Invalid payment method: " + paymentMethodInput + "... Defaulting to cash");
                            continue;
                        }
                        try {
                            Cashier cashier = new Cashier(cart, paymentMethod);
                            cashier.processPaymentAndGenerateReceipt();
//                    PaymentMethod paymentMethod = PaymentMethod.valueOf(payment)
                            for (Product product : cart.getCartItems().getKeys()) {
                                BigDecimal quantityInCart = cart.getCartItems().get(product);
                                productInventory.decrementProductStock(product.getId(), quantityInCart);
//                        BigDecimal quantity = cart.getCartItems().get(product);
//                        System.out.printf("%-20s%-15s%n", product.getName(), quantity);
                            }
//                    Cashier cashier = new Cashier(cart, )
                            productInventory.printProducts();

                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e);
                            continue;
                        }
                    } catch (InsufficientQuantityException e) {
                        System.out.println("Error: " + e);
                        break;
                    }
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
