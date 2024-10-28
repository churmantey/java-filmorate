DELETE FROM FILM_LIKES;
DELETE FROM FILM_GENRES;
DELETE FROM USER_FRIENDS;
DELETE FROM USERS;
ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 1;
DELETE FROM FILMS;
ALTER TABLE FILMS ALTER COLUMN ID RESTART WITH 1;
DELETE FROM GENRES;
ALTER TABLE GENRES ALTER COLUMN ID RESTART WITH 1;
DELETE FROM RATINGS;
ALTER TABLE RATINGS ALTER COLUMN ID RESTART WITH 1;

INSERT INTO GENRES (name) VALUES
('Комедия'),
('Драма'),
('Мультфильм'),
('Триллер'),
('Документальный'),
('Боевик');

INSERT INTO RATINGS (NAME, DESCRIPTION) VALUES
('G', 'у фильма нет возрастных ограничений'),
('PG', 'детям рекомендуется смотреть фильм с родителями'),
('PG-13', 'детям до 13 лет просмотр не желателен'),
('R', 'лицам до 17 лет просматривать фильм можно только в присутствии взрослого'),
('NC-17', 'лицам до 18 лет просмотр запрещён.');
    
INSERT INTO USERS (LOGIN, NAME, EMAIL, BIRTHDAY) VALUES
('aaa', 'Nevstruev J. P.', 'yesterday@niicha.vo', '1965-03-04'),
('hnt', 'Hunta K. H.', 'hunta@niicha.vo', '1905-06-07'),
('kvr', 'Kivrin F. S.', 'kivrin@niicha.vo', '1935-08-09'),
('vbg', 'Vybegallo Prof', 'vybegallo@niicha.vo', '1951-08-09');

INSERT INTO FILMS (TITLE , DESCRIPTION, RELEASE_DATE, DURATION, RATING_ID) VALUES
('Хищник', 'С Арнольдом', '1983-01-01', 100, 2),
('Близнецы', 'С Арнольдом и Де Вито', '1984-01-01', 102, 3),
('Чародеи', 'Гафт, Фарада', '1985-02-01', 100, 1),
('Король Лев', 'Дисней', '1996-03-01', 100, 1);

INSERT INTO FILM_GENRES (FILM_ID, GENRE_ID) VALUES
(1, 4),
(1, 6),
(2, 1),
(2, 6),
(4, 3);

INSERT INTO FILM_LIKES (FILM_ID, USER_ID) VALUES
(1, 1),
(1, 2),
(3, 3);

INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES
(1,3),
(1,2),
(3,2),
(3,1);
