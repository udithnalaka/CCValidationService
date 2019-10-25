package com.ud.ctm.cc.validator;

public interface CardTypeValidator {
	
	/**
	 * Validate the credit card type according to the following logic.
	 * 

	 * AMEX  - begin with 34 or 37. length = 15. 
	 * Discover   - begin with 6011. length = 16.
	 * MasterCard - begin with 51-55. length = 16.
	 * Visa       begin with 4. length = 13 or 16.
	 * 
	 * @param ccNumber String
	 * 
	 * @return cardType String
	 */
	public String validateCardType(final String ccNumber) throws Exception;

}
