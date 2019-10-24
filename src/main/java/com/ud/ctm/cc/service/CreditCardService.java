package com.ud.ctm.cc.service;

import com.ud.ctm.cc.entity.CreditCardDTO;
import com.ud.ctm.cc.entity.CreditCardInfo;

public interface CreditCardService {
	
	/**
	 * validate a given credit card number.
	 * 
	 * @param creditCardInfo {@link CreditCardInfo}
	 * 
	 * @return {@link CreditCardDTO}
	 */
	CreditCardDTO validateCC(final CreditCardInfo creditCardInfo);

}
