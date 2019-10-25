package com.ud.ctm.cc.entity;

public class CreditCardDTO {

	private String customerName;

	private String status;

	private String cardType;
	
	public CreditCardDTO() {}

	public CreditCardDTO(String customerName, String status, String cardType) {
		this.customerName = customerName;
		this.status = status;
		this.cardType = cardType;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	@Override
	public String toString() {
		return "CreditCardDTO [customerName=" + customerName + ", status=" + status + ", cardType=" + cardType + "]";
	}

}
