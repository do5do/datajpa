package study.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly { // interface만 정의하면 spring data jpa가 구현체를 만들어준다.
    // closed projections 이라고 한다.
    // String getUsername(); // getter 형식으로 지정하면 해당 필드만 선택해서 조회한다.

    // open projections => db에서 member에 관련된 모든 필드를 조회한 뒤 계산한다. => select절 최적화가 안된다.
    @Value("#{target.username + ' ' + target.age}")
    String getUsername();
}
