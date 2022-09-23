package SilkLoad.config.auth;

import SilkLoad.entity.Members;
import SilkLoad.entity.UserRoleEnum.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
public class SecurityMember extends User {
    private static final String ROLE_PREFIX = "ROLE_";
    private static final long serialVersionUID = 1L;

    public SecurityMember(Members member) {

        super(member.getLoginId(), member.getPassword(), makeGrantedAuthority(Role.GUEST));
    }

    private static List<GrantedAuthority> makeGrantedAuthority(Role role){
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + "GUEST"));
        return list;
    }

}