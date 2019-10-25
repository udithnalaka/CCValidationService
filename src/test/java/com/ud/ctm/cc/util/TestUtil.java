package com.ud.ctm.cc.util;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Utility class for testing REST controllers.
 * 
 * @author udith
 *
 */
public class TestUtil {


	/**
	 * Convert an object to JSON byte array.

	 * @throws IOException
	 */
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		JavaTimeModule module = new JavaTimeModule();
		mapper.registerModule(module);

		return mapper.writeValueAsBytes(object);
	}

}
