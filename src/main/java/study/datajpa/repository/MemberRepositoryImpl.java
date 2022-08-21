package study.datajpa.repository;

import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

// 구현 클레스 네이밍 규칙: 기본 repository 이름 + Impl => MemberRepositoryImpl
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom { // custom interface(=MemberRepositoryCustom) 이름은 아무거나 상관 없음.
    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
