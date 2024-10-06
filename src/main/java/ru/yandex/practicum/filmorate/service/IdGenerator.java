package ru.yandex.practicum.filmorate.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
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
