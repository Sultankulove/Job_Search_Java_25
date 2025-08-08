package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.MessageDto;

import java.util.List;

public interface ChatService {
    List<MessageDto> getChatByResponseId(Long responseId);

    void sendMessage(Long chatId, MessageDto dto);
}
