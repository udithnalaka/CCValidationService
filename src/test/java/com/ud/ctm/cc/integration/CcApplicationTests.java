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
	private static final String CC_API_VALIDATE_PATH = "/validate";
	
	private static final String RESPONSE_PARAM_USER_NAME = "customerName";
	private static final String RESPONSE_PARAM_CC_STATUS = "status";
	
	private static final String RESPONSE_CC_STATUS_VALID = "Valid";
	private static final String RESPONSE_CC_STATUS_INVALID = "Invalid";
	
	private static final String CC_NUMER_VALID_VISA = "4716220707196896";
	private static final String CC_NUMER_INVALID_VISA = "4716220707196890";
	private static final String CC_USER_NAME = "Udith Nalaka";
	private static final int CC_CSV_VALID = 123;
	

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

		ccMockMvc.perform(put(CC_API_BASE_PATH + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(null)))
			.andExpect(status().isBadRequest());

	}
	
	@Test
	void validateCreditCardWithValidVisaCardNumberShouldReturnValidState() throws Exception {

		ccMockMvc.perform(put(CC_API_BASE_PATH + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(createCreditCardInfoForTesting(
					CC_USER_NAME,CC_NUMER_VALID_VISA,CC_CSV_VALID))))
			.andExpect(status().isOk())
			.andExpect(jsonPath(RESPONSE_PARAM_USER_NAME).value(CC_USER_NAME))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_STATUS).value(RESPONSE_CC_STATUS_VALID));
	}
	
	@Test
	void validateCreditCardWithInValidVisaCardNumberShouldReturnInValidState() throws Exception {

		ccMockMvc.perform(put(CC_API_BASE_PATH + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.convertObjectToJsonBytes(createCreditCardInfoForTesting(
					CC_USER_NAME,CC_NUMER_INVALID_VISA,CC_CSV_VALID))))
			.andExpect(status().isOk())
			.andExpect(jsonPath(RESPONSE_PARAM_USER_NAME).value(CC_USER_NAME))
			.andExpect(jsonPath(RESPONSE_PARAM_CC_STATUS).value(RESPONSE_CC_STATUS_INVALID));
	}

}
