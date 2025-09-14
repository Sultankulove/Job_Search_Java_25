package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.messageDto.MessageDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatService {

    List<MessageDto> getChatByResponseId(Long responseId, Long userId);

    @Transactional
    void sendMessage(Long responseId, Long userId, MessageDto dto);
}
