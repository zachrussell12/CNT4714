import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class NoStock {

    public NoStock() {
        showNoStock();
    }

    public void showNoStock(){
        JFrame orderPopUp = new JFrame("E-Stores R' Us - Shopping Cart");

        orderPopUp.setSize(400,150);
        orderPopUp.setLocation(500,250);

        orderPopUp.setLayout(new BorderLayout());

        JPanel labelContainer = new JPanel();

        JLabel emptyLine2 = new JLabel("----------------------------------------------------------------------------------------");

        labelContainer.add(emptyLine2);

        JLabel noStockText = new JLabel("|      Sorry... that item is out of stock, please try another item.      |");

        noStockText.setHorizontalAlignment(SwingConstants.CENTER);

        labelContainer.add(noStockText);

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
