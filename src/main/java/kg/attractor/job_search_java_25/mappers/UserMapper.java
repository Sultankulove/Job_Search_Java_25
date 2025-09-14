package kg.attractor.job_search_java_25.mappers;

import kg.attractor.job_search_java_25.dto.userDtos.AccountType;
import kg.attractor.job_search_java_25.dto.userDtos.EditProfileDto;
import kg.attractor.job_search_java_25.dto.userDtos.UserProfileDto;
import kg.attractor.job_search_java_25.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public UserProfileDto toProfile(User u) {
        if (u == null) return null;
        var dto = new UserProfileDto();
        dto.setName(u.getName());
        dto.setSurname(u.getSurname());
        dto.setAge(u.getAge());
        dto.setEmail(u.getEmail());
        dto.setPhoneNumber(u.getPhoneNumber());
        dto.setAvatar(u.getAvatar());
        dto.setAccountType(AccountType.valueOf(u.getAccountType()));
        return dto;
    }

    public void applyEdit(EditProfileDto d, User u) {
        u.setName(d.getName());
        u.setSurname(d.getSurname());
        u.setAge(d.getAge());
        u.setEmail(d.getEmail());
        u.setPhoneNumber(d.getPhoneNumber());
    }
}
