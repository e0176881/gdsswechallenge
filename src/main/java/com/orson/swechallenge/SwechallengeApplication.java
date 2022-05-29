package com.orson.swechallenge;

import com.orson.swechallenge.entity.UserDetail;
import com.orson.swechallenge.repository.UserDetailRepository;
import com.orson.swechallenge.service.UserDetailService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.orson.swechallenge.controller", "com.orson.swechallenge.service"})
@EntityScan("com.orson.swechallenge.entity")
@EnableJpaRepositories("com.orson.swechallenge.repository")
public class SwechallengeApplication {

	@Autowired
	private UserDetailRepository userDetailRepository;

	public static void main(String[] args) {
		SpringApplication.run(SwechallengeApplication.class, args);
	}

	@Bean
	InitializingBean seedDatabase() {
		return () -> {
			UserDetail userDetail = new UserDetail();
			userDetail.setName("John");
			userDetail.setSalary(2000.0f);
			userDetailRepository.save(userDetail);

			userDetail = new UserDetail();
			userDetail.setName("John 2");
			userDetail.setSalary(4000.0f);
			userDetailRepository.save(userDetail);
		};
	}

}
