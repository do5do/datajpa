package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

// @Repository 생략 가능 -> 컴포넌트 스캔을 spring data jpa가 자동으로 처리
// interface만 있어도 동작하는 이유 -> spring data jpa가 구현체를 만들어주기 때문
public interface MemberRepository extends JpaRepository<Member, Long> { // JpaRepository<Entity, pk(Id)> 를 의미한다.
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    // NamedQuery
    // @Query(name = "Member.findByUsername") -> 생략 가능 -> 우선순위가 엔티티에서 named query를 먼저 찾는다. 없으면 jpa가 메서드 이름으로 쿼리를 생성한다.
    // 이 namedQuery는 실무에서 잘 사용하지 않는다. -> repository에 쿼리를 정의하는 방식을 사용한다.
    List<Member> findByUsername(@Param("username") String username); // jpql이 명확히 작성되었을 때 named parameter를 넘겨줘야하기 때문에 @Param을 작성해줘야 한다.

    // repository에 쿼리를 정의하는 방식
    // -> 이름이 없는 namedQuery라고 보면 된다. 어플리케이션 실행 시점에 오류를 찾을 수 있다는 장점
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    // 값 하나 조회
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    // dto로 조회 -> new operation을 사용해야 한다.
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    List<Member> findListByUsername(String username); // 컬렉션
    Member findMemberByUsername(String username); // 단건
    Optional<Member> findOprionalByUsername(String username); // 단건 Optional

    Page<Member> findByAge(int age, Pageable pageable);

    Slice<Member> findAllByAge(int age, Pageable pageable);

    // count query 분리 -> join해서 페이징 하는 경우 count를 구하는 쿼리에서도 조인을해서 가져오기 때문에 성능이 느려질 수 있다.
    // 그래서 count하는 쿼리는 최적화 된 쿼리로 직접 정의해준다.
    @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m.username) from Member m")
    Page<Member> findByAgeSepCnt(int age, Pageable pageable);

    // bulk update
    @Modifying(clearAutomatically = true) // 이 어노테이션이 있어야 .executeUpdate()를 실행한다.
    // clearAutomatically = true : 영속성 컨텍스트 초기화
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);
}
