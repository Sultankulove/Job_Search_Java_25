package kg.attractor.job_search_java_25.dao.mappers;


import kg.attractor.job_search_java_25.model.ContactType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactTypeMapper implements RowMapper<ContactType> {

    @Override
    public ContactType mapRow(ResultSet rs, int rowNum) throws SQLException {
        ContactType contactType = new ContactType();
        contactType.setId(rs.getLong("id"));
        contactType.setType(rs.getString("type"));
        return contactType;
    }
}
