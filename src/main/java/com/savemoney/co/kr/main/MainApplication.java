package com.savemoney.co.kr.main;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		/* SpringApplication.run(Chapter1Application.class, args); */
		SpringApplication application = new SpringApplication(MainApplication.class);
		/*
		 * application.setWebApplicationType(WebApplicationType.SERVLET);
		 * application.run(args);
		 */
		
		//WebApplicationType으로 설정할 수 있는 애플리케이션 타입은 세 가지다.
		/*
		 * 1. SERVLET은 기존의 스프링 MVC를 기반으로 웹 애플리케이션을 구동하겠다는 설정 
		 * 2. REACTIVE는 스프링 5.0에서 추가된 비동기(Asynchronous)처리와 
		 * 논블로킹 입출력(non-blocking I/O)을 지원하는 웹플럭스(WebFlux)를 적용할 때
		 * 사용한다.
		 * 3. NONE은 그냥 자바파일 실행
		 */
		 
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}

}
