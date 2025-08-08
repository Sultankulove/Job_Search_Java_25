package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MessageDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Message> getMessagesByResponseId(Long responseId) {
        String sql = "select * from messages where responded_applicants = ? order by timestamp asc";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, responseId);
        List<Message> messages = new ArrayList<>();
        for (Map<String, Object> map : rows) {
            Message msg = new Message();
            msg.setId(((Number) map.get("id")).longValue());
            msg.setRespondedApplicants(((Number) map.get("responded_applicants")).longValue());
            msg.setContent((String) map.get("content"));
            msg.setTimestamp((Timestamp) map.get("timestamp"));
            messages.add(msg);
        }
        return messages;
    }

    public void saveMessage(Message message) {
        String sql = "insert into messages (responded_applicants, content, timestamp) values (?, ?, ?)";
        jdbcTemplate.update(sql,
                message.getRespondedApplicants(),
                message.getContent(),
                message.getTimestamp()
        );
    }

}
