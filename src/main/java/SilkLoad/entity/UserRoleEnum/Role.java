package SilkLoad.entity.UserRoleEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//OAuth2 사용할 때 사용자 권한을 관리하는 Role을 생성
@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER","일반 사용자");

    private final String key;
    private final String title;
}
