package deer1;

import java.sql.*;
import java.util.*;

// =======================================================
// VEHICLE SERVICE MANAGEMENT SYSTEM - SINGLE FILE VERSION
// =======================================================
public class VehicleServiceSystem {

    // ===== DB CONNECTION =====
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/vehicle_service_db";
        String user = "root";
        String pass = ""; // XAMPP default
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            return null;
        }
    }

    // ===== MODEL CLASS =====
    static class ServiceRecord {
        int id;
        String customerName, phone, vehicleNumber, vehicleModel;
        String problemDescription, status, remarks;
        double serviceCost;
    }

    // ====== CRUD OPERATIONS ======
    // Add Record
    public static void addRecord() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Customer Name: ");
        String name = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Vehicle Number: ");
        String vno = sc.nextLine();
        System.out.print("Vehicle Model: ");
        String model = sc.nextLine();
        System.out.print("Problem Description: ");
        String prob = sc.nextLine();
        System.out.print("Status (Pending/In-Progress/Completed): ");
        String status = sc.nextLine();
        System.out.print("Service Cost: ");
        double cost = sc.nextDouble();
        sc.nextLine();
        System.out.print("Remarks: ");
        String remarks = sc.nextLine();

        String sql = "INSERT INTO service_records (customer_name, phone, vehicle_number, vehicle_model, problem_description, status, service_cost, remarks) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, vno);
            ps.setString(4, model);
            ps.setString(5, prob);
            ps.setString(6, status);
            ps.setDouble(7, cost);
            ps.setString(8, remarks);

            ps.executeUpdate();
            System.out.println("\n✔ Record Added Successfully!");

        } catch (Exception e) {
            System.out.println("Add Error: " + e.getMessage());
        }
    }

    // View All Records
    public static void viewRecords() {
        String sql = "SELECT * FROM service_records";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n===== SERVICE RECORDS =====");

            while (rs.next()) {
                System.out.println("\nID: " + rs.getInt("id"));
                System.out.println("Customer: " + rs.getString("customer_name"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("Vehicle No: " + rs.getString("vehicle_number"));
                System.out.println("Model: " + rs.getString("vehicle_model"));
                System.out.println("Problem: " + rs.getString("problem_description"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("Cost: " + rs.getDouble("service_cost"));
                System.out.println("Remarks: " + rs.getString("remarks"));
                System.out.println("---------------------------------");
            }

        } catch (Exception e) {
            System.out.println("Fetch Error: " + e.getMessage());
        }
    }

    // Update Record
    public static void updateRecord() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Record ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("New Status: ");
        String status = sc.nextLine();

        System.out.print("New Service Cost: ");
        double cost = sc.nextDouble();
        sc.nextLine();

        System.out.print("New Remarks: ");
        String remarks = sc.nextLine();

        String sql = "UPDATE service_records SET status=?, service_cost=?, remarks=? WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setDouble(2, cost);
            ps.setString(3, remarks);
            ps.setInt(4, id);

            ps.executeUpdate();
            System.out.println("\n✔ Record Updated Successfully!");

        } catch (Exception e) {
            System.out.println("Update Error: " + e.getMessage());
        }
    }

    // Delete Record
    public static void deleteRecord() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Completed Record ID to Delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM service_records WHERE id=? AND status='Completed'";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("\n✔ Record Deleted Successfully!");
            else
                System.out.println("\n❌ Only Completed Records Can Be Deleted!");

        } catch (Exception e) {
            System.out.println("Delete Error: " + e.getMessage());
        }
    }

    // ===== MAIN MENU =====
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== VEHICLE SERVICE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add New Vehicle Service Record");
            System.out.println("2. View All Service Records");
            System.out.println("3. Update Service Status / Cost");
            System.out.println("4. Delete Completed Service Record");
            System.out.println("5. Exit");
            System.out.print("Enter Choice: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1: addRecord(); break;
                case 2: viewRecords(); break;
                case 3: updateRecord(); break;
                case 4: deleteRecord(); break;
                case 5:
                    System.out.println("Thank You!");
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}

