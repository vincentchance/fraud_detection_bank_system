package com.example.fraud;

import com.example.fraud.service.FraudDetectionService;
import com.example.fraud.dao.UserDao;
import com.example.fraud.dao.TransactionDao;
import com.example.fraud.dao.AlertDao;
import com.example.fraud.dao.TransactionDao;
import com.example.fraud.dao.UserDao;
import com.example.fraud.model.Transaction;
import com.example.fraud.service.FraudDetectionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App {
    public static void main(String[] args) {
        FraudDetectionService fraudDetection = new FraudDetectionService();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Parse tanggal transaksi
        Date transactionDate = null;
        try {
            transactionDate = sdf.parse("2025-01-21 10:00:00");
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return; // Berhenti jika parsing gagal
        }

        double threshold = 10000000.00; // contoh threshold

        // Membuat tabel jika belum ada
        UserDao userDao = new UserDao();
        userDao.ensureTableExist();
        
        TransactionDao transactionDao = new TransactionDao();
        transactionDao.ensureTableExist();
        
        AlertDao alertDao = new AlertDao();
        alertDao.ensureTableExist();

        // Test transaksi untuk deteksi fraud
        Transaction test = new Transaction(
            "1",              // transaction_id (String)
            "1234",           // account_id
            15000000.00,      // amount
            transactionDate,  // transaction_date (Date)
            "0012:21212",     // location
            "pending"         // status
        );

        transactionDao.addTransaction(test);

        // Deteksi transaksi fraud berdasarkan threshold
        fraudDetection.detectAndFlagFrauds(threshold);
    }
}
