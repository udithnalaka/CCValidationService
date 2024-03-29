package com.ud.ctm.cc.entity;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreditCardInfo {

	@NotBlank
	private String customerName;

	@Size(min = 13, max = 16)
	@NotBlank
	private String ccNumber;

	@Digits(integer = 3, fraction = 3)
	private int csv;
	
	public CreditCardInfo() {}

	public CreditCardInfo(String customerName, String ccNumber, int csv) {
		this.customerName = customerName;
		this.ccNumber = ccNumber;
		this.csv = csv;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCcNumber() {
		return ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	public int getCsv() {
		return csv;
	}

	public void setCsv(int csv) {
		this.csv = csv;
	}

	@Override
	public String toString() {
		return "CreditCardInfo [customerName=" + customerName + ", ccNumber=" + ccNumber + ", csv=" + csv + "]";
	}
	
}
