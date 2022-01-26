import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class FinalInvoice {

    private static List<String> finalOrder;
    private static double[] finalSubtotal;

    public FinalInvoice(List<String> order, double[] subtotal) {
        this.finalOrder = order;
        this.finalSubtotal = subtotal;

        finalInvoice();

    }

    private void finalInvoice(){

        JFrame finalInvoiceFrame = new JFrame("E-Stores R' Us - Final Invoice");

        finalInvoiceFrame.setSize(400,500);
        finalInvoiceFrame.setLocation(660,250);

        finalInvoiceFrame.setLayout(new BorderLayout());

        JPanel labelContainer = new JPanel();

        BoxLayout layout = new BoxLayout(labelContainer, BoxLayout.Y_AXIS);

        labelContainer.setLayout(layout);


        Date today = new Date();

        JLabel date = new JLabel("Date: " + today.toString());

        labelContainer.add(date);



        JLabel lineItems = new JLabel("Number of line items: " + this.finalOrder.size());

        lineItems.setHorizontalAlignment(SwingConstants.CENTER);

        labelContainer.add(lineItems);

        JLabel columnDesc = new JLabel("Item # / ID / Title / Price / Qty / Disc % / Subtotal:");

        labelContainer.add(columnDesc);

        JList list = new JList(this.finalOrder.toArray());

        list.setBounds(100,0,400,400);

        list.setAlignmentY(Component.LEFT_ALIGNMENT);
        list.setAlignmentX(Component.LEFT_ALIGNMENT);

        labelContainer.add(list);

        DecimalFormat df = new DecimalFormat("#.00");

        JLabel total = new JLabel("Order subtotal: $" + df.format(this.finalSubtotal[0]));

        labelContainer.add(total);

        JLabel tax = new JLabel("Tax rate: 6%");

        labelContainer.add(tax);

        double taxAmt = (this.finalSubtotal[0]*0.06);

        JLabel taxAmount = new JLabel("Tax amount: $" + df.format(taxAmt));

        labelContainer.add(taxAmount);

        double taxAndOrder = (this.finalSubtotal[0] + taxAmt);

        JLabel taxAndOrderFinal = new JLabel("Order total: $" + df.format(taxAndOrder));

        labelContainer.add(taxAndOrderFinal);

        JLabel thanks = new JLabel("Thanks for shopping at E-Stores R' Us!");

        labelContainer.add(thanks);

        labelContainer.setAlignmentY(Component.CENTER_ALIGNMENT);

        JButton close = new JButton("Okay");
        close.setPreferredSize(new Dimension(75,25));

        labelContainer.add(close);

        finalInvoiceFrame.getContentPane().add(BorderLayout.NORTH, labelContainer);

        close.setHorizontalAlignment(SwingConstants.CENTER);

        close.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                finalInvoiceFrame.dispose();
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

        finalInvoiceFrame.setVisible(true);
    }
}
