# voting_q schema

# --- !Ups

CREATE TABLE elections
(
    id              bigint       NOT NULL PRIMARY KEY,
    name            varchar(50)  NOT NULL,
    day             date         NOT NULL,
    ocd_division_id varchar(200) NOT NULL,
    expired         boolean      NOT NULL DEFAULT (false)
);

CREATE TABLE centers
(
    id                    serial       NOT NULL PRIMARY KEY,
    name                  varchar(150) NOT NULL,
    street1               varchar(100) NOT NULL,
    street2               varchar(100) NULL,
    city                  varchar(50)  NOT NULL,
    state                 varchar(2)   NOT NULL,
    zip                   varchar(5)   NOT NULL,
    current_election_id   bigint       NULL,
    current_polling_hours varchar(75)  NULL,
    FOREIGN KEY (current_election_id) REFERENCES elections (id)
);

CREATE TABLE queues
(
    id          serial    NOT NULL PRIMARY KEY,
    election_id bigint    NOT NULL,
    center_id   bigint    NOT NULL,
    start       timestamp NOT NULL,
    finish      timestamp NOT NULL,
    FOREIGN KEY (center_id) REFERENCES centers (id),
    FOREIGN KEY (election_id) REFERENCES elections (id)
);

CREATE TABLE voters
(
    id       serial       NOT NULL PRIMARY KEY,
    email    varchar(254) NOT NULL,
    verified boolean      NOT NULL DEFAULT (true)
);

CREATE TABLE queue_groups
(
    id            serial    NOT NULL PRIMARY KEY,
    election_id   bigint    NOT NULL,
    queue_id      bigint    NOT NULL,
    center_id     bigint    NOT NULL,
    position      int       NULL,
    queue_entered timestamp NULL,
    queue_left    timestamp NULL,
    FOREIGN KEY (election_id) REFERENCES elections (id),
    FOREIGN KEY (queue_id) REFERENCES queues (id),
    FOREIGN KEY (center_id) REFERENCES centers (id)
);

CREATE TABLE group_voters
(
    queue_group_id bigint NOT NULL,
    voter_id       bigint NOT NULL,
    FOREIGN KEY (queue_group_id) REFERENCES queue_groups (id),
    FOREIGN KEY (voter_id) REFERENCES voters (id)
)
    # --- !Downs

DROP TABLE centers;
DROP TABLE elections;
DROP TABLE queues;
DROP TABLE voters;
DROP TABLE queue_groups;
DROP TABLE group_voters;
