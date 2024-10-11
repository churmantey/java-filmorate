package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.BaseDbStorage;
import ru.yandex.practicum.filmorate.storage.BaseStorage;

import java.util.List;

@Repository
@Qualifier("userDbStorage")
public class UserDbStorage extends BaseDbStorage<User> implements BaseStorage<User> {

    public UserDbStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public User addElement(User element) {
        return null;
    }

    @Override
    public boolean deleteElement(User element) {
        return false;
    }

    @Override
    public User updateElement(User newElement) {
        return null;
    }

    @Override
    public List<User> getAllElements() {
        return null;
    }

    @Override
    public User getElement(Integer id) {
        return null;
    }

    @Override
    public boolean deleteElementById(Integer id) {
        return false;
    }
}
