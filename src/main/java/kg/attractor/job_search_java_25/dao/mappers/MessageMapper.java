package kg.attractor.job_search_java_25.dao.mappers;

import kg.attractor.job_search_java_25.model.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        Message message = new Message();
        message.setId(rs.getLong("id"));
        message.setRespondedApplicants(rs.getLong("responded_applicants"));
        message.setContent(rs.getString("content"));
        message.setTimestamp(rs.getTimestamp("timestamp"));
        return message;
    }
}
