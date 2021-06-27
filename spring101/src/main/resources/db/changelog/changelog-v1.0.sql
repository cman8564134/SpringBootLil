--liquibase formatted sql

--changeset cthye:v1.0-1
--comment: Create Room table
CREATE TABLE ROOM
(
    ROOM_ID     SERIAL PRIMARY KEY,
    NAME        VARCHAR(16) NOT NULL,
    ROOM_NUMBER CHAR(2)     NOT NULL UNIQUE,
    BED_INFO    CHAR(2)     NOT NULL
);
--rollback drop table ROOM;

--changeset cthye:v1.0-2
--comment: Create Guest table
CREATE TABLE GUEST
(
    GUEST_ID      SERIAL PRIMARY KEY,
    FIRST_NAME    VARCHAR(64),
    LAST_NAME     VARCHAR(64),
    EMAIL_ADDRESS VARCHAR(64),
    ADDRESS       VARCHAR(64),
    COUNTRY       VARCHAR(32),
    STATE         VARCHAR(12),
    PHONE_NUMBER  VARCHAR(24)
);
--rollback drop table GUEST;

--changeset cthye:v1.0-3
--comment: Create Reservation table
CREATE TABLE RESERVATION
(
    RESERVATION_ID SERIAL PRIMARY KEY,
    ROOM_ID        BIGINT NOT NULL,
    GUEST_ID       BIGINT NOT NULL,
    RES_DATE       DATE
);
--rollback drop table RESERVATION;

--changeset cthye:v1.0-4 rollbackEndDelimiter:;;
--comment: Create FK key constraint for reservation table
--preconditions onFail:HALT onError:HALT
--precondition-sql-check expectedResult:t SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_schema = 'public' AND table_name   = 'reservation');
ALTER TABLE RESERVATION
    ADD FOREIGN KEY (ROOM_ID) REFERENCES ROOM (ROOM_ID);
ALTER TABLE RESERVATION
    ADD FOREIGN KEY (GUEST_ID) REFERENCES GUEST (GUEST_ID);
--rollback ALTER TABLE RESERVATION DROP CONSTRAINT reservation_room_id_fkey; ALTER TABLE RESERVATION DROP CONSTRAINT reservation_guest_id_fkey;;

--changeset cthye:v1.0-5
--comment: create index for reservation table
--preconditions onFail:HALT onError:HALT
--precondition-sql-check expectedResult:t SELECT EXISTS (SELECT FROM information_schema.tables WHERE  table_schema = 'public'   AND    table_name   = 'reservation'     );
CREATE INDEX IDX_RES_DATE_ ON RESERVATION (RES_DATE);
--rollback ALTER TABLE RESERVATION DROP INDEX IDX_RES_DATE_;

--changeset cthye:v1.0-6
--comment: Create Role table
CREATE TABLE security_role (
                               id SERIAL PRIMARY KEY,
                               description varchar(100) DEFAULT NULL,
                               role_name varchar(100) DEFAULT NULL);
--rollback drop table security_role;

--changeset cthye:v1.0-7
--comment: Create user table
CREATE TABLE security_user (
                               id SERIAL PRIMARY KEY,
                               username varchar(255) NOT NULL,
                               password varchar(255) NOT NULL,
                               first_name varchar(255) DEFAULT NULL,
                               last_name varchar(255) NOT NULL
);
--rollback drop table security_user;

--changeset cthye:v1.0-8
--comment: Create user-role table
--preconditions onFail:HALT onError:HALT
--precondition-sql-check expectedResult:t SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_schema = 'public' AND table_name   = 'security_user');
--precondition-sql-check expectedResult:t SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_schema = 'public' AND table_name   = 'security_role');
CREATE TABLE user_role (
                           user_id BIGINT NOT NULL,
                           role_id BIGINT NOT NULL,
                           CONSTRAINT FK_SECURITY_USER_ID FOREIGN KEY (user_id) REFERENCES security_user (id),
                           CONSTRAINT FK_SECURITY_ROLE_ID FOREIGN KEY (role_id) REFERENCES security_role (id)
);
--rollback drop table user_role;

