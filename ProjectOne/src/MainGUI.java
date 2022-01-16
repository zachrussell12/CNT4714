import javax.swing.*;
import java.awt.*;

public class MainGUI {

    public static void initializeWindow(JFrame store){
        store.setSize(700,400);
        store.setLocation(500,500);

        store.setLayout(new BorderLayout());

        store.setVisible(true);
    }

    public static void createBottomBar(JPanel bottomBar, int counter){

        JButton button1 = new JButton("Process Item #" + counter);
        JButton button2 = new JButton("Confirm Item #" + counter);
        JButton button3 = new JButton("View Order");
        JButton button4 = new JButton("Finish Order");
        JButton button5 = new JButton("New Order");
        JButton button6 = new JButton("Exit");

        bottomBar.add(button1);
        bottomBar.add(button2);
        bottomBar.add(button3);
        bottomBar.add(button4);
        bottomBar.add(button5);
        bottomBar.add(button6);

    }

    public static void main(String[] args){

        JFrame store = new JFrame("E-Stores R' Us");
        int counter = 1;

        initializeWindow(store);

        JPanel centralScreen = new JPanel();
        centralScreen.setLayout(new BoxLayout(centralScreen, BoxLayout.Y_AXIS));

        createCenterScreen(centralScreen, counter);

        store.getContentPane().add(BorderLayout.CENTER, centralScreen);

        JPanel bottomBar = new JPanel();

        createBottomBar(bottomBar, counter);

        store.getContentPane().add(BorderLayout.SOUTH, bottomBar);

    }

    private static void createCenterScreen(JPanel centralScreen, int counter) {

        JLabel itemIDLabel = new JLabel("Enter item ID for Item #" + counter);
        JTextField itemIDField = new JTextField(10);

        JLabel quantityLabel = new JLabel("Enter quantity for Item #" + counter);
        JTextField quantityField = new JTextField(10);

        JLabel detailsLabel = new JLabel("Details for Item #" + counter);
        JTextField detailsField = new JTextField(10);

        JLabel subtotalLabel = new JLabel("Order subtotal for " + (counter-1) + "item(s)");
        JTextField subtotalField = new JTextField(10);

        centralScreen.add(itemIDLabel);
        centralScreen.add(itemIDField);
        itemIDLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        //itemIDField.setAlignmentX(Component.LEFT_ALIGNMENT);

        centralScreen.add(quantityLabel);
        centralScreen.add(quantityField);
        quantityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        quantityField.setAlignmentX(Component.LEFT_ALIGNMENT);

        centralScreen.add(detailsLabel);
        centralScreen.add(detailsField);
        detailsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsField.setAlignmentX(Component.LEFT_ALIGNMENT);

        centralScreen.add(subtotalLabel);
        centralScreen.add(subtotalField);
        subtotalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtotalField.setAlignmentX(Component.LEFT_ALIGNMENT);

    }
}
