package SilkLoad.repository;

import SilkLoad.dto.ChatMessageDto;
import SilkLoad.entity.ChatMessage;
import SilkLoad.entity.Crawling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT NEW SilkLoad.dto.ChatMessageDto( c.Id , " +
            "c.members.name " +
            ", c.members.id " +
            ", c.message, " +
            "c.localDateTime) " +
            "FROM ChatMessage c " +
            "WHERE c.chatRoom.id = :chatRoomId")
    List<ChatMessageDto>  findByChatRoom_Id(@Param("chatRoomId") Long chatRoomId);


}
