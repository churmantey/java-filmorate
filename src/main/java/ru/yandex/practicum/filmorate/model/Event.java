package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private Integer eventId;
    private long timestamp;
    private Integer userId;
    private Integer entityId;
    private EventOperation operation;
    private EventType eventType;
}
