package com.example.fraud.service;

import com.example.fraud.dao.AlertDao;
import com.example.fraud.dao.TransactionDao;
import com.example.fraud.model.Alert;
import com.example.fraud.model.Transaction;

import java.util.List;

public class FraudDetectionService {
	private TransactionDao transactionDao;
	private AlertDao alertDao;
	
	public FraudDetectionService(){
		this.transactionDao = new TransactionDao();
		this.alertDao = new AlertDao();
	}
	public void detectAndFlagFrauds(double threshold){
		List<Transaction> transactions = transactionDao.findTransactionsAboveThreshold(threshold);
		
		for(Transaction transaction : transactions){
			transactionDao.flagTransaction(transaction.getTransactionId());
			
			//create an alert
			Alert alert = new Alert(0, transaction.getTransactionId(), 90.0, "Suspicious transaction detected. Amount exceeds " + threshold, null);
			alertDao.addAlert(alert);
		
			System.out.println("Transaction " + transaction.getTransactionId() + " flagged as fraudulent.");
		}
	}
	
}