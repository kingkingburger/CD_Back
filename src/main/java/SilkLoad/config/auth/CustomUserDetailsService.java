package SilkLoad.config.auth;

import SilkLoad.entity.Members;
import SilkLoad.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return
                Optional.ofNullable(memberRepository.findByLoginId(username))
                        .filter(m -> m!= null)
                        .map(m -> new SecurityMember(m.get())).get();
    }
}