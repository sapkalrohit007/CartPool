package com.cmpe275.sjsu.cartpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.cmpe275.sjsu.cartpool.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class CartpoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartpoolApplication.class, args);
	}

}
