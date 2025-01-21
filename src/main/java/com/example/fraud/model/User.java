package com.example.fraud.model;

public class User{
	private int userId;
	private String name;
	private String accountId;
	private String email;
	
	public User( int userId, String name, String accountId, String email){
		this.userId = userId;
		this.name = name;
		this.accountId = accountId;
		this.email = email;
	}
	
	//kita hanya perlu mengambil data dari database tabel user maka menggunakan getter
	public int getUserId(){
		return userId;
	}
	
	public String getName(){
		return name;
	}
	
	public String getAccountId(){
		return accountId;
	}
	
	public String getEmail(){
		return email;
	}
	
}