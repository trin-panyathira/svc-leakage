package com.example.leakage.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class LeakageRequestDto {
	@NotNull
	@Positive
	private double newLoanAmount;
	
	@NotNull
	@Positive
	private double requestNewLoanRate1st;
	
	@NotNull
	@Positive
	private double requestNewLoanRate2nd;
	
	@NotNull
	@Positive
	private double requestNewLoanRate3rd;
	
	private String caId;
	private UserInfoDto userInfo;

	public LeakageRequestDto() {}

	public double getNewLoanAmount() { return newLoanAmount; }
	public void setNewLoanAmount(double newLoanAmount) { this.newLoanAmount = newLoanAmount; }
	public double getRequestNewLoanRate1st() { return requestNewLoanRate1st; }
	public void setRequestNewLoanRate1st(double requestNewLoanRate1st) { this.requestNewLoanRate1st = requestNewLoanRate1st; }
	public double getRequestNewLoanRate2nd() { return requestNewLoanRate2nd; }
	public void setRequestNewLoanRate2nd(double requestNewLoanRate2nd) { this.requestNewLoanRate2nd = requestNewLoanRate2nd; }
	public double getRequestNewLoanRate3rd() { return requestNewLoanRate3rd; }
	public void setRequestNewLoanRate3rd(double requestNewLoanRate3rd) { this.requestNewLoanRate3rd = requestNewLoanRate3rd; }
	public String getCaId() { return caId; }
	public void setCaId(String caId) { this.caId = caId; }
	public UserInfoDto getUserInfo() { return userInfo; }
	public void setUserInfo(UserInfoDto userInfo) { this.userInfo = userInfo; }
}
