package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.RegistrationRequestDto;

public interface UserService {
    void registration(RegistrationRequestDto rrd);

    Long findUserIdByEmail(String name);
}
