INSERT INTO DOCUMENT (NUMBER, CONTENT, TITLE, STATUS, CREATOR_ID, CREATED_AT, DOCUMENT_TYPE)
VALUES ('0', 'test fancy content0', 'z test title0', 'DRAFT', 1, TIMESTAMP '2017-01-01 10:44:00', 'MANUAL');

INSERT INTO DOCUMENT (NUMBER, CONTENT, TITLE, STATUS, CREATOR_ID, CREATED_AT, DOCUMENT_TYPE)
VALUES ('1', 'test content0', 'test fancy title1', 'ARCHIVED', 1, TIMESTAMP '2017-01-01 10:50:00', 'MANUAL');

INSERT INTO DOCUMENT (number, content, title, status, creator_id, created_at, DOCUMENT_TYPE)
VALUES('2', 'auta cabrio', 'b fancy autka', 'VERIFIED', '2', TIMESTAMP '2017-01-01 10:50:00', 'MANUAL');

INSERT INTO DOCUMENT (NUMBER, CONTENT, TITLE, STATUS, CREATOR_ID, CREATED_AT, DOCUMENT_TYPE)
VALUES ('fancy', 'test content1', 'test title0', 'DRAFT', 2, TIMESTAMP '2017-01-02 10:44:00', 'MANUAL');

INSERT INTO DOCUMENT (NUMBER, CONTENT, TITLE, STATUS, CREATOR_ID, CREATED_AT, DOCUMENT_TYPE)
VALUES ('3', 'test content1', 'test title1', 'DRAFT', 2, TIMESTAMP '2017-01-05 10:44:00', 'MANUAL');

INSERT INTO DOCUMENT (number, content, title, status, creator_id, created_at, editor_id, changed_at, publisher_id,
published_at, verifier_id, verified_at, DOCUMENT_TYPE)
VALUES('5', 'auta sportowe', 'e AUDI', 'PUBLISHED', '1', TIMESTAMP '2017-01-07 10:44:00'
, '1', TIMESTAMP '2017-02-05 10:44:00', '2', TIMESTAMP '2017-02-05 10:44:00', '2', TIMESTAMP '2017-03-05 10:44:00', 'MANUAL');