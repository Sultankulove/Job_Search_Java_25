package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.messageDto.MessageDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatService {

    Long startChat(Long responseId, Long userId);

    List<MessageDto> getChatByResponseId(Long responseId, Long userId);

    @Transactional
    MessageDto sendMessage(Long responseId, Long userId, MessageDto dto);
}
