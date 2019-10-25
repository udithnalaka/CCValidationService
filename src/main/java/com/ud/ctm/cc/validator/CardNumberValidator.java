package com.ud.ctm.cc.validator;

public interface CardNumberValidator {
	
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
	public boolean validateCardNumber(final String ccNumber) throws Exception;

}
