package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@ComponentScan("ru.yandex.practicum.filmorate")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDbStorageTests {
    private final UserDbStorage userStorage;
    private User user;
    private User user2;

    @BeforeEach
    public void setUp() {
        this.user = new User(
                "fff",
                "fff",
                "no@mail.ru",
                LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
        );
        this.user2 = new User(
                "ggg",
                "ggg",
                "not@mail.ru",
                LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
        );
    }


    @Test
    public void testInitialUsersCount() {
        List<User> userList = userStorage.getAllElements();
        assertThat(userList).hasSize(4);
        assertThat(userList.get(0)).hasFieldOrPropertyWithValue("login", "aaa");
        assertThat(userList.get(3)).hasFieldOrPropertyWithValue("login", "vbg");
    }

    @Test
    public void testFindUserById() {
        Optional<User> userOptional = Optional.ofNullable(userStorage.getElement(1));
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void testAddUser() {
        Integer currentId = user.getId();
        User addedUser = userStorage.addElement(user);
        assertThat(addedUser.getId()).isNotEqualTo(currentId);
        assertThat(addedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testUpdateUser() {
        User addedUser = userStorage.addElement(user);

        Integer id = addedUser.getId();
        String name = addedUser.getName();
        String email = addedUser.getEmail();

        addedUser.setEmail("updated@mail.ru");
        addedUser.setName("updated name");
        User updatedUser = userStorage.updateElement(addedUser);

        assertThat(updatedUser.getId()).isEqualTo(id);
        assertThat(updatedUser.getName())
                .isEqualTo("updated name")
                .isNotEqualTo(name);
        assertThat(updatedUser.getEmail())
                .isEqualTo("updated@mail.ru")
                .isNotEqualTo(email);
    }

    @Test
    public void testDeleteUser() {
        List<User> userList = userStorage.getAllElements();
        int initialUsersCount = userList.size();
        userStorage.addElement(user);
        List<User> newUserList = userStorage.getAllElements();
        int increasedUsersCount = newUserList.size();
        assertThat(increasedUsersCount).isEqualTo(initialUsersCount + 1);
        userStorage.deleteElementById(user.getId());
        assertThat(userStorage.getAllElements().size()).isEqualTo(initialUsersCount);
    }

    @Test
    public void testMutualFriends() {
        List<User> userList = userStorage.getMutualFriends(1, 3);
        assertThat(userList).hasSize(1);
        assertThat(userList.get(0).getId()).isEqualTo(2);
    }

    @Test
    public void testAddUserFriend() {
        user = userStorage.addElement(user);
        user2 = userStorage.addElement(user2);
        user = userStorage.addUserFriend(user.getId(), user2.getId());
        assertThat(userStorage.getUserFriends(user.getId())).hasSize(1);
        assertThat(userStorage.getUserFriends(user2.getId())).hasSize(0);
        user2 = userStorage.addUserFriend(user2.getId(), user.getId());
        assertThat(userStorage.getUserFriends(user2.getId())).hasSize(1);
    }

    @Test
    public void testRemoveUserFriend() {
        user = userStorage.addElement(user);
        user2 = userStorage.addElement(user2);
        user = userStorage.addUserFriend(user.getId(), user2.getId());
        assertThat(userStorage.getUserFriends(user.getId())).hasSize(1);
        user = userStorage.removeUserFriend(user.getId(), user2.getId());
        assertThat(userStorage.getUserFriends(user.getId())).hasSize(0);
    }


}
