package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.BaseDbStorage;

import java.util.List;

@Repository
@Qualifier("userDbStorage")
public class UserDbStorage extends BaseDbStorage<User> implements UserStorage {
    private static final String tableName = "users";
    private static final String fields = "id, login, name, email, birthday";
    private static final String FIND_ALL_QUERY = "SELECT " + fields + " from " + tableName;
    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE id = ?";
    private static final String FIND_FRIENDS_QUERY = FIND_ALL_QUERY + " WHERE id IN " +
            "(SELECT uf.friend_id FROM user_friends uf WHERE uf.user_id = ?)";
    private static final String FIND_MUTUAL_FRIENDS_QUERY = FIND_ALL_QUERY +
            " WHERE id IN (SELECT uf.friend_id FROM user_friends uf WHERE uf.user_id = ?) " +
            " AND id IN (SELECT uf.friend_id FROM user_friends uf WHERE uf.user_id = ?)";
    private static final String INSERT_QUERY = "INSERT INTO " + tableName +
            " (login, name, email, birthday) " +
            " VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE " + tableName + " SET " +
            "login = ?, name = ?, email = ?, birthday = ? WHERE id = ?";
    private static final String ADD_USER_FRIEND_QUERY = "INSERT INTO user_friends (user_id, friend_id) " +
            "VALUES (?, ?)";
    private static final String REMOVE_USER_FRIEND_QUERY = "DELETE FROM user_friends " +
            "WHERE (user_id = ? AND friend_id = ?)";
    private static final String DELETE_FRIENDS_QUERY = "DELETE FROM user_friends WHERE (user_id = ? OR friend_id = ?)";
    private static final String DELETE_LIKES_QUERY = "DELETE FROM film_likes WHERE user_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM " + tableName + " WHERE id = ?";


    public UserDbStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public User addElement(User element) {
        Integer newId = insert(INSERT_QUERY,
                element.getLogin(),
                element.getName(),
                element.getEmail(),
                element.getBirthday().format(DATE_FORMATTER)
        );
        element.setId(newId);
        return element;
    }

    @Override
    public boolean deleteElement(User user) {
        deleteFriendsAndLikes(user.getId());
        return delete(DELETE_QUERY, user.getId());
    }

    @Override
    public User updateElement(User newElement) {
        update(UPDATE_QUERY,
                newElement.getLogin(),
                newElement.getName(),
                newElement.getEmail(),
                newElement.getBirthday().format(DATE_FORMATTER),
                newElement.getId());

        return newElement;
    }

    @Override
    public List<User> getAllElements() {
        List<User> baseList = findMany(FIND_ALL_QUERY);
        baseList.forEach(this::setUserFriends);
        return baseList;
    }

    @Override
    public User getElement(Integer id) {
        User user = findOne(FIND_BY_ID_QUERY, id)
                .orElseThrow(() -> new NotFoundException("Не найден пользователь с id = " + id));
        setUserFriends(user);
        return user;
    }

    @Override
    public boolean deleteElementById(Integer id) {
        deleteFriendsAndLikes(id);
        return delete(DELETE_QUERY, id);
    }

    // Заполняет коллекцию друзей в объекте user
    private void setUserFriends(User user) {
        user.getFriends().
                addAll(
                        findMany(FIND_FRIENDS_QUERY, user.getId())
                );
    }

    //
    private void deleteFriendsAndLikes(Integer userId) {
        delete(DELETE_FRIENDS_QUERY, userId, userId);
        delete(DELETE_LIKES_QUERY, userId);
    }

    @Override
    public List<User> getMutualFriends(Integer userId, Integer otherUserId) {
        List<User> baseList = findMany(FIND_MUTUAL_FRIENDS_QUERY, userId, otherUserId);
        baseList.forEach(this::setUserFriends);
        return baseList;
    }

    @Override
    public User addUserFriend(Integer userId, Integer friendId) {
        update(ADD_USER_FRIEND_QUERY, userId, friendId);
        return getElement(userId);
    }

    @Override
    public User removeUserFriend(Integer userId, Integer friendId) {
        update(REMOVE_USER_FRIEND_QUERY, userId, friendId);
        return getElement(userId);
    }
}
