package SilkLoad.service;

import SilkLoad.dto.ChatMessageDto;
import SilkLoad.dto.ChatRoomDto;
import SilkLoad.dto.ChatRoomTableDto;
import SilkLoad.entity.ChatMessage;
import SilkLoad.entity.ChatRoom;
import SilkLoad.entity.ChatRoomEnum.ChatRoomType;
import SilkLoad.entity.Members;
import SilkLoad.repository.ChatMessageRepository;
import SilkLoad.repository.ChatRoomRepository;
import SilkLoad.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public boolean checkRoomPermission(Long roomId , Long memberId) {

        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(roomId);

        if (optionalChatRoom.isPresent()) {
            ChatRoom chatRoom = optionalChatRoom.get();
            ChatRoomType chatRoomType =chatRoom.getChatRoomType();
            if ( chatRoomType != ChatRoomType.close) {

                if ( (chatRoomType == ChatRoomType.onlySeller && chatRoom.getProduct().getId() == memberId)
                    || (chatRoomType == chatRoomType.onlyBuyer && chatRoom.getMembersBuyer().getId() == memberId
                    || (chatRoomType == chatRoomType.open)
                        )
                ) {
                    return true;
                }

            } else{
                return false;
            }
        }
        return false;
    }

    @Transactional
    public ChatRoomDto getChatRoom(Long RoomId) {

        ChatRoom chatRoom = chatRoomRepository.findById(RoomId).get();

        return ChatRoomDto.builder()
                .id(chatRoom.getId())
                .name(chatRoom.getName())
                .build();
    }

    @Transactional
    public Page<ChatRoomTableDto> getMemberChatRoomList(Long memberId, Pageable pageable) {
        return chatRoomRepository.findByMemberChatRoomList(memberId, pageable);
    }

    //방에 들어오면 기존 채팅 기록들을 찾아서 뽑아내야한다.
    @Transactional
    public List<ChatMessageDto> getChatMessageList(Long chatRoomId, Long userId) {

        if( checkTheRoomUser(chatRoomId, userId) ) {

            List<ChatMessageDto> chatMessageList = chatMessageRepository.findByChatRoom_Id(chatRoomId);

            return chatMessageList;
        };
        return null;
    }
    //확인한다.방 사용자를
    private Boolean checkTheRoomUser(Long chatRoomId, Long userId) {

        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(chatRoomId);

        if(optionalChatRoom.isPresent()) {

            ChatRoom chatRoom = optionalChatRoom.get();
            if( chatRoom.getMembersBuyer().getId() == userId ||
                    chatRoom.getProduct().getMembers().getId() == userId) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public ChatMessage saveChatMessage(ChatMessageDto chatMessageDto) {

        Members members = memberRepository.findById(chatMessageDto.getWriterId()).get();
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getRoomId()).get();

        ChatMessage buildMessage = ChatMessage.builder()
                .members(members)
                .chatRoom(chatRoom)
                .message(chatMessageDto.getMessage())
                .localDateTime(chatMessageDto.getCreateMessageTime())
                .build();

        return chatMessageRepository.save(buildMessage);

    }



    @Transactional
    public boolean exitRoom(Long roomId , Long memberId) {

        Optional<ChatRoom> optionalRoom = chatRoomRepository.findById(roomId);

        if (optionalRoom.isPresent()) {

            ChatRoom chatRoom = optionalRoom.get();

            if ( chatRoom.getChatRoomType() == ChatRoomType.open) {

                if (chatRoom.getMembersBuyer().getId() == memberId) {

                    chatRoom.setChatRoomType(ChatRoomType.onlySeller );
                }
                else {

                    chatRoom.setChatRoomType(ChatRoomType.onlyBuyer);
                }

            } else {

                chatRoom.setChatRoomType(ChatRoomType.close);

            }
            ChatRoom saveRoom = chatRoomRepository.save(chatRoom);

            return saveRoom != null ? true : false;

        }
        return false;
    }
}
