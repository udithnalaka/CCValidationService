package com.ud.ctm.cc.validator.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import com.ud.ctm.cc.validator.CardTypeValidator;

@Component(value = "CreditCardTypeValidator")
public class CreditCardTypeValidator implements CardTypeValidator {
	
	private static final String MASTER_CARD = "MasterCard";
	private static final String DISCOVER = "Discover";
	private static final String AMEX = "AMEX";
	private static final String VISA = "Visa";
	
	private static List<String> masterCardStartNums;
	private static List<String> amexCardStartNums;
	private static List<String> discoverCardStartNums;
	private static List<String> visaCardStartNums;
	
	static {
		//to hold and check credit card start digits
		masterCardStartNums = Arrays.asList("51", "52", "53", "54", "55");
		amexCardStartNums = Arrays.asList("34", "37");
		discoverCardStartNums = Arrays.asList("6011");
		visaCardStartNums = Arrays.asList("4");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String validateCardType(String ccNumber) throws Exception {
		
		if(ccNumber.length() >= 13 && ccNumber.length() <= 16) {
			if((ccNumber.length() == 13 || ccNumber.length() == 16) && 
					visaCardStartNums.contains(ccNumber.substring(0, 1))) {
				return VISA;
			} else if (ccNumber.length() == 15 && 
					(amexCardStartNums.contains(ccNumber.substring(0, 2)) )) {
				return AMEX;
			} else if (ccNumber.length() == 16 && 
					discoverCardStartNums.contains(ccNumber.substring(0, 4))) {
				return DISCOVER;
			} else if (ccNumber.length() == 16 && 
					(masterCardStartNums.contains(ccNumber.substring(0, 2)))) {
				return MASTER_CARD;
			}
		} 
		return Strings.EMPTY;
	}

}
