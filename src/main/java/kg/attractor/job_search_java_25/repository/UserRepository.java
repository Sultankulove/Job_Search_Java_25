package kg.attractor.job_search_java_25.repository;

import kg.attractor.job_search_java_25.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    Optional<User> findByResetPasswordToken(String token);
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("update User u set u.avatar = :avatar where u.id = :userId")
    int saveAvatar(@Param("avatar") String avatar, @Param("userId") Long userId);

    @Query("select u.avatar from User u where u.id = :id")
    Optional<String> findAvatarPathById(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("""
        update User u
           set u.name        = :name,
               u.surname     = :surname,
               u.age         = :age,
               u.email       = :email,
               u.phoneNumber = :phoneNumber
         where u.id          = :id
    """)
    int editProfile(@Param("id") Long id,
                    @Param("name") String name,
                    @Param("surname") String surname,
                    @Param("age") Byte age,
                    @Param("email") String email,
                    @Param("phoneNumber") String phoneNumber);

    @Query("select u.id from User u where lower(u.email) = lower(:email)")
    Optional<Long> findUserIdByEmailIgnoreCase(@Param("email") String email);
}
