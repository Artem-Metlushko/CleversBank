DROP TABLE IF EXISTS "account";
DROP TABLE IF EXISTS "user";
CREATE TABLE IF NOT EXISTS "user"
(
    user_id     VARCHAR(255) PRIMARY KEY,
    firstName   VARCHAR(255) NOT NULL,
    lastName    VARCHAR(255) NOT NULL,
    surName     VARCHAR(255) NOT NULL,
    address     VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(255) NOT NULL
);
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('81ade06e-4402-11ee-be56-0242ac120002', 'Ivan', 'Ivanov', 'Ivanov', 'Minsk', '555-4819');

DROP TABLE IF EXISTS "bank";
CREATE TABLE IF NOT EXISTS "bank"
(
    bank_id VARCHAR(255) PRIMARY KEY,
    name    VARCHAR(255) NOT NULL
);

INSERT INTO "bank" (bank_id, name)
VALUES ('8cf724a3-5ebe-47fe-ae8b-6f729df5f15a', 'Minsk');
INSERT INTO "bank" (bank_id, name)
VALUES ('f7680199-c0b4-4b46-bb04-7835f0737ae3', 'Petrov');

DROP TABLE IF EXISTS "account";
CREATE TABLE IF NOT EXISTS "account"
(
    account_id   VARCHAR(255) PRIMARY KEY,
    opening_date TIMESTAMP      NOT NULL,
    user_id      VARCHAR(255)   NOT NULL,
    bank_id      VARCHAR(255)   NOT NULL,
    balance      NUMERIC(18, 2) NOT NULL,
    FOREIGN KEY (bank_id) REFERENCES bank (bank_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES "user" (user_id) ON DELETE CASCADE
);


INSERT INTO "account" (account_id, opening_date, user_id, bank_id, balance)
VALUES ('a1e86e08-4402-11ee-be56-0242ac120002', '2023-08-27 10:00:00', '81ade06e-4402-11ee-be56-0242ac120002', '8cf724a3-5ebe-47fe-ae8b-6f729df5f15a', 1500.00);
INSERT INTO "account" (account_id, opening_date, user_id, bank_id, balance)
VALUES ('b1e86e08-4402-11ee-be56-0242ac120003', '2023-08-28 14:30:00', 'a87db4e1-5093-4b41-ad02-4a055a871a98', 'f7680199-c0b4-4b46-bb04-7835f0737ae3', 2300.50);






