package SilkLoad.service;

import SilkLoad.dto.LoginFormDto;
import SilkLoad.entity.Members;
import SilkLoad.entity.User;
import SilkLoad.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LoginService  {

    private final MemberRepository memberRepository;


    //성능향상상
    @Transactional(readOnly = true)
    public Members login(String loginId, String password) {

        Optional<Members> optionalMember = memberRepository.findByLoginId(loginId);

        if (optionalMember.isPresent()) {
            Members member = optionalMember.get();
            return member.getPassword().equals(password) ? member : null;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Members loginWithOauth2(String email) {

        Optional<Members> optionalMember = memberRepository.findByEmail(email);

        if (optionalMember.isPresent()) {
            Members member = optionalMember.get();
            return member;
        }
        return null;
    }
}
