import javax.swing.*;
import java.awt.*;

public class ElectricityGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Electricity Bill System");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== SMOOTH BACKGROUND PANEL (NO IMAGE) =====
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;

                int width = getWidth();
                int height = getHeight();

                // 🎨 Smooth Gradient Background
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(102, 126, 234),   // light blue
                        width, height, new Color(118, 75, 162) // purple
                );

                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, width, height);
            }
        };

        backgroundPanel.setLayout(new BorderLayout());
        frame.setContentPane(backgroundPanel);

        // ===== MAIN PANEL =====
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        // ===== BOX =====
        JPanel box = new JPanel();
        box.setLayout(new GridLayout(4, 1, 15, 15));
        box.setPreferredSize(new Dimension(420, 300));
        box.setBackground(new Color(255, 255, 255, 200));

        JButton btn1 = new JButton("Add Customer");
        JButton btn2 = new JButton("View Customers");
        JButton btn3 = new JButton("Generate Bill");
        JButton btn4 = new JButton("View Bills");

        // ===== BUTTON CONNECTIONS =====
        btn1.addActionListener(e -> new AddCustomer());
        btn2.addActionListener(e -> new ViewCustomers());
        btn3.addActionListener(e -> new GenerateBill());
        btn4.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Coming Soon"));

        box.add(btn1);
        box.add(btn2);
        box.add(btn3);
        box.add(btn4);

        // ===== TITLE =====
        JLabel title = new JLabel("Electricity Bill System", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 36));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // ===== COMBINE =====
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(title, BorderLayout.NORTH);
        bottomPanel.add(box, BorderLayout.CENTER);

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setOpaque(false);
        wrapper.add(bottomPanel);

        mainPanel.add(wrapper, BorderLayout.SOUTH);

        frame.add(mainPanel);

        frame.setVisible(true);
    }
}