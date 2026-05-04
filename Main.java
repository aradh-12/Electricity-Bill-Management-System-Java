import java.sql.*;
import java.util.Scanner;

public class Main {

    // 💡 Bill calculation
    public static double calculateBill(int units) {
        if (units <= 100) {
            return units * 1.5;
        } else if (units <= 300) {
            return 100 * 1.5 + (units - 100) * 2.5;
        } else {
            return 100 * 1.5 + 200 * 2.5 + (units - 300) * 4;
        }
    }

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/electricity_db",
                "root",
                "root123"
            );

            Statement stmt = con.createStatement();

            System.out.println("\n⚡ ELECTRICITY BILL MANAGEMENT SYSTEM ⚡");

            while (true) {
                System.out.println("\n====================================");
                System.out.println("1. Add Customer");
                System.out.println("2. View Customers");
                System.out.println("3. Generate Bill");
                System.out.println("4. View Bills");
                System.out.println("5. Exit");
                System.out.println("====================================");
                System.out.print("Enter choice: ");

                if (!sc.hasNextInt()) {
                    System.out.println("❌ Please enter a valid number!");
                    sc.next();
                    continue;
                }

                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {
                    System.out.println("\n--- Add Customer ---");

                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Address: ");
                    String address = sc.nextLine();

                    System.out.print("Meter No: ");
                    String meter = sc.nextLine();

                    String query = "INSERT INTO customers (name, address, meter_no) VALUES ('"
                            + name + "', '" + address + "', '" + meter + "')";

                    stmt.executeUpdate(query);
                    System.out.println("✅ Customer added successfully!");

                } else if (choice == 2) {
                    System.out.println("\n--- Customer List ---");

                    ResultSet rs = stmt.executeQuery("SELECT * FROM customers");

                    while (rs.next()) {
                        System.out.printf(
                            "ID: %d | Name: %s | Address: %s | Meter: %s\n",
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getString("meter_no")
                        );
                    }

                } else if (choice == 3) {
                    System.out.println("\n--- Generate Bill ---");

                    System.out.print("Customer ID: ");
                    int id = sc.nextInt();

                    System.out.print("Units Consumed: ");
                    int units = sc.nextInt();

                    if (units < 0) {
                        System.out.println("❌ Units cannot be negative!");
                        continue;
                    }

                    double amount = calculateBill(units);

                    String query = "INSERT INTO bills (customer_id, units, amount) VALUES ("
                            + id + ", " + units + ", " + amount + ")";

                    stmt.executeUpdate(query);

                    System.out.println("💰 Bill Generated Successfully!");
                    System.out.println("Units: " + units);
                    System.out.println("Amount: ₹" + amount);

                } else if (choice == 4) {
                    System.out.println("\n--- Bills ---");

                    ResultSet rs = stmt.executeQuery("SELECT * FROM bills");

                    while (rs.next()) {
                        System.out.printf(
                            "BillID: %d | CustID: %d | Units: %d | Amount: ₹%.2f | Date: %s\n",
                            rs.getInt("id"),
                            rs.getInt("customer_id"),
                            rs.getInt("units"),
                            rs.getDouble("amount"),
                            rs.getTimestamp("bill_date")
                        );
                    }

                } else if (choice == 5) {
                    System.out.println("👋 Thank you for using system!");
                    break;

                } else {
                    System.out.println("❌ Invalid choice!");
                }
            }

            con.close();
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}