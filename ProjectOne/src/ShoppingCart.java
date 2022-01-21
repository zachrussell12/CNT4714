import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ShoppingCart {

    private final List<String> cart;

    public ShoppingCart(List<String> order) {
        this.cart = order;

        showCart();
    }

    public void showCart(){
        JFrame orderPopUp = new JFrame("E-Stores R' Us - Shopping Cart");

        orderPopUp.setSize(400,400);
        orderPopUp.setLocation(500,500);

        orderPopUp.setLayout(new BorderLayout());

        if(this.cart.size() == 0){
            JLabel noItems = new JLabel("No items are in your cart.");

            noItems.setHorizontalAlignment(SwingConstants.CENTER);

            orderPopUp.add(noItems);
        }
        else{
            StringBuilder list = new StringBuilder();

            for(int i = 0; i < this.cart.size(); i++){
                list.append(i + 1).append(". ").append(this.cart.get(i)).append('\n');
            }

            JLabel orderList = new JLabel(list.toString());

            orderPopUp.add(orderList);
        }

        orderPopUp.setVisible(true);
    }
}
