package com.ud.ctm.cc.entity;

public class CreditCardDTO {

	private String customerName;

	private String status;

	private String cardType;

	public CreditCardDTO(String customerName, String status, String cardType) {
		this.customerName = customerName;
		this.status = status;
		this.cardType = cardType;
	}

	@Override
	public String toString() {
		return "CreditCardDTO [customerName=" + customerName + ", status=" + status + ", cardType=" + cardType + "]";
	}

}
