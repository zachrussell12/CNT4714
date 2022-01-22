import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

public class ShoppingCart {

    private final List<String> cart;

    public ShoppingCart(List<String> order) {
        this.cart = order;

        showCart();
    }

    public void showCart(){
        JFrame orderPopUp = new JFrame("E-Stores R' Us - Shopping Cart");

        orderPopUp.setSize(500,400);
        orderPopUp.setLocation(500,250);

        orderPopUp.setLayout(new BorderLayout());

        if(this.cart.size() == 0){
            JLabel noItems = new JLabel("No items are in your cart.");

            noItems.setHorizontalAlignment(SwingConstants.CENTER);

            orderPopUp.add(noItems);
        }
        else{

            JPanel cart = new JPanel();

            //StringBuilder list = new StringBuilder();

            JList list = new JList(this.cart.toArray());

            list.setBounds(100,0,400,400);

            list.setAlignmentY(Component.CENTER_ALIGNMENT);
            list.setAlignmentX(Component.CENTER_ALIGNMENT);

            cart.setAlignmentX(Component.CENTER_ALIGNMENT);
            cart.setAlignmentY(Component.CENTER_ALIGNMENT);

            cart.add(list);

            JButton close = new JButton("Okay");
            close.setPreferredSize(new Dimension(75,25));

            cart.add(close);

            close.setVerticalAlignment(SwingConstants.BOTTOM);

            /*for(int i = 0; i < this.cart.size(); i++){
                JLabel newLabel = new JLabel((i + 1) + ". " + this.cart.get(i));
                cart.add(newLabel);
            }*/

            close.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    orderPopUp.dispose();
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            orderPopUp.add(cart);
        }



        orderPopUp.setVisible(true);
    }
}
