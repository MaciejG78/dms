INSERT INTO DOCUMENT (number, content, title, status, creator_id, created_at)
VALUES('0', 'auta fancy sportowe', 'autka', 'DRAFT', '1', TIMESTAMP '2017-01-01 10:44:00');
INSERT INTO DOCUMENT (number, content, title, status, creator_id, created_at)
VALUES('1', 'auta cabrio', 'b fancy autka', 'ARCHIVED', '2', TIMESTAMP '2017-01-01 10:50:00');
INSERT INTO DOCUMENT (number, content, title, status, creator_id, created_at)
VALUES('fancy', 'auta sportowe', 'c sport', 'DRAFT', '3', TIMESTAMP '2017-01-02 10:44:00');
INSERT INTO DOCUMENT (number, content, title, status, creator_id, created_at)
VALUES('3', 'auta sportowe', 'd sport', 'DRAFT', '1', TIMESTAMP '2017-01-06 10:50:00');
INSERT INTO DOCUMENT (number, content, title, status, creator_id, created_at, editor_id, changed_at, publisher_id,
published_at, verifier_id, verified_at)
VALUES('5', 'auta sportowe', 'e AUDI', 'PUBLISHED', '1', TIMESTAMP '2017-01-05 10:44:00'
, '1', TIMESTAMP '2017-02-05 10:44:00', '2', TIMESTAMP '2017-02-05 10:44:00', '2', TIMESTAMP '2017-03-05 10:44:00');