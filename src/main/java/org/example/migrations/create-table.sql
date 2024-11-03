CREATE TABLE users(
	id int PRIMARY KEY AUTO_INCREMENT,
   	name varchar(255) not null,
    username varchar(255) UNIQUE,
    password text
);
CREATE TABLE tasks(
	id int PRIMARY KEY AUTO_INCREMENT,
    content text not null,
    status boolean DEFAULT false,
    userId int,
    FOREIGN KEY (userId) REFERENCES users(id)
)