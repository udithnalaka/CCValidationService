package com.ud.ctm.cc.service;

import org.springframework.stereotype.Service;

import com.ud.ctm.cc.entity.CreditCardDTO;
import com.ud.ctm.cc.entity.CreditCardInfo;

@Service
public class CreditCardServiceImpl implements CreditCardService {
	
	private static String VALID_CC_STATUS = "Valid";
	private static String INVALID_CC_STATUS = "Invalid";
	

	@Override
	public CreditCardDTO validateCC(CreditCardInfo creditCardInfo) {
		
		boolean isValid = validateCreditCardNumber(creditCardInfo.getCcNumber());
		
		String isCCValid = isValid ? VALID_CC_STATUS: INVALID_CC_STATUS;
		
		return new CreditCardDTO(creditCardInfo.getCustomerName(), isCCValid, "");
	}
	
	
	/**
	 * Validate the Credit Card number according to the below logic.
	 * 
	 * 1. Starting with the second last digit and continuing with every other digit 
	 * going back to the beginning of the card, double the digit.
	 * 2. Sum all the doubled and untouched digits in the number. 
	 * For digits greater than 9,split them and sum them independently.
	 * 3. If the sum total is a multiple of 10 then the card number is valid.
	 * 
	 * @param ccNumber String
	 * 
	 * @return boolean CCNUmer valid or not
	 */
	private boolean validateCreditCardNumber(String ccNumber) {

		int totalValue = 0;
		boolean isAlternateNumber = false;

		// iterate the ccNumber from the end
		for (int i = ccNumber.length() - 1; i >= 0; i--) {
			
			int digitValue = Integer.parseInt(ccNumber.substring(i, i + 1));
			
			//to check if every other digit
			if (isAlternateNumber) {
				
				digitValue *= 2;
				
				if (digitValue > 9) {
					
					//split and add the double digit value
					digitValue = 1 + (digitValue % 10);
				}
			}
			
			totalValue += digitValue;
			
			isAlternateNumber = !isAlternateNumber;
		}
		
		//valid if mod = 0
		return (totalValue % 10 == 0);
	}


}
