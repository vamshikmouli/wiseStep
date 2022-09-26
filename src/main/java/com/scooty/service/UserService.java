package com.scooty.service;

import com.scooty.exceptions.UserAlreadyExistsException;
import com.scooty.exceptions.UserNotFoundException;
import com.scooty.models.User;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    Map<Long, User> userMap = new HashMap<>();
    public void addUser(User user) throws UserAlreadyExistsException {
        if(userMap.containsKey(user.getId())){
            throw new UserAlreadyExistsException();
        }
        userMap.put(user.getId(), user);
    }

    public User getUser(@NonNull final Long userId) {
        if (!userMap.containsKey(userId)) {
            throw new UserNotFoundException();
        }
        return userMap.get(userId);
    }
}
