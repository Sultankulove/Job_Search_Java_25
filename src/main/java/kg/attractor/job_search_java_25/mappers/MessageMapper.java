package kg.attractor.job_search_java_25.mappers;

import kg.attractor.job_search_java_25.dto.messageDto.MessageDto;
import kg.attractor.job_search_java_25.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public MessageDto toDto(Message m) {
        if (m == null) return null;
        return new MessageDto(
                m.getId(),
                m.getRespondedApplicant().getId(),
                m.getContent(),
                m.getCreatedAt(),
                m.getTimestamp()
        );
    }
}
