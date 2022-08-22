package study.datajpa.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("변경 감지 테스트")
    void updateMemberTest() {
        // given
        Member member = new Member("test", 20);
        memberRepository.save(member);

        // when
        String updateName = "newTest";
        memberService.updateMember(member.getId(), updateName);

        // then
        Member findMember = memberRepository.findById(member.getId()).get();
        Assertions.assertThat(findMember.getUsername())
                .isEqualTo(updateName);
    }
}