package kg.attractor.job_search_java_25.dao.mappers;

import kg.attractor.job_search_java_25.model.ContactInfo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsInfoMapper implements RowMapper<ContactInfo> {
    @Override
    public ContactInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setId(rs.getLong("id"));
        contactInfo.setTypeId(rs.getLong("type_id"));
        contactInfo.setResumeId(rs.getLong("resume_id"));
        contactInfo.setContactValue(rs.getString("contact_value"));
        return contactInfo;
    }
}
