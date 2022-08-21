package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember(@PathVariable("id") Member member) { // 도메인 클래스 컨버터 -> 권장하지 않음.. 사용한다면 단순 조회용으로만 사용하길
        return member.getUsername();
    }

    // 기본 paging: 기본 size는 20개, page는 기본 0부터 시작함
    // parameter: page=0&size=3&sort=id,desc
    @GetMapping("/members")
    public Page<Member> list(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    // paging 값 변경 (여기만 적용), Dto로 반환 (절대 entity로 반환하면 안된다!)
    @GetMapping("/members2")
    public Page<MemberDto> list2(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(MemberDto::new); // member -> new MemberDto(member)
    }

//    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user"+i, i));
        }
    }
}
