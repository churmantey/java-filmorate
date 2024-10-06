package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;

public class FilmLikesComparator implements Comparator<Film> {

    @Override
    public int compare(Film o1, Film o2) {
        if (o1 != null && o2 != null) {
            return Integer.compare(o1.getLikes().size(), o2.getLikes().size());
        } else if (o1 == null && o2 != null) {
            return -1;
        } else if (o1 != null) {
            return 1;
        }
        return 0;
    }
}
