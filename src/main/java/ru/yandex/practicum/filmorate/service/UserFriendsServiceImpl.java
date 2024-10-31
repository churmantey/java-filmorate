package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.dto.mapper.UserMapperStruct;
import ru.yandex.practicum.filmorate.model.EventOperation;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFriendsServiceImpl implements UserFriendsService {

    private final UserStorage userStorage;
    private final EventService eventService;
    private final UserMapperStruct userMapperStruct;

    @Override
    public List<UserDto> getCommonFriends(Integer userId, Integer otherUserId) {
        List<User> uLst = userStorage.getMutualFriends(userId, otherUserId);
        log.info("Mutual friends found - {}", uLst);
        return userMapperStruct.map(uLst);
    }

    @Override
    public List<UserDto> getUserFriends(Integer userId) {
        User user = userStorage.getElement(userId);
        log.info("Searching for friends of user with id = {} ", userId);
        return userMapperStruct.map(userStorage.getUserFriends(userId));
    }

    @Override
    public UserDto addFriend(Integer userId, Integer friendId) {
        User user = userStorage.getElement(userId);
        User friend = userStorage.getElement(friendId);
        log.info("Creating an event - adding friends {} and {} ", userId, friendId);
        eventService.createEvent(userId, EventType.FRIEND, EventOperation.ADD, friendId);
        return userMapperStruct.mapUserToUserDto(userStorage.addUserFriend(userId, friendId));
    }

    @Override
    public UserDto removeFriend(Integer userId, Integer friendId) {
        User user = userStorage.getElement(userId);
        User friend = userStorage.getElement(friendId);
        log.info("Creating an event - removing friend {} from {} ", userId, friendId);
        eventService.createEvent(userId, EventType.FRIEND, EventOperation.REMOVE, friendId);
        userStorage.removeUserFriend(userId, friendId);
        return userMapperStruct.mapUserToUserDto(user);
    }

}
