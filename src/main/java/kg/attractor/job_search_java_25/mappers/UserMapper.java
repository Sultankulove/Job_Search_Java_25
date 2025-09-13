package kg.attractor.job_search_java_25.mappers;

import kg.attractor.job_search_java_25.dto.AccountType;
import kg.attractor.job_search_java_25.dto.UserProfileDto;
import kg.attractor.job_search_java_25.model.User;

import java.util.List;

public class UserMapper {
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
