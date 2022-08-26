package hello.hellospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringApplication {

	public static void main(String[] args) {

		// SpringBootApplication이라는 annotation이 달린 클래스를 인자로 넣으면 springboot app 실행
		// tomcat 웹 서버가 내장되어 있어 app을 자동으로 화면에 띄워 준다
		SpringApplication.run(HelloSpringApplication.class, args);
	}

}
