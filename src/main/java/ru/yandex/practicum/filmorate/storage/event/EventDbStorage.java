package ru.yandex.practicum.filmorate.storage.event;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.EventOperation;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.storage.BaseDbStorage;
import ru.yandex.practicum.filmorate.storage.mapper.EventRowMapper;

import java.time.Instant;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventDbStorage implements EventStorage{

    private final JdbcTemplate jdbcTemplate;
    private final EventRowMapper rowMapper;
    private static final String SELECT_EVENTS = "SELECT * FROM events WHERE user_id = ?";
    private static final String INSERT_EVENT = "INSERT INTO events " +
            "(user_id, timestamp, event_type, operation, entity_id) VALUES (?, ?, ?, ?, ?)";

    @Override
    public List<Event> getEvents(Integer userId) {
        return jdbcTemplate.query(SELECT_EVENTS, rowMapper, userId);
    }

    @Override
    public void createEvent(Integer userId, EventType type, EventOperation operation, Integer entityId) {
        jdbcTemplate.update(INSERT_EVENT,
                userId,
                Instant.now().toEpochMilli(),
                type.name(),
                operation.name(),
                entityId
        );
    }
}
