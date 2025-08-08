package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.dao.mappers.UserMapper;
import kg.attractor.job_search_java_25.model.User;
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

    public User getMyProfile(Long auth) {
        String sql = "select * from users where id = ?";
        return jdbcTemplate.queryForObject(sql, new UserMapper(), auth);
    }

    public void editProfile(User user, Long id) {
        String sql = "update users set" +
                "name = ?, " +
                "surname = ?, " +
                "age = ?, " +
                "email = ?, " +
                "password = ?, " +
                "phone_number = ?" +
                "where id = ?";

        jdbcTemplate.update(sql,
                user.getName(),
                user.getSurname(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                id
        );
    }

    public void createUser(User user) {
        String sql = "insert into users (name, surname, age, email, password, phone_number, avatar, account_type)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                user.getName(),
                user.getSurname(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getAvatar(),
                user.getAccountType()
        );
    }
}
