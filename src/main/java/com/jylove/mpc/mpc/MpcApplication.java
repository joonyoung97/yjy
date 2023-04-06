package com.jylove.mpc.mpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //엔티티 시간자동저장을 위해 추가
@SpringBootApplication
public class MpcApplication {

	public static void main(String[] args) {
		SpringApplication.run(MpcApplication.class, args);
	}

}
