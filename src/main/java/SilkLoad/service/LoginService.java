package SilkLoad.service;

import SilkLoad.dto.LoginFormDto;
import SilkLoad.entity.Members;
import SilkLoad.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LoginService extends LoginFormDto {

    private final MemberRepository memberRepository;

    public Members login(String loginId, String password) {

        Optional<Members> optionalMember = memberRepository.findByLoginId(loginId);

        if (optionalMember.isPresent()) {
            Members member = optionalMember.get();
            return member.getPassword().equals(password) ? member : null;
        }
        return null;
    }

}
