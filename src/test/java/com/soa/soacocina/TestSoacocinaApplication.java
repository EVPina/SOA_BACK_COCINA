package com.soa.soacocina;

import org.springframework.boot.SpringApplication;

public class TestSoacocinaApplication {

	public static void main(String[] args) {
		SpringApplication.from(SoacocinaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
