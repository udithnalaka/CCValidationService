package com.ud.ctm.cc.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ud.ctm.cc.CcApplication;
import com.ud.ctm.cc.controller.CreditCardController;
import com.ud.ctm.cc.entity.CreditCardInfo;
import com.ud.ctm.cc.service.CreditCardService;
import com.ud.ctm.cc.util.TestUtil;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CcApplication.class)
@WebAppConfiguration
class CcApplicationTests {

	private MockMvc ccMockMvc;

	@Autowired
	private CreditCardService creditCardService;

	private static final String CC_API_BASE_PATH = "/api/v1/cc";
	private static final String CC_API_VALIDATE_PATH = "validate";
	
	private static final String RESPONSE_PARAM_USER_NAME = "customerName";
	private static final String RESPONSE_PARAM_CC_STATUS = "status";
	private static final String RESPONSE_PARAM_CC_CARD_TYPE = "cardType";
	
	private static final String RESPONSE_CC_STATUS_VALID = "Valid";
	private static final String RESPONSE_CC_STATUS_INVALID = "Invalid";
	
	private static final String CC_VISA_NUMER_VALID = "4716220707196896";
	private static final String CC_VISA_NUMER_INVALID = "4716220707196890";
	private static final String CC_VISA_NUMER_INVALID_LENGTH = "471622070719689011";
	private static final String CC_VISA_CARD_TYPE = "Visa";
	
	private static final String CC_AMEX_NUMER_VALID = "371799974122028";
	private static final String CC_AMEX_NUMER_INVALID = "371799974122021";
	private static final String CC_AMEX_NUMER_INVALID_WITH_SIXTEEN_DIGITS = "3717999741220212";
	private static final String CC_AMEX_NUMER_INVALID_LENGTH = "37179997412202111";
	private static final String CC_AMEX_CARD_TYPE = "AMEX";
	
	private static final String CC_MASTERCARD_NUMER_VALID = "5264098993764699";
	private static final String CC_MASTERCARD_NUMER_INVALID = "2564098993764699";
	private static final String CC_MASTERCARD_NUMER_INVALID_LENGTH = "52640989937646991";
	private static final String CC_MASTERCARD_CARD_TYPE = "MasterCard";
	
	private static final String CC_DISCOVER_NUMER_VALID = "6011045500281208";
	private static final String CC_DISCOVER_NUMER_INVALID = "6011045500281202";
	private static final String CC_DISCOVER_NUMER_INVALID_LENGTH = "601104550028120822";
	private static final String CC_DISCOVER_CARD_TYPE = "Discover";
	
	private static final String CC_USER_NAME = "Udith Nalaka";
	private static final int CC_CSV_VALID = 123;
	private static final int CC_CSV_INVALID = 1234;
	

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		CreditCardController creditCardResource = new CreditCardController(creditCardService);

		this.ccMockMvc = MockMvcBuilders.standaloneSetup(creditCardResource).build();
	}

	private CreditCardInfo createCreditCardInfoForTesting(String name, String ccNumber, int csv) {
		return new CreditCardInfo(name, ccNumber, csv);
	}
	
	@Test
	void validateCreditCardWithEmptyRequestBodyShouldReturnValidationError() throws Exception {

		ccMockMvc.perform(put(CC_API_BASE_PATH + "/" + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(null)))
			.andExpect(status().isBadRequest());

	}
	
	@Test
	void validateCreditCardWithInValidCSVLengthShouldReturnValidationError() throws Exception {

		ccMockMvc.perform(put(CC_API_BASE_PATH + "/" + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(createCreditCardInfoForTesting(
					CC_USER_NAME,CC_VISA_NUMER_VALID,CC_CSV_INVALID))))
		.andExpect(status().isBadRequest());
	}
	
	////START - VISA card
	@Test
	void validateCreditCardWithValidVisaCardNumberShouldReturnValidState() throws Exception {

		ccMockMvc.perform(put(CC_API_BASE_PATH + "/" + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(createCreditCardInfoForTesting(
					CC_USER_NAME,CC_VISA_NUMER_VALID,CC_CSV_VALID))))
			.andExpect(status().isOk())
			.andExpect(jsonPath(RESPONSE_PARAM_USER_NAME).value(CC_USER_NAME))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_STATUS).value(RESPONSE_CC_STATUS_VALID))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_CARD_TYPE).value(CC_VISA_CARD_TYPE));
	}
	
	@Test
	void validateCreditCardWithInValidVisaCardNumberShouldReturnInValidState() throws Exception {

		ccMockMvc.perform(put(CC_API_BASE_PATH + "/" + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(createCreditCardInfoForTesting(
					CC_USER_NAME,CC_VISA_NUMER_INVALID,CC_CSV_VALID))))
			.andExpect(status().isOk())
			.andExpect(jsonPath(RESPONSE_PARAM_USER_NAME).value(CC_USER_NAME))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_STATUS).value(RESPONSE_CC_STATUS_INVALID))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_CARD_TYPE).value(""));
	}
	
	@Test
	void validateCreditCardWithInValidVisaCardNumberLengthShouldReturnValidationError() throws Exception {

		ccMockMvc.perform(put(CC_API_BASE_PATH + "/" + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(createCreditCardInfoForTesting(
					CC_USER_NAME,CC_VISA_NUMER_INVALID_LENGTH,CC_CSV_VALID))))
		.andExpect(status().isBadRequest());
	}
	////END - VISA card
	
	
	////START - AMEX card
	@Test
	void validateCreditCardWithValidAmexCardNumberShouldReturnValidState() throws Exception {

		ccMockMvc.perform(put(CC_API_BASE_PATH + "/" + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(createCreditCardInfoForTesting(
					CC_USER_NAME,CC_AMEX_NUMER_VALID,CC_CSV_VALID))))
			.andExpect(status().isOk())
			.andExpect(jsonPath(RESPONSE_PARAM_USER_NAME).value(CC_USER_NAME))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_STATUS).value(RESPONSE_CC_STATUS_VALID))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_CARD_TYPE).value(CC_AMEX_CARD_TYPE));
	}
	
	@Test
	void validateCreditCardWithInValidAmexCardNumberShouldReturnInValidState() throws Exception {

		ccMockMvc.perform(put(CC_API_BASE_PATH + "/" + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(createCreditCardInfoForTesting(
					CC_USER_NAME,CC_AMEX_NUMER_INVALID,CC_CSV_VALID))))
			.andExpect(status().isOk())
			.andExpect(jsonPath(RESPONSE_PARAM_USER_NAME).value(CC_USER_NAME))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_STATUS).value(RESPONSE_CC_STATUS_INVALID))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_CARD_TYPE).value(""));
	}
	
	@Test
	void validateCreditCardWithInValidAmexCardNumberWIthSixteenDigitsShouldReturnInValidState() throws Exception {

		ccMockMvc.perform(put(CC_API_BASE_PATH + "/" + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(createCreditCardInfoForTesting(
					CC_USER_NAME,CC_AMEX_NUMER_INVALID_WITH_SIXTEEN_DIGITS,CC_CSV_VALID))))
			.andExpect(status().isOk())
			.andExpect(jsonPath(RESPONSE_PARAM_USER_NAME).value(CC_USER_NAME))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_STATUS).value(RESPONSE_CC_STATUS_INVALID))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_CARD_TYPE).value(""));
	}
	

	@Test
	void validateCreditCardWithInValidAmexCardNumberLengthShouldReturnValidationError() throws Exception {

		ccMockMvc.perform(put(CC_API_BASE_PATH + "/" + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(createCreditCardInfoForTesting(
					CC_USER_NAME,CC_AMEX_NUMER_INVALID_LENGTH,CC_CSV_VALID))))
		.andExpect(status().isBadRequest());
	}
	////END - AMEX card
	
	
	////START - MasterCard
	@Test
	void validateCreditCardWithValidMasterCardNumberShouldReturnValidState() throws Exception {

		ccMockMvc.perform(put(CC_API_BASE_PATH + "/" + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(createCreditCardInfoForTesting(
					CC_USER_NAME,CC_MASTERCARD_NUMER_VALID,CC_CSV_VALID))))
			.andExpect(status().isOk())
			.andExpect(jsonPath(RESPONSE_PARAM_USER_NAME).value(CC_USER_NAME))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_STATUS).value(RESPONSE_CC_STATUS_VALID))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_CARD_TYPE).value(CC_MASTERCARD_CARD_TYPE));
	}
	
	@Test
	void validateCreditCardWithInValidMasterCardNumberShouldReturnInValidState() throws Exception {

		ccMockMvc.perform(put(CC_API_BASE_PATH + "/" + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(createCreditCardInfoForTesting(
					CC_USER_NAME,CC_MASTERCARD_NUMER_INVALID,CC_CSV_VALID))))
			.andExpect(status().isOk())
			.andExpect(jsonPath(RESPONSE_PARAM_USER_NAME).value(CC_USER_NAME))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_STATUS).value(RESPONSE_CC_STATUS_INVALID))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_CARD_TYPE).value(""));
	}
	
	@Test
	void validateCreditCardWithInValidMasterCardNumberLengthShouldReturnValidationError() throws Exception {

		ccMockMvc.perform(put(CC_API_BASE_PATH + "/" + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(createCreditCardInfoForTesting(
					CC_USER_NAME,CC_MASTERCARD_NUMER_INVALID_LENGTH,CC_CSV_VALID))))
		.andExpect(status().isBadRequest());
	}
	////END - MasterCard
	
	
	////START - Discover
	@Test
	void validateCreditCardWithValidDiscoverCardNumberShouldReturnValidState() throws Exception {

		ccMockMvc.perform(put(CC_API_BASE_PATH + "/" + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(createCreditCardInfoForTesting(
					CC_USER_NAME,CC_DISCOVER_NUMER_VALID,CC_CSV_VALID))))
			.andExpect(status().isOk())
			.andExpect(jsonPath(RESPONSE_PARAM_USER_NAME).value(CC_USER_NAME))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_STATUS).value(RESPONSE_CC_STATUS_VALID))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_CARD_TYPE).value(CC_DISCOVER_CARD_TYPE));
	}
	
	@Test
	void validateCreditCardWithInValidDiscoverCardNumberShouldReturnInValidState() throws Exception {

		ccMockMvc.perform(put(CC_API_BASE_PATH + "/" + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(createCreditCardInfoForTesting(
					CC_USER_NAME,CC_DISCOVER_NUMER_INVALID,CC_CSV_VALID))))
			.andExpect(status().isOk())
			.andExpect(jsonPath(RESPONSE_PARAM_USER_NAME).value(CC_USER_NAME))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_STATUS).value(RESPONSE_CC_STATUS_INVALID))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_CARD_TYPE).value(""));
	}
	
	@Test
	void validateCreditCardWithInValidDiscoverCardNumberLengthShouldReturnValidationError() throws Exception {

		ccMockMvc.perform(put(CC_API_BASE_PATH + "/" + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(createCreditCardInfoForTesting(
					CC_USER_NAME,CC_DISCOVER_NUMER_INVALID_LENGTH,CC_CSV_VALID))))
		.andExpect(status().isBadRequest());
	}
	////END - Discover
}
