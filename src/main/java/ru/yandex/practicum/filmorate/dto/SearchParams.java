package ru.yandex.practicum.filmorate.dto;

import lombok.Data;

@Data
public class SearchParams {

    private final String query;
    private final boolean isNeedDirector;
    private final boolean isNeedTitle;

    public SearchParams(String query, String inputParam) {
        this.query = query;
        this.isNeedDirector = inputParam.contains("director");
        this.isNeedTitle = inputParam.contains("title");
    }
}

