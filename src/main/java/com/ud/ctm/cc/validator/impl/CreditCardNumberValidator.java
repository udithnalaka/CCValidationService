package com.ud.ctm.cc.validator.impl;

import org.springframework.stereotype.Component;

import com.ud.ctm.cc.validator.CardNumberValidator;

@Component(value = "CreditCardNumberValidator")
public class CreditCardNumberValidator implements CardNumberValidator {

	private static final int ZERO = 0;
	private static final int ONE  = 1;
	private static final int TWO  = 2;
	private static final int NINE = 9;
	private static final int TEN  = 10;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean validateCardNumber(String ccNumber) throws Exception {

		int totalValue = 0;
		boolean isAlternateNumber = false;

		// iterate the ccNumber from the end
		for (int i = ccNumber.length() - 1; i >= 0; i--) {

			int digitValue = Integer.parseInt(ccNumber.substring(i, i + ONE));

			// to check if every other digit
			if (isAlternateNumber) {

				digitValue *= TWO;

				if (digitValue > NINE) {

					// split and add the double digit value
					digitValue = ONE + (digitValue % TEN);
				}
			}

			totalValue += digitValue;

			isAlternateNumber = !isAlternateNumber;
		}

		// valid if mod = 0
		return (totalValue % TEN == ZERO);

	}

}
