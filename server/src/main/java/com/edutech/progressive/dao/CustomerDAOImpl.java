package com.edutech.progressive.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.dto.CustomerAccountInfo;
import com.edutech.progressive.entity.Customers;
import com.edutech.progressive.entity.Transactions;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public List<Customers> getAllCustomers() throws SQLException {
        final String sql = "SELECT customer_id, name, email, username, password, role FROM customers";
        List<Customers> result = new ArrayList<>();

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(mapCustomer(rs));
            }
        }
        return result;
    }

    @Override
    public Customers getCustomerById(int customerId) throws SQLException {
        final String sql = "SELECT customer_id, name, email, username, password, role FROM customers WHERE customer_id = ?";
        Customers c = null;

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c = mapCustomer(rs);
                }
            }
        }
        return c;
    }

    @Override
    public int addCustomer(Customers customers) throws SQLException {
        final String sql = "INSERT INTO customers (name, email, username, password, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, customers.getName());
            ps.setString(2, customers.getEmail());
            ps.setString(3, customers.getUsername());
            ps.setString(4, customers.getPassword());

            // Role can be null at MS2 Day 3 stage
            if (customers.getRole() != null && !customers.getRole().trim().isEmpty()) {
                ps.setString(5, customers.getRole());
            } else {
                ps.setNull(5, Types.VARCHAR);
            }

            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new SQLException("Insert customer failed; no rows affected.");
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                // IMPORTANT: call next() before reading -> fixes "Before start of result set"
                if (keys.next()) {
                    int id = keys.getInt(1);
                    customers.setCustomerId(id); // helpful for callers/tests
                    return id;
                } else {
                    throw new SQLException("Insert customer succeeded but no ID obtained.");
                }
            }
        }
    }

    /**
     * NOTE: This method really belongs in TransactionDAOImpl, but keeping your existing
     * method intact here since it may be referenced elsewhere in your code.
     */
    public void updateTransaction(Transactions transaction) throws SQLException {
        final String sql = "UPDATE transactions " +
                "SET account_id = ?, amount = ?, transaction_date = ?, transaction_type = ? " +
                "WHERE transaction_id = ?";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, transaction.getAccountId());
            ps.setDouble(2, transaction.getAmount());

            Timestamp ts = (transaction.getTransactionDate() != null)
                    ? new Timestamp(transaction.getTransactionDate().getTime())
                    : new Timestamp(System.currentTimeMillis());

            ps.setTimestamp(3, ts);
            ps.setString(4, transaction.getTransactionType());
            ps.setInt(5, transaction.getTransactionId());

            ps.executeUpdate(); // DML -> executeUpdate()
        }
    }

    @Override
    public void deleteCustomer(int customerId) throws SQLException {
        final String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ps.executeUpdate(); // DML -> executeUpdate()
        }
    }

    public CustomerAccountInfo getCustomerAccountInfo(int customerId) throws SQLException {
        final String sql =
            "SELECT c.customer_id, c.name, c.email, a.account_id, a.balance " +
            "FROM customers c LEFT JOIN accounts a ON c.customer_id = a.customer_id " +
            "WHERE c.customer_id = ? " +
            "ORDER BY a.account_id ASC";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int custId = rs.getInt("customer_id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");

                    // LEFT JOIN may return nulls for account fields
                    int accountId = rs.getInt("account_id");
                    boolean accountIdWasNull = rs.wasNull();

                    double balance = 0.0;
                    if (!accountIdWasNull) {
                        // use BigDecimal to avoid DECIMAL precision issues, fallback to 0.0
                        java.math.BigDecimal bal = rs.getBigDecimal("balance");
                        balance = (bal != null) ? bal.doubleValue() : 0.0;
                    } else {
                        accountId = 0; // if your DTO requires primitives
                        balance = 0.0;
                    }

                    return new CustomerAccountInfo(custId, name, email, accountId, balance);
                }
            }
        }
        return null;
    }

    @Override
    public void updateCustomer(Customers customers) throws SQLException {
        final String sql = "UPDATE customers SET name = ?, email = ?, username = ?, password = ?, role = ? WHERE customer_id = ?";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customers.getName());
            ps.setString(2, customers.getEmail());
            ps.setString(3, customers.getUsername());
            ps.setString(4, customers.getPassword());

            if (customers.getRole() != null && !customers.getRole().trim().isEmpty()) {
                ps.setString(5, customers.getRole());
            } else {
                ps.setNull(5, Types.VARCHAR);
            }

            ps.setInt(6, customers.getCustomerId());
            ps.executeUpdate(); // DML -> executeUpdate()
        }
    }

    // ---------- Helper ----------
    private Customers mapCustomer(ResultSet rs) throws SQLException {
        Customers c = new Customers();
        c.setCustomerId(rs.getInt("customer_id"));
        c.setName(rs.getString("name"));
        c.setEmail(rs.getString("email"));
        c.setUsername(rs.getString("username"));
        c.setPassword(rs.getString("password"));
        c.setRole(rs.getString("role")); // may be null at MS2 stage
        return c;
    }
}