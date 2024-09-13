package ru.yandex.practicum.filmorate.service;

public class IdGenerator {

    private Integer id;

    public IdGenerator() {
        id = 0;
    }

    public Integer getNextId() {
        return ++id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
