package kg.attractor.job_search_java_25.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public List<User> getAllUsers() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public User getUserById(Long id) {
        String sql = "select * from users where id = ?";

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
    }

    public void uploadAvatar(String avatar, Long userId) {
        // найти по id юзера и avatar записать на строку avatar
    }

    public String getAvatarByUserId(Long userId) {
        // По userId находит юзера и берет у него avatar

        String avatar = "";
        return avatar;
    }
}
