import javax.swing.*;
import java.awt.*;

import java.sql.*;

public class AddCustomer {

    JFrame frame;
    JTextField nameField, meterField, addressField;

    public AddCustomer() {

        frame = new JFrame("Add Customer");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JLabel meterLabel = new JLabel("Meter No:");
        JLabel addressLabel = new JLabel("Address:");

        nameField = new JTextField();
        meterField = new JTextField();
        addressField = new JTextField();

        JButton saveBtn = new JButton("Save");

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(meterLabel);
        frame.add(meterField);
        frame.add(addressLabel);
        frame.add(addressField);
        frame.add(new JLabel());
        frame.add(saveBtn);

        // ===== BUTTON ACTION =====
        saveBtn.addActionListener(e -> saveCustomer());

        frame.setVisible(true);
    }

    // ===== DATABASE INSERT =====
    private void saveCustomer() {
        String name = nameField.getText();
        String meter = meterField.getText();
        String address = addressField.getText();

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/electricity_db",
                "root",
                "root123"
            );

            String query = "INSERT INTO customers (name, meter_no, address) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, meter);
            ps.setString(3, address);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Customer Added Successfully!");

            // clear fields
            nameField.setText("");
            meterField.setText("");
            addressField.setText("");

            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }
}