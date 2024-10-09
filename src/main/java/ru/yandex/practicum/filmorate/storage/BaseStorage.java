package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface BaseStorage<T> {

    T addElement(T element);

    boolean deleteElement(T element);

    T updateElement(T newElement);

    List<T> getAllElements();

    T getElement(Integer id);

    boolean deleteElementById(Integer id);

}
