import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void initializeWindow(JFrame store){
        store.setSize(700,400);
        store.setLocation(500,250);

        //store.setLayout(new BorderLayout());

        store.setVisible(true);
    }

    private static void createBottomBar(JPanel bottomBar, java.util.List<String> order){


        JButton button1 = new JButton("Process Item #" + (order.size()+1));
        JButton button2 = new JButton("Confirm Item #" + (order.size()+1));
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

        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);

    }

    private static void createCenterScreen(JPanel centralScreen, int counter, JPanel bottomBar, InventoryItem[] inventoryList, java.util.List<String> order) {

        final double[] orderTotal = {0.0};

        JLabel itemIDLabel = new JLabel("Enter item ID for Item #" + (order.size()+1));
        JTextField itemIDField = new JTextField(10);
        itemIDField.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.DARK_GRAY));


        JLabel quantityLabel = new JLabel("Enter quantity for Item #" + (order.size()+1));
        JTextField quantityField = new JTextField(10);
        quantityField.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.DARK_GRAY));

        JLabel detailsLabel = new JLabel("Details for Item #" + (order.size()+1));
        JTextField detailsField = new JTextField(65);
        detailsField.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.DARK_GRAY));
        detailsField.setDisabledTextColor(Color.black);


        JLabel subtotalLabel = new JLabel("Order subtotal for " + (order.size()) + " item(s)");
        JTextField subtotalField = new JTextField(10);
        subtotalField.setDisabledTextColor(Color.black);
        subtotalField.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.DARK_GRAY));

        centralScreen.add(itemIDLabel);
        centralScreen.add(itemIDField);

        itemIDField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                disableEnable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                disableEnable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                disableEnable();
            }

            public void disableEnable(){
                if(itemIDField.getText().equals("")){
                    bottomBar.getComponent(1).setEnabled(false);
                    bottomBar.getComponent(0).setEnabled(true);
                }
            }
        });

        centralScreen.add(quantityLabel);
        centralScreen.add(quantityField);

        quantityField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                disableEnable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                disableEnable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                disableEnable();
            }

            public void disableEnable(){
                if(itemIDField.getText().equals("")){
                    bottomBar.getComponent(1).setEnabled(false);
                    bottomBar.getComponent(0).setEnabled(true);
                }
                else{
                    if(bottomBar.getComponent(0).isEnabled() == false){bottomBar.getComponent(1).setEnabled(true);}
                }

                if(!(quantityField.getText().equals(""))){
                    quantityField.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.DARK_GRAY));
                }
            }
        });

        centralScreen.add(detailsLabel);
        centralScreen.add(detailsField);

        detailsField.setEnabled(false);

        centralScreen.add(subtotalLabel);
        centralScreen.add(subtotalField);

        subtotalField.setEnabled(false);


        bottomBar.getComponent(0).addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println(itemIDField.getText());
                final DecimalFormat df = new DecimalFormat("#.00");
                int check = 0;
                for(int i = 0; i < inventoryList.length; i++){
                    if(inventoryList[i].itemId.equals(itemIDField.getText())){
                        //System.out.println("Printing out details:");
                        //System.out.println(inventoryList[i].itemId + " " + inventoryList[i].itemName + " " + inventoryList[i].itemInStock + " $" + df.format(inventoryList[i].itemPrice));
                        if(inventoryList[i].itemInStock == true){
                            String discount = getDiscount(quantityField);
                            detailsField.setText(inventoryList[i].itemId + " " + inventoryList[i].itemName + " " + inventoryList[i].itemInStock + " " + discount + " $" + df.format(inventoryList[i].itemPrice));
                            bottomBar.getComponent(0).setEnabled(false);
                            if(!(quantityField.getText().equals(""))) {
                                bottomBar.getComponent(1).setEnabled(true);
                            }
                            else{
                                quantityField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red));
                            }
                            break;
                        }
                        else{
                            System.out.println(inventoryList[i].itemInStock);
                            NoStock OOS = new NoStock();
                            itemIDField.setText("");
                            quantityField.setText("");
                            if(!(detailsField.getText().equals(""))){
                                detailsField.setText("");
                            }
                            break;
                        }

                    }
                    check++;
                }

                if(check == inventoryList.length){
                    AcceptedOrDeclined display = new AcceptedOrDeclined(false, itemIDField.getText());
                    itemIDField.setText("");
                    quantityField.setText("");
                }
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

        bottomBar.getComponent(1).addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e){

                final DecimalFormat df = new DecimalFormat("#.00");

                for(int i = 0; i < inventoryList.length; i++){
                    if(inventoryList[i].itemId.equals(itemIDField.getText()) && inventoryList[i].itemInStock == true){
                        String discount = getDiscount(quantityField);
                        String total = getItemTotal(quantityField.getText(), inventoryList[i].itemPrice, discount, df);
                        order.add(counter + ". " + inventoryList[i].itemId + " " + inventoryList[i].itemName + " " + inventoryList[i].itemInStock + " $" + df.format(inventoryList[i].itemPrice) + " " + quantityField.getText() + " " + discount + " $" + total);
                        orderTotal[0] += Double.parseDouble(total);
                        bottomBar.getComponent(1).setEnabled(false);
                        itemIDField.setText("");
                        quantityField.setText("");
                        subtotalField.setText("$" + String.valueOf(df.format(orderTotal[0])));
                        AcceptedOrDeclined display = new AcceptedOrDeclined(true, inventoryList[i].itemId);
                        enableView(bottomBar);
                        increaseOrderSize(itemIDLabel, quantityLabel, detailsLabel, subtotalLabel, order.size());
                        break;
                    }
                }
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

        bottomBar.getComponent(2).addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ShoppingCart window = new ShoppingCart(order);
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

        bottomBar.getComponent(3).addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FinalInvoice orderFinished = new FinalInvoice(order, orderTotal);
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

        bottomBar.getComponent(4).addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                orderTotal[0] = 0.0;
                order.clear();
                itemIDField.setText("");
                quantityField.setText("");
                detailsField.setText("");
                subtotalField.setText("");
                itemIDLabel.setText("Enter item ID for Item #" + (order.size()+1));
                quantityLabel.setText("Enter quantity for Item #" + (order.size()+1));
                detailsLabel.setText("Details for Item #" + (order.size()+1));
                subtotalLabel.setText("Order subtotal for " + (order.size()) + " item(s)");
                bottomBar.getComponent(1).setEnabled(false);
                bottomBar.getComponent(2).setEnabled(false);
                bottomBar.getComponent(3).setEnabled(false);
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
    }

    private static void increaseOrderSize(JLabel fieldOne, JLabel fieldTwo, JLabel fieldThree, JLabel fieldFour, int size){
        fieldOne.setText("Enter item ID for Item #" + (size+1) + ":");
        fieldTwo.setText("Enter quantity for Item #" + (size+1) + ":");
        fieldThree.setText("Details for Item #" + (size+1) + ":");
        fieldFour.setText("Order subtotal for " + size + " item(s):");
    }

    private static void enableView(JPanel bottomBar){
        if(!(bottomBar.getComponent(2).isEnabled())){
            //System.out.println("Already on");
            bottomBar.getComponent(2).setEnabled(true);
        }
        if(!(bottomBar.getComponent(3).isEnabled())){
            bottomBar.getComponent(3).setEnabled(true);
        }
        return;
        //System.out.println("Turning button on");
    }

    private static String getItemTotal(String quantity, double price, String discount, DecimalFormat df){

        discount = discount.replace("%", "");
        discount = "0." + discount;

        double disc = Double.parseDouble(discount);

        String s = String.valueOf(df.format(((Integer.parseInt(quantity) * price))-((Integer.parseInt(quantity) * price)*disc)));
        return s;
    }

    private static InventoryItem[] openInventory(){

        InventoryItem[] inv = new InventoryItem[40];
        File invList = new File("../ProjectOne/src/inventory.txt");
        Scanner scanList = null;
        try {
            scanList = new Scanner(invList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < inv.length; i++){
            String itemInfo = scanList.nextLine();
            //System.out.println(itemInfo);

            String[] parts = itemInfo.split(String.valueOf(','));

            if(parts[2].contains("true")) {
                inv[i] = new InventoryItem(parts[0], parts[1], true, Double.parseDouble(parts[3]));
            }
            else{
                inv[i] = new InventoryItem(parts[0], parts[1], false, Double.parseDouble(parts[3]));
            }

        }


        return inv;
    }

    private static String getDiscount(JTextField quantityField){

        if(quantityField.getText().equals("")){
            return "0%";
        }

        if(Integer.parseInt(quantityField.getText()) <= 4) {
            return "0%";
        }
        else if (Integer.parseInt(quantityField.getText()) > 4 && Integer.parseInt(quantityField.getText()) <= 9) {
            return "10%";
        }
        else if(Integer.parseInt(quantityField.getText()) > 10 && Integer.parseInt(quantityField.getText()) <= 14) {
            return "15%";
        }
        else{
            return "20%";
        }

    }

    public static void main(String[] args){

        JFrame store = new JFrame("E-Stores R' Us");
        List<String> order = new ArrayList<>();
        int counter = 1;

        initializeWindow(store);

        InventoryItem[] inventory = openInventory();
        System.out.println(inventory[1].itemName);

        JPanel bottomBar = new JPanel();

        createBottomBar(bottomBar, order);

        store.getContentPane().add(BorderLayout.SOUTH, bottomBar);

        JPanel centralScreen = new JPanel();

        centralScreen.setLayout(new BoxLayout(centralScreen, BoxLayout.Y_AXIS));

        createCenterScreen(centralScreen, counter, bottomBar, inventory, order);

        store.getContentPane().add(BorderLayout.NORTH, centralScreen);

        bottomBar.getComponent(5).addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                store.dispose();
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
    }
}
