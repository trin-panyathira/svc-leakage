package com.example.leakage.dto;

public class UserInfoDto {
	private String firstName;
	private String lastName;
	private String advisor;
	private String tel;
	private double loanAmount;
	private double currentLoanRate1st;
	private double currentLoanRate2nd;
	private double currentLoanRate3rd;

	public UserInfoDto() {}

	public UserInfoDto(String firstName, String lastName, String advisor, String tel, 
					   double loanAmount, double currentLoanRate1st, double currentLoanRate2nd, double currentLoanRate3rd) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.advisor = advisor;
		this.tel = tel;
		this.loanAmount = loanAmount;
		this.currentLoanRate1st = currentLoanRate1st;
		this.currentLoanRate2nd = currentLoanRate2nd;
		this.currentLoanRate3rd = currentLoanRate3rd;
	}

	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	public String getAdvisor() { return advisor; }
	public void setAdvisor(String advisor) { this.advisor = advisor; }
	public String getTel() { return tel; }
	public void setTel(String tel) { this.tel = tel; }
	public double getLoanAmount() { return loanAmount; }
	public void setLoanAmount(double loanAmount) { this.loanAmount = loanAmount; }
	public double getCurrentLoanRate1st() { return currentLoanRate1st; }
	public void setCurrentLoanRate1st(double currentLoanRate1st) { this.currentLoanRate1st = currentLoanRate1st; }
	public double getCurrentLoanRate2nd() { return currentLoanRate2nd; }
	public void setCurrentLoanRate2nd(double currentLoanRate2nd) { this.currentLoanRate2nd = currentLoanRate2nd; }
	public double getCurrentLoanRate3rd() { return currentLoanRate3rd; }
	public void setCurrentLoanRate3rd(double currentLoanRate3rd) { this.currentLoanRate3rd = currentLoanRate3rd; }
}
