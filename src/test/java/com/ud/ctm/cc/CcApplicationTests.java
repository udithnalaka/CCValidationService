package com.ud.ctm.cc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.ud.ctm.cc.controller.CreditCardController;
import com.ud.ctm.cc.service.CreditCardService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CcApplication.class)
@WebAppConfiguration
class CcApplicationTests {

	private MockMvc ccMockMvc;

	@Autowired
	private CreditCardService creditCardService;

	private static final String CC_API_BASE_PATH = "/api/v1/cc";
	private static final String CC_API_VALIDATE_PATH = "/validate";

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		CreditCardController creditCardResource = new CreditCardController(creditCardService);

		this.ccMockMvc = MockMvcBuilders.standaloneSetup(creditCardResource).build();
	}

	@Test
	void validateCreditCardWithEmptyRequestBodyShouldReturnValidationError() throws Exception {

		ccMockMvc.perform(get(CC_API_BASE_PATH + CC_API_VALIDATE_PATH)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());

	}

}
