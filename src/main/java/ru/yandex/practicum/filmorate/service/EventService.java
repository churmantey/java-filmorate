package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.EventOperation;
import ru.yandex.practicum.filmorate.model.EventType;

import java.util.List;

public interface EventService {
    List<Event> getEvents(Integer userId);

    void createEvent(Integer userId, EventType type, EventOperation operation, Integer entityId);
}
