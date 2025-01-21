package com.example.fraud.model;

public class Alert {
	private int userId;
	private String transactionId;
	private double riskScore;
	private String message;
	private String createdAt;
	
	public Alert( int userId, String transactionId, double riskScore, String message, String createdAt){
		this.userId = userId;
		this.transactionId = transactionId;
		this.riskScore = riskScore;
		this.message = message;
		this.createdAt = createdAt;
	}
	
	public int getUserId(){
		return userId;
	}
	
	public String getTransactionId(){
		return transactionId;
	}
	
	public double getRiskScore(){
		return riskScore;
	}
	
	public String getMessage(){
		return message;
	}
	
	public String getCreatedAt(){
		return createdAt;
	}
}