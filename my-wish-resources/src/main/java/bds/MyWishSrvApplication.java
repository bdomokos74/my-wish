package bds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
public class MyWishSrvApplication {
	Logger logger = LoggerFactory.getLogger(MyWishSrvApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MyWishSrvApplication.class, args);
	}



}

