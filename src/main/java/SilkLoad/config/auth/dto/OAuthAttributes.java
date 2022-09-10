package SilkLoad.config.auth.dto;

import SilkLoad.entity.Members;
import SilkLoad.entity.UserRoleEnum.Role;
import SilkLoad.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

//OAuth2UserService를 통해 가져온 네이버 OAuth2User의 attributes를 담을 클래스이다.
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        // 여기서 네이버와 카카오 등 구분 (ofNaver, ofKakao)

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST) // 기본 권한 GUEST
                .build();
    }

    //기본적인 members 구성요소
    public Members CreateMemberEntity(){
        return Members.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST) // 기본 권한 GUEST
                .ranks(1)
                .numberPurchase(0)
                .build();
    }

}