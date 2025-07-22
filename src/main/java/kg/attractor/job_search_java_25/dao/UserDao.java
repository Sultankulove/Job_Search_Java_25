package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.dao.mappers.UserMapper;
import kg.attractor.job_search_java_25.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return namedParameterJdbcTemplate.query(sql, new UserMapper());
    }


    public User findByName(String name) {
        String sql = "select * from users where name = :name";
        return namedParameterJdbcTemplate.queryForObject(sql,
                Map.of("name", name), new UserMapper());
    }

    public User findByPhoneNumber(String phoneNumber) {
        String sql = "select * from users where phone_number = :phoneNumber";
        return namedParameterJdbcTemplate.queryForObject(sql,
                Map.of("phoneNumber", phoneNumber), new UserMapper());
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = :email";
        return namedParameterJdbcTemplate.queryForObject(sql, Map.of("email", email), new UserMapper());
    }


    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = :email";
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, Map.of("email", email), Integer.class);
        return count != null && count > 0;
    }

    public void save(User user) {
        String sql = "INSERT INTO users (name, surname, age, email, password, phone_number, avatar, account_type) VALUES (:name, :surname, :age, :email, :password, :phone, :avatar, :type)";
        Map<String, Object> params = Map.of(
                "name", user.getName(),
                "surname", user.getSurname(),
                "age", user.getAge(),
                "email", user.getEmail(),
                "password", user.getPassword(),
                "phone_number", user.getPhoneNumber(),
                "avatar", user.getAvatar(),
                "account_type", user.getAccountType()
        );
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = :id";
        namedParameterJdbcTemplate.update(sql, Map.of("id", id));
    }
}
