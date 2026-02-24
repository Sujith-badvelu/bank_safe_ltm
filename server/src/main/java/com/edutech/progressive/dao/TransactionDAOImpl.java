package com.edutech.progressive.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Transactions;

public class TransactionDAOImpl implements TransactionDAO {

    @Override
    public List<Transactions> getAllTransactions() throws SQLException {
        final String sql = "SELECT transaction_id, account_id, amount, transaction_date, transaction_type FROM transactions";
        List<Transactions> result = new ArrayList<>();

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(mapTransaction(rs));
            }
        }
        return result;
    }

    @Override
    public Transactions getTransactionById(int transactionId) throws SQLException {
        final String sql = "SELECT transaction_id, account_id, amount, transaction_date, transaction_type " +
                           "FROM transactions WHERE transaction_id = ?";
        Transactions t = null;

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, transactionId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    t = mapTransaction(rs);
                }
            }
        }
        return t;
    }

    @Override
    public int addTransaction(Transactions transaction) throws SQLException {
        final String sql = "INSERT INTO transactions (account_id, amount, transaction_date, transaction_type) " +
                           "VALUES (?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, transaction.getAccountId());
            // Use BigDecimal at the JDBC boundary for DECIMAL(10,2)
            ps.setBigDecimal(2, BigDecimal.valueOf(transaction.getAmount()));
            ps.setTimestamp(3, transaction.getTransactionDate() != null
                    ? new Timestamp(transaction.getTransactionDate().getTime())
                    : new Timestamp(System.currentTimeMillis()));
            ps.setString(4, transaction.getTransactionType());

            int count = ps.executeUpdate();
            if (count == 0) {
                throw new SQLException("Insert transaction failed; no rows affected.");
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    generatedId = keys.getInt(1);
                    // Set back on entity for immediate use by callers/tests
                    transaction.setTransactionId(generatedId);
                } else {
                    throw new SQLException("Insert transaction succeeded but no ID obtained.");
                }
            }
        }
        return generatedId;
    }

    @Override
    public void updateTransaction(Transactions transaction) throws SQLException {
        final String sql = "UPDATE transactions " +
                           "SET account_id = ?, amount = ?, transaction_date = ?, transaction_type = ? " +
                           "WHERE transaction_id = ?";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, transaction.getAccountId());
            ps.setBigDecimal(2, BigDecimal.valueOf(transaction.getAmount()));
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
    public void deleteTransaction(int transactionId) throws SQLException {
        final String sql = "DELETE FROM transactions WHERE transaction_id = ?";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, transactionId);
            ps.executeUpdate(); // DML -> executeUpdate()
        }
    }

    @Override
    public List<Transactions> getTransactionsByCustomerId(int customerId) throws SQLException {
        // Join transactions -> accounts to filter by customer_id
        final String sql =
            "SELECT t.transaction_id, t.account_id, t.amount, t.transaction_date, t.transaction_type " +
            "FROM transactions t INNER JOIN accounts a ON t.account_id = a.account_id " +
            "WHERE a.customer_id = ?";

        List<Transactions> result = new ArrayList<>();

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapTransaction(rs));
                }
            }
        }
        return result;
    }

    // ---------- Helper ----------
    private Transactions mapTransaction(ResultSet rs) throws SQLException {
        Transactions t = new Transactions();
        t.setTransactionId(rs.getInt("transaction_id"));
        t.setAccountId(rs.getInt("account_id"));

        BigDecimal amt = rs.getBigDecimal("amount");
        t.setAmount(amt != null ? amt.doubleValue() : 0.0);

        Timestamp ts = rs.getTimestamp("transaction_date");
        t.setTransactionDate(ts != null ? new Date(ts.getTime()) : null);

        t.setTransactionType(rs.getString("transaction_type"));
        return t;
    }
}