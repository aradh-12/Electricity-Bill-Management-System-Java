import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class GenerateBill {

    JFrame frame;
    JTextField meterField, unitsField;

    public GenerateBill() {

        frame = new JFrame("Generate Bill");
        frame.setSize(400, 250);
        frame.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel meterLabel = new JLabel("Meter No:");
        JLabel unitsLabel = new JLabel("Units:");

        meterField = new JTextField();
        unitsField = new JTextField();

        JButton generateBtn = new JButton("Generate");

        frame.add(meterLabel);
        frame.add(meterField);
        frame.add(unitsLabel);
        frame.add(unitsField);
        frame.add(new JLabel());
        frame.add(generateBtn);

        // ✅ BUTTON ACTION
        generateBtn.addActionListener(e -> generateBill());

        frame.setVisible(true);
    }

    private void generateBill() {

        try {
            String meter = meterField.getText();
            int units = Integer.parseInt(unitsField.getText());

            double amount = 0;

            if (units <= 100) {
                amount = units * 5;
            } else if (units <= 200) {
                amount = (100 * 5) + ((units - 100) * 7);
            } else {
                amount = (100 * 5) + (100 * 7) + ((units - 200) * 10);
            }

            // ✅ CONNECT DB
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/electricity_db",
                    "root",
                    "root123"
            );

            // ✅ CHECK CUSTOMER EXISTS
            PreparedStatement check = con.prepareStatement(
                    "SELECT * FROM customers WHERE meter_no=?"
            );
            check.setString(1, meter);

            ResultSet rs = check.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(frame, "❌ Meter not found!");
                return;
            }

            // ✅ INSERT BILL
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO bills (meter_no, units, amount) VALUES (?, ?, ?)"
            );

            ps.setString(1, meter);
            ps.setInt(2, units);
            ps.setDouble(3, amount);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(frame, "✅ Bill Generated: ₹" + amount);

            con.close();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "❌ Enter valid number!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "❌ ERROR: " + ex.getMessage());
        }
    }
}