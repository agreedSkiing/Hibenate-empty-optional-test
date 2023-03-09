DROP TABLE IF EXISTS test;

CREATE TABLE IF NOT EXISTS test (id BIGINT NOT NULL, field VARCHAR(10), uu UUID, PRIMARY KEY (id));

-- INSERT INTO test (id, field, uu) VALUES (1, 'test1', '40e6215d-b5c6-4896-987c-f30f3678f608'::uuid);
-- INSERT INTO test (id, field, uu) VALUES (2, 'test2', '6ecd8c99-4036-403d-bf84-cf8400f67836'::uuid);
INSERT INTO test (id, field, uu) VALUES (1, 'test1', '40e6215d-b5c6-4896-987c-f30f3678f608');
INSERT INTO test (id, field, uu) VALUES (2, 'test2', '6ecd8c99-4036-403d-bf84-cf8400f67836');
INSERT INTO test (id, field, uu) VALUES (3, 'test3', '1ecd8c19-4036-403d-bf84-cf8400f67836');
