CREATE TABLE IF NOT EXISTS RATINGS (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME CHARACTER VARYING(16) NOT NULL,
	DESCRIPTION CHARACTER VARYING,
	CONSTRAINT RATINGS_PK PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS GENRES(
	ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME CHARACTER VARYING NOT NULL,
	CONSTRAINT GENRES_PK PRIMARY KEY (ID)
);


CREATE TABLE IF NOT EXISTS FILMS (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	TITLE CHARACTER VARYING NOT NULL,
	DESCRIPTION CHARACTER VARYING(200) NOT NULL,
	RELEASE_DATE DATE NOT NULL,
	DURATION INTEGER NOT NULL,
	RATING_ID INTEGER NOT NULL,
	CONSTRAINT FILMS_PK PRIMARY KEY (ID),
	CONSTRAINT FILMS_RATINGS_FK FOREIGN KEY (RATING_ID) REFERENCES RATINGS(ID) ON DELETE SET NULL
);


CREATE TABLE IF NOT EXISTS USERS  (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	LOGIN CHARACTER VARYING NOT NULL,
	NAME CHARACTER VARYING,
	EMAIL CHARACTER VARYING NOT NULL,
	BIRTHDAY DATE NOT NULL,
	CONSTRAINT USERS_PK PRIMARY KEY (ID)
);
create unique index if not exists USER_EMAIL_UINDEX on USERS (email);
create unique index if not exists USER_LOGIN_UINDEX on USERS (login);

CREATE TABLE IF NOT EXISTS USER_FRIENDS (
	USER_ID INTEGER NOT NULL,
	FRIEND_ID INTEGER NOT NULL,
	CONSTRAINT USER_FRIENDS_PK PRIMARY KEY (USER_ID,FRIEND_ID),
	CONSTRAINT USER_FRIENDS_USERS_FK_1 FOREIGN KEY (USER_ID) REFERENCES USERS(ID) ON DELETE CASCADE,
	CONSTRAINT USER_FRIENDS_USERS_FK_2 FOREIGN KEY (FRIEND_ID) REFERENCES USERS(ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS FILM_GENRES  (
	FILM_ID INTEGER NOT NULL,
	GENRE_ID INTEGER NOT NULL,
	CONSTRAINT FILM_GENRES_FILMS_FK FOREIGN KEY (FILM_ID) REFERENCES FILMS(ID) ON DELETE CASCADE,
	CONSTRAINT FILM_GENRES_GENRES_FK FOREIGN KEY (GENRE_ID) REFERENCES GENRES(ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS FILM_LIKES (
	FILM_ID INTEGER NOT NULL,
	USER_ID INTEGER NOT NULL,
	CONSTRAINT FILM_LIKES_FILMS_FK FOREIGN KEY (FILM_ID) REFERENCES FILMS(ID) ON DELETE CASCADE,
	CONSTRAINT FILM_LIKES_USERS_FK FOREIGN KEY (USER_ID) REFERENCES USERS(ID) ON DELETE CASCADE
);
