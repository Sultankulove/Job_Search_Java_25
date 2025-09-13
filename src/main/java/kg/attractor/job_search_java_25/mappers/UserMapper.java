package kg.attractor.job_search_java_25.mappers;

import jakarta.persistence.*;
import kg.attractor.job_search_java_25.dto.userDtos.AccountType;
import kg.attractor.job_search_java_25.dto.userDtos.UserProfileDto;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.model.User;
import kg.attractor.job_search_java_25.model.Vacancy;

import java.util.List;

public class UserMapper {


    private String name;
    private String surname;
    private Byte age;
    private String email;
    private String phoneNumber;
    private String avatar;
    private String accountType;

    public static UserProfileDto toDto(User user) {
        if (user == null) return null;

        UserProfileDto dto = new UserProfileDto();
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setAge(user.getAge());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAvatar(user.getAvatar());
        dto.setAccountType(AccountType.valueOf(user.getAccountType()));
        return dto;
    }

    public static List<UserProfileDto> toDtoList(List<User> users) {
        return users.stream()
                .map(UserMapper::toDto)
                .toList();
    }
}
