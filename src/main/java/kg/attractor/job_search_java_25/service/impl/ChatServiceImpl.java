package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.messageDto.MessageDto;
import kg.attractor.job_search_java_25.exceptions.types.ForbiddenException;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import kg.attractor.job_search_java_25.model.Message;
import kg.attractor.job_search_java_25.model.RespondedApplicant;
import kg.attractor.job_search_java_25.repository.MessageRepository;
import kg.attractor.job_search_java_25.repository.RespondedApplicantRepository;
import kg.attractor.job_search_java_25.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {
    private final MessageRepository messageRepository;
    private final RespondedApplicantRepository respondedApplicantRepository;

    @Transactional
    @Override
    public Long startChat(Long responseId, Long userId) {
        RespondedApplicant ra = respondedApplicantRepository.findById(responseId)
                .orElseThrow(() -> new NotFoundException("Response not found"));

        Long employerId = ra.getVacancy().getAuthor().getId();
        if (!userId.equals(employerId)) {
            throw new ForbiddenException("Only vacancy author can start the chat");
        }

        return responseId;
    }

    @Override
    public List<MessageDto> getChatByResponseId(Long responseId, Long userId) {
        log.debug("Chat.get(responseId={}, userId={})", responseId, userId);
        RespondedApplicant ra = respondedApplicantRepository.findById(responseId)
                .orElseThrow(() -> new NotFoundException("Response not found"));

        Long applicantId = ra.getResume().getApplicant().getId();
        Long employerId  = ra.getVacancy().getAuthor().getId();
        if (!userId.equals(applicantId) && !userId.equals(employerId))
            throw new ForbiddenException("Not your chat");

        return messageRepository.getMessagesByRespondedApplicant_IdOrderByTimestampAsc(responseId)
                .stream()
                .map(m -> MessageDto.builder()
                        .id(m.getId())
                        .content(m.getContent())
                        .timestamp(m.getTimestamp())
                        .build())
                .toList();
    }

    @Transactional
    @Override
    public MessageDto sendMessage(Long responseId, Long userId, MessageDto dto) {
        log.info("Chat.send(responseId={}, userId={})", responseId, userId);

        RespondedApplicant ra = respondedApplicantRepository.findById(responseId)
                .orElseThrow(() -> new NotFoundException("Response not found"));

        Long applicantId = ra.getResume().getApplicant().getId();
        Long employerId  = ra.getVacancy().getAuthor().getId();
        if (!userId.equals(applicantId) && !userId.equals(employerId))
            throw new ForbiddenException("Not your chat");

        if (dto.getContent() == null || dto.getContent().isBlank())
            throw new IllegalArgumentException("Empty message");

        Message msg = new Message();
        msg.setRespondedApplicant(ra);
        msg.setContent(dto.getContent().trim());
        msg.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));

        msg = messageRepository.save(msg);
        log.debug("Message saved (id={}, len={})", msg.getId(), msg.getContent().length());

        return MessageDto.builder()
                .id(msg.getId())
                .content(msg.getContent())
                .timestamp(msg.getTimestamp())
                .build();
    }

}
