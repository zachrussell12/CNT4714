import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AcceptedOrDeclined {

    public AcceptedOrDeclined(boolean choice, String id) {
        displayChoicePopUp(choice, id);
    }

    private static void displayChoicePopUp(boolean choice, String id){

        JFrame orderPopUp = new JFrame("E-Stores R' Us - Item Status");

        orderPopUp.setSize(400,150);
        orderPopUp.setLocation(660,383);

        orderPopUp.setLayout(new BorderLayout());

        JPanel labelContainer = new JPanel();

        JLabel emptyLine2 = new JLabel("----------------------------------------------------------------------------------------");

        labelContainer.add(emptyLine2);

        if(choice){
            System.out.println("Item found and added to cart");

            JLabel noStockText = new JLabel("|         Item #" + id +" accepted. Added to your cart.         |");

            noStockText.setHorizontalAlignment(SwingConstants.CENTER);

            labelContainer.add(noStockText);

        }
        else{
            System.out.println("Item not found. Try again.");

            JLabel noStockText = new JLabel("|      Item #" + id +" was not found, please try another item.      |");

            noStockText.setHorizontalAlignment(SwingConstants.CENTER);

            labelContainer.add(noStockText);
        }

        JLabel emptyLine = new JLabel("----------------------------------------------------------------------------------------");

        labelContainer.add(emptyLine);

        labelContainer.setAlignmentY(Component.CENTER_ALIGNMENT);

        JButton close = new JButton("Okay");
        close.setPreferredSize(new Dimension(75,25));

        labelContainer.add(close);

        orderPopUp.add(labelContainer);

        close.setHorizontalAlignment(SwingConstants.CENTER);

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

        orderPopUp.setVisible(true);

    }


}
