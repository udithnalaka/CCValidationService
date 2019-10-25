package com.ud.ctm.cc.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.ctm.cc.entity.CreditCardDTO;
import com.ud.ctm.cc.entity.CreditCardInfo;
import com.ud.ctm.cc.service.CreditCardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST controller for managing Credit Card related functionality.
 * 
 * @author udith
 *
 */

@RestController
@RequestMapping(path = "/api/v1/cc")
@Api(value = "Credit Card API Resources")
public class CreditCardController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardController.class);

	private final CreditCardService creditCardService;

	@Autowired
	public CreditCardController(final CreditCardService creditCardService) {
		this.creditCardService = creditCardService;
	}

	/**
	 * validate a given credit card number.
	 * 
	 * @param creditCardInfo {@link CreditCardInfo}
	 * 
	 * @return {@link CreditCardDTO}
	 * @throws Exception 
	 */
	@PutMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "CreditCard number is valid."),
			@ApiResponse(code = 400, message = "CreditCard number is invalid") })
	public ResponseEntity<CreditCardDTO> validateCreditCard(@Valid @RequestBody CreditCardInfo creditCardInfo) throws Exception {
		LOGGER.info("validateCreditCard(). CreditCardInfo : {}", creditCardInfo);

		return ResponseEntity.ok(creditCardService.validateCC(creditCardInfo));

	}

}
