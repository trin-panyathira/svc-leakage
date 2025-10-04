package com.example.leakage.dto;

public class CAItemDto {
	private String caId;
	private String firstName;
	private String lastName;
	private double loanAmount;

	public CAItemDto() {}

	public CAItemDto(String caId, String firstName, String lastName, double loanAmount) {
		this.caId = caId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.loanAmount = loanAmount;
	}

	public String getCaId() { return caId; }
	public void setCaId(String caId) { this.caId = caId; }
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	public double getLoanAmount() { return loanAmount; }
	public void setLoanAmount(double loanAmount) { this.loanAmount = loanAmount; }
}
