package SilkLoad.repository;

import SilkLoad.dto.ChatRoomTableDto;
import SilkLoad.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT " +
            "NEW SilkLoad.dto.ChatRoomTableDto(c.id, c.name, c.product.name, c.membersBuyer.name ) " +
            "FROM ChatRoom c " +
            "WHERE ( c.product.members.id = :memberId " +
            "AND c.chatRoomType = SilkLoad.entity.ChatRoomEnum.ChatRoomType.onlySeller ) " +
            "OR ( c.membersBuyer.id = :memberId " +
            " AND c.chatRoomType = SilkLoad.entity.ChatRoomEnum.ChatRoomType.onlyBuyer ) " +
            "OR ( (c.product.members.id = :memberId OR c.membersBuyer.id = :memberId) AND " +
            "c.chatRoomType = SilkLoad.entity.ChatRoomEnum.ChatRoomType.open )"
            )
    Page<ChatRoomTableDto> findByMemberChatRoomList(@Param("memberId") Long memberId, Pageable pageable);

}
