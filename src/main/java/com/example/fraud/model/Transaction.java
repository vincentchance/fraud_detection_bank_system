package com.example.fraud.model;

import java.util.Date;

public class Transaction{
	private String transactionId;
	private String accountId;
	private double amount;
	private Date transactionDate;
	private String location; //lokasi IP like it happen on ATM or another device
	private String status; //fraudulent or not
	
	//Constructor
	public Transaction( String transactionId, String accountId, double amount, Date transactionDate, String location, String status ){
		this.transactionId = transactionId;
		this.accountId = accountId;
		this.amount = amount;
		this.transactionDate = transactionDate;
		this.location =  location;
		this.status = status;
	}
	
	//getters and setters
	public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // To String Method
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}