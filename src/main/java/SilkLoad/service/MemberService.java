package SilkLoad.service;

import SilkLoad.dto.MemberFormDto;
import SilkLoad.dto.ProductFormDto;
import SilkLoad.entity.Members;
import SilkLoad.entity.Product;
import SilkLoad.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.RecursiveTask;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService  {

    //final 붙여야지 생성자 만들어줌
    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberFormDto memberFormDto) {

        Members member = Members.builder()
                .loginId(memberFormDto.getLoginId())
                .name(memberFormDto.getName())
                .password((memberFormDto.getPassword()))
                .build();

        memberRepository.save(member);

    }

    public Members updatePassword( MemberFormDto memberFormDto) {

        Optional<Members> optionalMember = memberRepository.findByLoginId(memberFormDto.getLoginId());

        if (optionalMember.isPresent() ) {
            Members member = optionalMember.get();
            member.setPassword(memberFormDto.getPassword());

            if (member.getPassword() != null) {
                memberRepository.save(member);
                return member;
            }

        }
        return null;
    }

    public Members findByLoginId( String loginId ) {

        Optional<Members> optionalMember = memberRepository.findByLoginId(loginId);

        if (optionalMember.isPresent()) {
            return (Members) optionalMember.get();
        }

        return null;

    }

}
