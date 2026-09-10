import java.sql.*;

public class backend {

    //  EDIT THESE TO MATCH YOUR MYSQL SETUP 
    private static final String URL = "jdbc:mysql://localhost:3306/cybercrime_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "your_mysql_password";

    // Opens a connection to the database
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


    // Creates a new account. Returns true if it worked.
    public static boolean register(String username, String email, String password, String role) {
        String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, role);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Register failed: " + e.getMessage());
            return false;
        }
    }

    // Checks username/email + password. Returns the user's id, or -1 if login failed.
    public static int login(String usernameOrEmail, String password) {
        String sql = "SELECT id FROM users WHERE (username = ? OR email = ?) AND password = ?";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usernameOrEmail);
            ps.setString(2, usernameOrEmail);
            ps.setString(3, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        return -1;
    }

    // Returns the role ("Admin" or "User") for a given user id.
    public static String getRole(int userId) {
        String sql = "SELECT role FROM users WHERE id = ?";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("role");
            }
        } catch (SQLException e) {
            System.out.println("Could not get role: " + e.getMessage());
        }
        return null;
    }

    //CRIME CASES

    // Saves a new report. Returns the generated case id (e.g. "CS20260007"), or null on failure.
    public static String reportCrime(String type, String description, String date, int userId) {
        String caseId = "CS2026" + String.format("%04d", (int) (Math.random() * 9000) + 1000);
        String sql = "INSERT INTO crime_cases (case_id, crime_type, description, incident_date, status, reported_by) " +
                "VALUES (?, ?, ?, ?, 'Open', ?)";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, caseId);
            ps.setString(2, type);
            ps.setString(3, description);
            ps.setString(4, date);
            ps.setInt(5, userId);
            return ps.executeUpdate() > 0 ? caseId : null;
        } catch (SQLException e) {
            System.out.println("Report failed: " + e.getMessage());
            return null;
        }
    }

    // Prints every case belonging to one citizen (for "My Cases").
    public static void printMyCases(int userId) {
        String sql = "SELECT case_id, crime_type, status FROM crime_cases WHERE reported_by = ?";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getString("case_id") + " | " +
                            rs.getString("crime_type") + " | " + rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Could not fetch cases: " + e.getMessage());
        }
    }

    // Prints every case in the system (for the Admin Dashboard's "Recent Cases").
    public static void printAllCases() {
        String sql = "SELECT case_id, crime_type, status FROM crime_cases ORDER BY id DESC";
        try (Connection con = connect(); Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getString("case_id") + " | " +
                        rs.getString("crime_type") + " | " + rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Could not fetch cases: " + e.getMessage());
        }
    }

    // Changes a case's status (Open / In Progress / Closed).
    public static boolean updateStatus(String caseId, String newStatus) {
        String sql = "UPDATE crime_cases SET status = ? WHERE case_id = ?";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setString(2, caseId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            return false;
        }
    }

    // Returns { total, open, inProgress, closed } for the Admin Dashboard's summary cards.
    public static int[] getStats() {
        int[] stats = new int[4];
        String sql = "SELECT status, COUNT(*) AS count FROM crime_cases GROUP BY status";
        try (Connection con = connect(); Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String status = rs.getString("status");
                int count = rs.getInt("count");
                stats[0] += count;
                if (status.equalsIgnoreCase("Open")) stats[1] = count;
                else if (status.equalsIgnoreCase("In Progress")) stats[2] = count;
                else if (status.equalsIgnoreCase("Closed")) stats[3] = count;
            }
        } catch (SQLException e) {
            System.out.println("Could not get stats: " + e.getMessage());
        }
        return stats;
    }

    // QUICK DEMO 
    // Run this file directly to see the whole flow work.
    public static void main(String[] args) {
        register("john_doe", "john@example.com", "pass123", "User");

        int userId = login("john_doe", "pass123");
        System.out.println("Logged in with id: " + userId);

        String caseId = reportCrime("Financial Fraud", "Unauthorized transaction", "09/09/2026", userId);
        System.out.println("Reported case: " + caseId);

        System.out.println("My cases:");
        printMyCases(userId);

        System.out.println("All cases:");
        printAllCases();

        updateStatus(caseId, "In Progress");

        int[] stats = getStats();
        System.out.println("Total: " + stats[0] + ", Open: " + stats[1] +
                ", In Progress: " + stats[2] + ", Closed: " + stats[3]);
    }
}