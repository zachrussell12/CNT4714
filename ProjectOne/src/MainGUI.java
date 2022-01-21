import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class MainGUI {

    public static void initializeWindow(JFrame store){
        store.setSize(700,400);
        store.setLocation(500,500);

        store.setLayout(new BorderLayout());

        store.setVisible(true);
    }

    private static void createBottomBar(JPanel bottomBar, List<String> order){


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

    private static void createCenterScreen(JPanel centralScreen, int counter, JPanel bottomBar, InventoryItem[] inventoryList, List<String> order) {

        JLabel itemIDLabel = new JLabel("Enter item ID for Item #" + counter);
        JTextField itemIDField = new JTextField(10);

        JLabel quantityLabel = new JLabel("Enter quantity for Item #" + counter);
        JTextField quantityField = new JTextField(10);
        quantityField.setText("1");

        JLabel detailsLabel = new JLabel("Details for Item #" + counter);
        JTextField detailsField = new JTextField(65);

        JLabel subtotalLabel = new JLabel("Order subtotal for " + (counter-1) + " item(s)");
        JTextField subtotalField = new JTextField(10);

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
                    detailsField.setText("");
                }
                else{
                    if(!(quantityField.getText().equals(""))) {
                        bottomBar.getComponent(1).setEnabled(true);
                    }
                    else{
                        bottomBar.getComponent(1).setEnabled(false);
                        bottomBar.getComponent(0).setEnabled(true);
                    }
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
                    quantityField.setText("1");
                }
                else{
                    if(!(itemIDField.getText().equals(""))) {
                        bottomBar.getComponent(1).setEnabled(true);
                    }
                    else{
                        bottomBar.getComponent(1).setEnabled(false);
                        bottomBar.getComponent(0).setEnabled(true);
                    }
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
                int counter = 0;
                for(int i = 0; i < inventoryList.length; i++){
                    if(inventoryList[i].itemId.equals(itemIDField.getText())){
                        //System.out.println("Printing out details:");
                        //System.out.println(inventoryList[i].itemId + " " + inventoryList[i].itemName + " " + inventoryList[i].itemInStock + " $" + df.format(inventoryList[i].itemPrice));
                        String discount = getDiscount(quantityField);
                        detailsField.setText(inventoryList[i].itemId + " " + inventoryList[i].itemName + " " + inventoryList[i].itemInStock + " " + discount + " $" + df.format(inventoryList[i].itemPrice));
                        bottomBar.getComponent(0).setEnabled(false);
                        break;
                    }
                    else{
                        counter++;
                    }
                }

                if(counter == inventoryList.length){
                    System.out.println("item was not found!");
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
            public void mouseClicked(MouseEvent e) {

                final DecimalFormat df = new DecimalFormat("#.00");

                for(int i = 0; i < inventoryList.length; i++){
                    if(inventoryList[i].itemId.equals(itemIDField.getText()) && inventoryList[i].itemInStock == true){
                        String discount = getDiscount(quantityField);
                        order.add(inventoryList[i].itemId + " " + inventoryList[i].itemName + " " + inventoryList[i].itemInStock + " " + discount + " $" + df.format(inventoryList[i].itemPrice));
                        itemIDField.setText("");
                        quantityField.setText("");
                        break;
                    }
                    else{
                        //System.out.println("Sorry that item is out of stock.");
                        NoStock OOS = new NoStock();
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
        int counter = 1;
        List<String> order = new ArrayList<>();

        initializeWindow(store);

        InventoryItem[] inventory = openInventory();
        System.out.println(inventory[1].itemName);

        JPanel bottomBar = new JPanel();

        createBottomBar(bottomBar, order);

        store.getContentPane().add(BorderLayout.SOUTH, bottomBar);

        JPanel centralScreen = new JPanel();
        centralScreen.setLayout(new FlowLayout(FlowLayout.LEFT));

        createCenterScreen(centralScreen, counter, bottomBar, inventory, order);

        store.getContentPane().add(BorderLayout.CENTER, centralScreen);
    }
}
