package com.rentacar.model.adapters;

import com.rentacar.model.UserDTO;
import com.rentacar.repository.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter {

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .ID(user.getID())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public static User fromDTO (UserDTO userDTO) {
        return User.builder()
                .ID(userDTO.getID())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .build();
    }

    public static List<UserDTO> toListDTO (List<User> userList) {
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user : userList) {
            userDTOList.add(toDTO(user));
        }

        return userDTOList;
    }

    public static List<User> fromListDTO (List<UserDTO> userDTOList) {
        List<User> userList = new ArrayList<>();

        for (UserDTO userDTO : userDTOList) {
            userList.add(fromDTO(userDTO));
        }

        return userList;
    }
}
