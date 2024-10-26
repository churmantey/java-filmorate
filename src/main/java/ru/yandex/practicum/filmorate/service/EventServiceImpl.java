package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.EventOperation;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.storage.event.EventStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventStorage eventStorage;
    private final UserService userService;


    @Override
    public List<Event> getEvents(Integer userId) {
        UserDto user = userService.getUserById(userId);
        return eventStorage.getEvents(userId);
    }

    @Override
    public void createEvent(Integer userId, EventType type, EventOperation operation, Integer entityId) {
        eventStorage.createEvent(userId, type, operation, entityId);
    }
}
