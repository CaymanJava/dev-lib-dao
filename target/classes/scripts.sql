CREATE TABLE author (
  id                SERIAL                   PRIMARY KEY,
  name              CHARACTER VARYING (255)  UNIQUE NOT NULL
);

CREATE TABLE publisher (
  id                SERIAL                   PRIMARY KEY,
  name              CHARACTER VARYING (255)  UNIQUE NOT NULL
);

CREATE TABLE category (
  id                SERIAL                   PRIMARY KEY,
  name              CHARACTER VARYING (255)  UNIQUE NOT NULL,
  image             CHARACTER VARYING (255)   DEFAULT NULL
);

CREATE TABLE rating (
  id                SERIAL                   PRIMARY KEY,
  votes             INTEGER					         DEFAULT 0,
  points      		  INTEGER					         DEFAULT 0
);

CREATE TABLE book (
  id                SERIAL                   PRIMARY KEY,
  name              CHARACTER VARYING (255)   NOT NULL,
  lang 	            CHARACTER VARYING (255)   NOT NULL,
  year              INTEGER                   NOT NULL,
  publisher_id      INTEGER			  references publisher(id),
  image             CHARACTER VARYING (255)   DEFAULT NULL,
  file_id    				CHARACTER VARYING (255)   DEFAULT NULL,
  description       CHARACTER VARYING			   DEFAULT '',
  category_id		    INTEGER 	     references category (id),
  page_count        INTEGER                  DEFAULT 0,
  rating_id   			INTEGER			 references rating (id),
  add_date	       TIMESTAMP                 DEFAULT now(),
  views             INTEGER                  DEFAULT 0
);

CREATE TABLE book_author (
  id                SERIAL                   PRIMARY KEY,
  book_id   		    INTEGER 	         references book (id),
  author_id		      INTEGER 	     references author (id)
);

CREATE TABLE vote (
  user_id           SERIAL                     NOT NULL,
  book_id   		    SERIAL 	                   NOT NULL,
  value   		      INTEGER 	               DEFAULT 0,
  date    	       TIMESTAMP                 DEFAULT now()
);

CREATE INDEX user_id_book_id_index
  ON public.vote USING btree
  (user_id ASC NULLS LAST, book_id ASC NULLS LAST)
TABLESPACE pg_default;



delete from book_author;
delete from author;
delete from book;
delete from category;
delete from publisher;
delete from rating;
delete from vote;