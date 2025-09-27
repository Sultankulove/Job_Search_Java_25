CREATE TABLE publications
(
    id          BIGINT auto_increment PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    content     TEXT         NOT NULL,
    cover       VARCHAR(255),
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP,
    author_id   BIGINT       NOT NULL REFERENCES users (id),
    category_id BIGINT       NOT NULL REFERENCES categories (id)
);

CREATE INDEX idx_publications_category ON publications (category_id);
CREATE INDEX idx_publications_author ON publications (author_id);
CREATE INDEX idx_publications_deleted ON publications (deleted_at);

CREATE TABLE publication_comments
(
    id                   bigint auto_increment PRIMARY KEY,
    publication_id       BIGINT    NOT NULL REFERENCES publications (id) ON DELETE CASCADE,
    author_id            BIGINT    NOT NULL REFERENCES users (id),
    content              TEXT      NOT NULL,
    created_at           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at           TIMESTAMP,
    deleted_by_moderator BOOLEAN   NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_publication_comments_publication ON publication_comments (publication_id);
CREATE INDEX idx_publication_comments_author ON publication_comments (author_id);
CREATE INDEX idx_publication_comments_deleted ON publication_comments (deleted_at);

INSERT INTO publications (title, content, author_id, category_id)
VALUES ('Как подготовиться к собеседованию', 'Поделюсь личным чек-листом подготовки к техническому собеседованию.', 1,
        1),
       ('Советы по составлению резюме',
        'Рассказываю о том, как выделиться в потоке резюме и не забыть про ключевые блоки.', 2, 2);

INSERT INTO publication_comments (publication_id, author_id, content)
VALUES (1, 2, 'Спасибо за советы!'),
       (1, 3, 'Очень полезно.'),
       (2, 1, 'Берите на вооружение, коллеги.');
