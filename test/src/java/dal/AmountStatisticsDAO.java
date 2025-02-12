/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class AmountStatisticsDAO {

    private DBContext dbContext; // Store an instance of DBContext

    // Constructor to initialize the DBContext
    public AmountStatisticsDAO() {
        this.dbContext = new DBContext();
    }

    public enum Table {
        CUSTOMER("customer", "amount", "created_at"),
        TRANSACTIONS("transactions", "amount", "transaction_date"),
        LOAN_DISBURSEMENTS("loan_disbursements", "amount", "disbursement_date"),
        SAVINGS("savings", "amount", "start_date"),
        INSURANCE_TRANSACTIONS("insurance_transactions", "amount", "transaction_date"),
        LOAN("loan", "amount", "start_date");

        private final String tableName;
        private final String amountColumn;
        private final String dateColumn;

        Table(String tableName, String amountColumn, String dateColumn) {
            this.tableName = tableName;
            this.amountColumn = amountColumn;
            this.dateColumn = dateColumn;
        }

        public String getTableName() {
            return tableName;
        }

        public String getAmountColumn() {
            return amountColumn;
        }

        public String getDateColumn() {
            return dateColumn;
        }
    }

    public List<Table> getTablesWithAmountColumn() {
        List<Table> tables = new ArrayList<>();
        for (Table table : Table.values()) {
            tables.add(table);
        }
        return tables;
    }

    public List<Map<String, Object>> getAmountStatistics(Table table) {
        List<Map<String, Object>> results = new ArrayList<>();
        String sql = "SELECT YEAR(" + table.getDateColumn() + ") AS ReportYear, " +
                "MONTH(" + table.getDateColumn() + ") AS ReportMonth, " +
                "SUM(" + table.getAmountColumn() + ") AS TotalAmount " +
                "FROM " + table.getTableName() + " " +
                "GROUP BY YEAR(" + table.getDateColumn() + "), MONTH(" + table.getDateColumn() + ") " +
                "ORDER BY ReportYear, ReportMonth";

        try (Connection conn = dbContext.getConnection(); // Use DBContext to get the connection
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("year", rs.getInt("ReportYear"));
                row.put("month", rs.getInt("ReportMonth"));
                row.put("totalAmount", rs.getDouble("TotalAmount"));
                results.add(row);
            }

        } catch (Exception e) { //Catch generic exception since DBContext can throw one
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace(); //Very important to print the stack trace.
            return new ArrayList<>(); // Return empty list on error
        }

        return results;
    }
}
