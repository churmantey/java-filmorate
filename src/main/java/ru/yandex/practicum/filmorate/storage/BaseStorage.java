package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface BaseStorage<T> {

    T addElement(T element);

    T deleteElement(T element);

    T updateElement(T newElement);

    List<T> getAllElements();

    T getElement(Integer id);

    T deleteElementById(Integer id);

}
