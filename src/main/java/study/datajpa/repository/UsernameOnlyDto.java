package study.datajpa.repository;

public class UsernameOnlyDto { // 인터페이스가 아닌 dto의 형식도 가능하다.
    private final String username;

    public UsernameOnlyDto(String username) { // 생성자의 파라미터 이름으로 매칭
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
