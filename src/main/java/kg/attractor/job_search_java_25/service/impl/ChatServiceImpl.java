package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.MessageDao;
import kg.attractor.job_search_java_25.dto.MessageDto;
import kg.attractor.job_search_java_25.model.Message;
import kg.attractor.job_search_java_25.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static java.lang.System.currentTimeMillis;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {
    private final MessageDao messageDao;

    @Override
    public List<MessageDto> getChatByResponseId(Long responseId) {

        log.debug("ChatService.getChatByResponseId(responseId={}) — start", responseId);
        List<Message> messages = messageDao.getMessagesByResponseId(responseId);
        log.info("Чат: получено сообщений {}", messages.size());

        return messages.stream()
                .map(msg -> MessageDto.builder()
                        .id(msg.getId())
                        .content(msg.getContent())
                        .timestamp(msg.getTimestamp())
                        .build())
                .toList();
    }

    @Override
    public void sendMessage(Long chatId, MessageDto dto) {

        log.info("ChatService.sendMessage(chatId={})", chatId);
        Message msg = new Message();
        msg.setRespondedApplicants(chatId);
        msg.setContent(dto.getContent());
        msg.setTimestamp(new Timestamp(currentTimeMillis()));

        messageDao.saveMessage(msg);
        log.debug("Сообщение сохранено (len={})", dto.getContent() == null ? 0 : dto.getContent().length());

    }
}
