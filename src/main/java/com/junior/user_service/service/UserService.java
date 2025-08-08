package com.junior.user_service.service;

import com.junior.user_service.dto.LoginRequestDto;
import com.junior.user_service.dto.RegistrationRequestDto;
import com.junior.user_service.dto.UserDto;
import com.junior.user_service.dto.UserValidationResponse;

import java.util.List;

public interface UserService {
     UserDto register(RegistrationRequestDto requestDto);
     UserValidationResponse validate(LoginRequestDto requestDto);

     UserDto getUser(Long id);
     UserDto updateUser(Long id,UserDto userDto);
     String deleteUser(Long id);
     List<UserDto> getAllUser();
     UserDto searchUser(String userName);// we have to use now dsa here, and all stand things like pagination , elastic search capability, etc.


}
