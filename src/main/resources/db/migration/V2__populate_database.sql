-- Categories
INSERT INTO categories (name)
VALUES ('Programming Languages'),
       ('Frameworks'),
       ('Databases'),
       ('DevOps'),
       ('Soft Skills');

-- Knowledge
INSERT INTO knowledge (name, description)
VALUES ('Java', 'Object-oriented programming language'),
       ('Spring', 'Java-based framework for building enterprise applications'),
       ('MySQL', 'Open-source relational database management system'),
       ('Docker', 'Platform for developing, shipping, and running applications'),
       ('Communication', 'Ability to effectively convey and exchange information'),
       ('Python', 'High-level, interpreted programming language'),
       ('Git', 'Distributed version control system');

-- Categories-Knowledge relationships
INSERT INTO categories_knowledge (category_id, knowledge_id)
VALUES (1, 1), -- Programming Languages - Java
       (1, 6), -- Programming Languages - Python
       (2, 2), -- Frameworks - Spring
       (3, 3), -- Databases - MySQL
       (4, 4), -- DevOps - Docker
       (4, 7), -- DevOps - Git
       (5, 5);
-- Soft Skills - Communication

-- Profiles
INSERT INTO profiles (name)
VALUES ('Junior Developer'),
       ('Senior Developer'),
       ('DevOps Engineer'),
       ('Team Lead');

-- Profiles-Knowledge relationships
INSERT INTO profiles_knowledge (profile_id, knowledge_id, level)
VALUES (1, 1, 'BASIC'),    -- Junior Dev - Java Basic
       (1, 2, 'BASIC'),    -- Junior Dev - Spring Basic
       (2, 1, 'ADVANCED'), -- Senior Dev - Java Advanced
       (2, 2, 'ADVANCED'), -- Senior Dev - Spring Advanced
       (3, 4, 'EXPERT'),   -- DevOps - Docker Expert
       (4, 5, 'EXPERT');
-- Team Lead - Communication Expert

-- Users
INSERT INTO users (first_name, last_name, role)
VALUES ('John', 'Doe', 'USER'),
       ('Jane', 'Smith', 'ADMIN'),
       ('Mike', 'Johnson', 'USER'),
       ('Sarah', 'Williams', 'USER');

-- Users-Knowledge relationships
INSERT INTO users_knowledge (user_id, knowledge_id, level)
VALUES (1, 1, 'INTERMEDIATE'), -- John - Java Intermediate
       (1, 2, 'BASIC'),        -- John - Spring Basic
       (2, 1, 'EXPERT'),       -- Jane - Java Expert
       (2, 3, 'ADVANCED'),     -- Jane - MySQL Advanced
       (3, 4, 'INTERMEDIATE'), -- Mike - Docker Intermediate
       (4, 5, 'EXPERT'); -- Sarah - Communication Expert