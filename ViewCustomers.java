import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;

public class ViewCustomers {

    JFrame frame;
    JTable table;
    DefaultTableModel model;

    public ViewCustomers() {

        frame = new JFrame("Customer Details");
        frame.setSize(600, 400);

        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Meter No");
        model.addColumn("Address");

        fetchData();

        JScrollPane sp = new JScrollPane(table);
        frame.add(sp);

        frame.setVisible(true);
    }

    private void fetchData() {
        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/electricity_db",
                "root",
                "root123"
            );

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM customers");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("meter_no"),
                    rs.getString("address")
                });
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}