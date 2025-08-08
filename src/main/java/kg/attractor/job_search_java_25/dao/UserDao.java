package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.dao.mappers.UserMapper;
import kg.attractor.job_search_java_25.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
        String sql = "UPDATE users SET avatar = ? WHERE id = ?";
        jdbcTemplate.update(sql, avatar, userId);
    }

    public String getAvatarByUserId(Long userId) {
        String sql = "SELECT avatar FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, userId);
    }

    public User getMyProfile(Long auth) {
        String sql = "select * from users where id = ?";
        return jdbcTemplate.query(sql, new UserMapper(), auth)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public int editProfile(User user, Long id) {
        String sql = "update users " +
                "SET name = ?, " +
                "surname = ?, " +
                "age = ?, " +
                "email = ?, " +
                "password = ?, " +
                "phone_number = ?" +
                "where id = ?";

        return jdbcTemplate.update(sql,
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

    public Optional<Long> findUserIdByEmail(String email) {
        String sql = "select id from users where email = ?";
        return jdbcTemplate.query(sql, (rs, i) -> rs.getLong("id"), email)
                .stream()
                .findFirst();
    }


    public boolean existsByEmail(String email) {
        String sql = "select count(1) from users where email = ?";
        Integer c = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return c != null && c > 0;
    }
    public boolean existsByPhone(String phone) {
        String sql = "select count(1) from users where phone_number = ?";
        Integer c = jdbcTemplate.queryForObject(sql, Integer.class, phone);
        return c != null && c > 0;
    }

    public Optional<User> findByEmail(String email) {
        String sql = "select id, name, surname, age, email, password, phone_number, avatar, account_type " +
                "from users where email = ?";
        return jdbcTemplate.query(sql, new UserMapper(), email).stream().findFirst();
    }


}
