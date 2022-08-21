package study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

	@Bean // 이 메서드가 BaseEntity의 등록자, 수정자를 넣어준다. 실무에서는 spring securityContext에서 로그인 정보나 session정보를 꺼내서 넣어주는 방식으로 사용한다.
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of(UUID.randomUUID().toString()); // .of() -> 반드시 값이 있어야 하는 객체인 경우에 사용

		// 위 lambda는 아래와 같음 -> interface에서 method가 하나면 lambda로 바꿀 수 있다.
		// return new AuditorAware<String>() {
		//			@Override
		//			public Optional<String> getCurrentAuditor() {
		//				return Optional.of(UUID.randomUUID().toString());
		//			}
		//		};
	}
}
