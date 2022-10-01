package SilkLoad.service;

import SilkLoad.dto.MemberFormDto;
import SilkLoad.entity.Members;
import SilkLoad.entity.Product;
import SilkLoad.entity.UserRoleEnum.Role;
import SilkLoad.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService  {

    //final 붙여야지 생성자 만들어줌
    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberFormDto memberFormDto) {
        Members member = Members.builder()
                .name(memberFormDto.getLoginId())
                .password(memberFormDto.getPassword())
                .email(memberFormDto.getLoginId())
                .picture(null)
                .role(Role.GUEST) // 기본 권한 GUEST
                .ranks(1)
                .numberPurchase(0)
                .build();

        memberRepository.save(member);

    }

    @Transactional
    public Members findByLoginId(String id){
        return memberRepository.findByLoginId(id).get();
    }

    @Transactional
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

    @Transactional(readOnly = true)
    public List<Product> findByLoginIdProductList( String loginId ) {

        Optional<Members> optionalMember = memberRepository.findByLoginId(loginId);

        if (optionalMember.isPresent()) {
            List<Product> productListDto = new ArrayList<Product>();
            List<Product> productList = optionalMember.get().getProductList();
            productList.forEach( product -> {
                productListDto.add(product);
            });

            return productListDto;
        }
        return null;
    }
}
