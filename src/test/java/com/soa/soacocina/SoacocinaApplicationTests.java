package com.soa.soacocina;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import({com.soa.soacocina.service.OrdenProduccionService.class, 
		 com.soa.soacocina.exception.GlobalExceptionHandler.class, 
		 com.soa.soacocina.exception.ErrorResponse.class})
@SpringBootTest
class SoacocinaApplicationTests {

	@Test
	void contextLoads() {
	}

}
