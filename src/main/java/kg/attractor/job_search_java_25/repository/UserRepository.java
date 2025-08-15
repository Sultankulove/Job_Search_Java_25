package kg.attractor.job_search_java_25.repository;

import kg.attractor.job_search_java_25.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query("select u from User as u")
    List<User> getAllUsers();

    User getUserById(Long id);

    @Modifying
    @Query("update User u set u.avatar = :avatar where  u.id = :userId")
    void uploadAvatar(@Param("avatar") String avatar, @Param("userId") Long userId);

    @Query("select u.avatar from User u where u.id = :id")
    String getAvatarByUserId(@Param("id") Long userId);

//    @Query("select u from User u where u.id = :id")
//    User getMyProfile(@Param("id") Long auth);


    @Modifying
    @Query("""
            update User u
                        set u.name = :name,
                                    u.surname = :surname,
                                                u.age = :age,
                                                            u.email = :email,
                                                                        u.password = :password,
                                                                                    u.phoneNumber = :phoneNumber
                                                                                                where u.id = :id""")
    int editProfile(@Param("id") Long id,
                    @Param("name") String name,
                    @Param("surname") String surname,
                    @Param("age") Byte age,
                    @Param("email") String email,
                    @Param("password") String password,
                    @Param("phoneNumber") String phoneNumber);



    @Query("select u.id from User u where u.email = :email")
    Optional<Long> findUserIdByEmail(@Param("email") String email);

    boolean existsByEmail(String email);


    boolean existsByPhoneNumber(String phoneNumber);

}
