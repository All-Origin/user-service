package com.brainz.user_service.service;

import com.brainz.user_service.dto.LoginRequestDto;
import com.brainz.user_service.dto.RegistrationRequestDto;
import com.brainz.user_service.dto.UserDto;
import com.brainz.user_service.dto.UserValidationResponse;

public interface UserService {
     UserDto register(RegistrationRequestDto requestDto);
     UserValidationResponse validate(LoginRequestDto requestDto);
}
