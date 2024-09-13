package ru.yandex.practicum.filmorate.storage;

import java.util.List;
import java.util.Optional;

public interface BaseStorage<T> {

    T addElement(T element);

    T deleteElement(T element);

    T updateElement(T newElement);

    List<T> getAllElements();

    Optional<T> getElement(Integer id);

    T deleteElementById(Integer id);

}
