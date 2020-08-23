package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// 네이밍
// Service는 주로 비지니스 로직
// 리포지토리는 단순 데이터를 입출력 등
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     *
     * @param member
     * @return
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원 x
//        Optional<Member> result = memberRepository.findByName(member.getName());
        // Optional로 받으면 모양이 좋지 않아서 그냥 메소드 체이닝 하는걸 추천

//        memberRepository.findByName(member.getName())
//                .ifPresent(m -> {
//                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
//                });
        // 메소드로 뽑는걸 추천

        validataDuplicate(member);  // 중복 회원 검증
            // 값이 있으면 예외 발생
        memberRepository.save(member);
        return member.getId();
    }

    private void validataDuplicate(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     * @return
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
