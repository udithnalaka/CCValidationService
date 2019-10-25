package com.ud.ctm.cc.service.impl;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.ctm.cc.entity.CreditCardDTO;
import com.ud.ctm.cc.entity.CreditCardInfo;
import com.ud.ctm.cc.service.CreditCardService;
import com.ud.ctm.cc.validator.CardNumberValidator;
import com.ud.ctm.cc.validator.CardTypeValidator;

@Service
public class CreditCardServiceImpl implements CreditCardService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardServiceImpl.class);

	private static String VALID_CC_STATUS = "Valid";
	private static String INVALID_CC_STATUS = "Invalid";

	private final CardTypeValidator cardTypeValidator;
	private final CardNumberValidator cardNumberValidator;

	@Autowired
	public CreditCardServiceImpl(final CardTypeValidator cardTypeValidator,
			final CardNumberValidator cardNumberValidator) {
		this.cardTypeValidator = cardTypeValidator;
		this.cardNumberValidator = cardNumberValidator;
	}

	@Override
	public CreditCardDTO validateCC(CreditCardInfo creditCardInfo) throws Exception {
		LOGGER.info("validateCC(). CreditCardInfo: {} ", creditCardInfo);

		try {
			String cardType = Strings.EMPTY;

			 boolean isValid = cardNumberValidator.validateCardNumber(creditCardInfo.getCcNumber());

			if (isValid) {
				cardType = cardTypeValidator.validateCardType(creditCardInfo.getCcNumber());
			}

			String isCCValid = isValid ? VALID_CC_STATUS : INVALID_CC_STATUS;

			return new CreditCardDTO(creditCardInfo.getCustomerName(), isCCValid, cardType);

		} catch (Exception e) {
			LOGGER.error("validateCC(). Backend Exception : {} ", e);
			throw e;
		}
	}

}
