CREATE TABLE categories
(
    id   TINYINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT uk_category_name UNIQUE (name)
);

CREATE TABLE knowledge
(
    id          SMALLINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL,
    description TEXT,
    CONSTRAINT uk_knowledge_name UNIQUE (name)
);

CREATE TABLE profiles
(
    id   SMALLINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE users
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    role       VARCHAR(30) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE categories_knowledge
(
    category_id  TINYINT  NOT NULL,
    knowledge_id SMALLINT NOT NULL,
    PRIMARY KEY (category_id, knowledge_id),
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE,
    FOREIGN KEY (knowledge_id) REFERENCES knowledge (id) ON DELETE CASCADE
);

CREATE TABLE profiles_knowledge
(
    profile_id SMALLINT NOT NULL,
    knowledge_id SMALLINT NOT NULL,
    level VARCHAR(20) NOT NULL,
    PRIMARY KEY (profile_id, knowledge_id),
    FOREIGN KEY (profile_id) REFERENCES profiles (id),
    FOREIGN KEY (knowledge_id) REFERENCES knowledge (id)
);

CREATE TABLE users_knowledge
(
    user_id BIGINT NOT NULL,
    knowledge_id SMALLINT NOT NULL,
    level VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_id, knowledge_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (knowledge_id) REFERENCES knowledge (id)
);