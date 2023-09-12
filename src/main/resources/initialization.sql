DROP TABLE IF EXISTS "transaction";
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
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('a87db4e1-5093-4b41-ad02-4a055a871a98', 'Petr', 'Petrov', 'Petrov', 'Minsk', '555-9465');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('66fd3eeb-405a-4628-a55e-d32fe1470497', 'Sergey', 'Sergeev', 'Sergeev', 'Minsk', '555-0471');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('7bd1f9fe-21ab-49ac-a461-d9ea85b987d3', 'Valery', 'Valeryev', 'Valeryev', 'Valeryev', '555-5076');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('357df1d3-9970-4516-985f-d640665f2ea5', 'Yurik', 'Yuriev', 'Yuriev', 'Ramensk', '555-3013');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('43c17b1d-853a-4b8f-b18f-6218b8a3ac2c', 'Alex', 'Smith', 'Johnson', 'NewYork', '555-1234');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('eb218a34-e36d-4a22-9b57-b8a57d6289dd', 'Emily', 'Davis', 'Wilson', 'LosAngeles', '555-9876');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('59c5eefc-99d0-4736-90e7-9356db22e4da', 'Sophia', 'Brown', 'Miller', 'Chicago', '555-4321');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('7161a7d9-6b67-4081-8b31-53f12d83030f', 'William', 'Martinez', 'Garcia', 'Houston', '555-7890');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('e1340e84-835d-4c63-994b-b1ff8b78d2c7', 'Olivia', 'Johnson', 'Jones', 'Phoenix', '555-2345');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('c543ff0f-b13c-4f75-97b9-ef4cc9c59ea2', 'Liam', 'Taylor', 'Brown', 'Dallas', '555-5678');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('f7b60026-9135-4b5e-925d-60b43b68a97d', 'Emma', 'Hernandez', 'Lee', 'SanFrancisco', '555-8765');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('8ef5b09f-24c7-4c8e-8605-41479cc700a7', 'Noah', 'Gonzalez', 'Davis', 'Miami', '555-3456');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('79a1353c-bc52-4e1e-a47c-431b33eb7b2b', 'Ava', 'Rodriguez', 'Miller', 'Denver', '555-6543');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('aa14d7eb-381b-45b6-9773-24c881f8c794', 'James', 'Lee', 'Jackson', 'Seattle', '555-9876');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('74f255f1-89ce-4dcb-9edf-93d573d7c3f9', 'Isabella', 'White', 'Harris', 'Atlanta', '555-1234');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('fb5f79e5-5399-45a3-b42a-09c6ecaceee6', 'Benjamin', 'Martin', 'Anderson', 'Boston', '555-4321');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('d20f2b33-b98f-4a69-9a95-56d3da86f4f5', 'Mia', 'Thompson', 'Smith', 'Chicago', '555-7890');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('89075e5a-289c-4e72-89a9-02cf7c17b9b2', 'Ethan', 'Lopez', 'Williams', 'LosAngeles', '555-2345');
INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
VALUES ('cfc53692-17ab-41e1-9c4a-e96e55e10b0e', 'Amelia', 'Adams', 'Brown', 'Houston', '555-8765');

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
INSERT INTO "bank" (bank_id, name)
VALUES ('9037c2f1-d96a-4265-8eef-e77c77538b2b', 'Bronze');
INSERT INTO "bank" (bank_id, name)
VALUES ('7e8b4d3c-8cc4-4563-9c09-6764aeb9e84a', 'Copper');
INSERT INTO "bank" (bank_id, name)
VALUES ('a9e3b01c-5ad5-44da-8f5a-05ff88ad97d5', 'Clever');

DROP TABLE IF EXISTS "account";
CREATE TABLE IF NOT EXISTS "account"
(
    account_id   VARCHAR(255) PRIMARY KEY,
    opening_date TIMESTAMP      NOT NULL,
    user_id      VARCHAR(255)   NOT NULL,
    bank_id      VARCHAR(255)   NOT NULL,
    balance      NUMERIC(18, 2) NOT NULL,
    version      BIGSERIAL      NOT NULL,
    FOREIGN KEY (bank_id) REFERENCES bank (bank_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES "user" (user_id) ON DELETE CASCADE
);


INSERT INTO "account" (account_id, opening_date, user_id, bank_id, balance)
VALUES ('a1e86e08-4402-11ee-be56-0242ac120002', '2023-08-27 10:00:00', '81ade06e-4402-11ee-be56-0242ac120002',
        '8cf724a3-5ebe-47fe-ae8b-6f729df5f15a', 1500.00);
INSERT INTO "account" (account_id, opening_date, user_id, bank_id, balance)
VALUES ('b1e86e08-4402-11ee-be56-0242ac120003', '2023-08-28 14:30:00', 'a87db4e1-5093-4b41-ad02-4a055a871a98',
        'f7680199-c0b4-4b46-bb04-7835f0737ae3', 2500.00);
INSERT INTO "account" (account_id, opening_date, user_id, bank_id, balance)
VALUES ('c1e86e08-4402-11ee-be56-0242ac120004', '2023-08-29 09:15:00', '66fd3eeb-405a-4628-a55e-d32fe1470497',
        '9037c2f1-d96a-4265-8eef-e77c77538b2b', 500.75);
INSERT INTO "account" (account_id, opening_date, user_id, bank_id, balance)
VALUES ('d1e86e08-4402-11ee-be56-0242ac120005', '2023-08-30 12:00:00', '7bd1f9fe-21ab-49ac-a461-d9ea85b987d3',
        '7e8b4d3c-8cc4-4563-9c09-6764aeb9e84a', 10000.00);
INSERT INTO "account" (account_id, opening_date, user_id, bank_id, balance)
VALUES ('e1e86e08-4402-11ee-be56-0242ac120006', '2023-08-31 16:45:00', '357df1d3-9970-4516-985f-d640665f2ea5',
        'a9e3b01c-5ad5-44da-8f5a-05ff88ad97d5', 750.25);
INSERT INTO "account" (account_id, opening_date, user_id, bank_id, balance)
VALUES ('f1e86e08-4402-11ee-be56-0242ac120007', '2023-09-01 08:30:00', '43c17b1d-853a-4b8f-b18f-6218b8a3ac2c',
        '8cf724a3-5ebe-47fe-ae8b-6f729df5f15a', 5000.00);
INSERT INTO "account" (account_id, opening_date, user_id, bank_id, balance)
VALUES ('d2e86e08-4402-11ee-be56-0242ac120008', '2023-09-02 10:15:00', '59c5eefc-99d0-4736-90e7-9356db22e4da',
        'f7680199-c0b4-4b46-bb04-7835f0737ae3', 12000.75);
INSERT INTO "account" (account_id, opening_date, user_id, bank_id, balance)
VALUES ('e2e86e08-4402-11ee-be56-0242ac120009', '2023-09-03 14:30:00', '7161a7d9-6b67-4081-8b31-53f12d83030f',
        '9037c2f1-d96a-4265-8eef-e77c77538b2b', 2500.50);
INSERT INTO "account" (account_id, opening_date, user_id, bank_id, balance)
VALUES ('f2e86e08-4402-11ee-be56-0242ac120010', '2023-09-04 09:00:00', 'e1340e84-835d-4c63-994b-b1ff8b78d2c7',
        '8cf724a3-5ebe-47fe-ae8b-6f729df5f15a', 8000.00);
INSERT INTO "account" (account_id, opening_date, user_id, bank_id, balance)
VALUES ('g2e86e08-4402-11ee-be56-0242ac120011', '2023-09-05 11:45:00', '43c17b1d-853a-4b8f-b18f-6218b8a3ac2c',
        '7e8b4d3c-8cc4-4563-9c09-6764aeb9e84a', 500.25);
INSERT INTO "account" (account_id, opening_date, user_id, bank_id, balance)
VALUES ('2', '2023-09-06 16:20:00', 'eb218a34-e36d-4a22-9b57-b8a57d6289dd',
        'a9e3b01c-5ad5-44da-8f5a-05ff88ad97d5', 2000.00);
INSERT INTO "account" (account_id, opening_date, user_id, bank_id, balance)
VALUES ('1', '2023-09-07 08:30:00', '357df1d3-9970-4516-985f-d640665f2ea5', 'f7680199-c0b4-4b46-bb04-7835f0737ae3',
        100.80);


DROP TABLE IF EXISTS "transaction";
CREATE TABLE IF NOT EXISTS "transaction"
(
    transaction_id   VARCHAR(255) PRIMARY KEY,
    timestamp        TIMESTAMP      NOT NULL,
    type_transaction VARCHAR(255)   NOT NULL,
    from_account_id  VARCHAR(255)   ,
    to_account_id    VARCHAR(255)   ,
    amount           NUMERIC(18, 2) NOT NULL,
    FOREIGN KEY (from_account_id) REFERENCES "account" (account_id) ON DELETE CASCADE,
    FOREIGN KEY (to_account_id) REFERENCES "account" (account_id) ON DELETE CASCADE
);

INSERT INTO "transaction" (transaction_id, timestamp, type_transaction, from_account_id, to_account_id, amount)
VALUES ('1', '2023-09-08 09:45:00', 'TRANSFER', 'a1e86e08-4402-11ee-be56-0242ac120002', 'b1e86e08-4402-11ee-be56-0242ac120003', 100.00);
INSERT INTO "transaction" (transaction_id, timestamp, type_transaction, from_account_id, to_account_id, amount)
VALUES ('2', '2023-09-08 10:30:00', 'DEBIT', 'd1e86e08-4402-11ee-be56-0242ac120005', NULL, -1000.00);
INSERT INTO "transaction" (transaction_id, timestamp, type_transaction, from_account_id, to_account_id, amount)
VALUES ('3', '2023-09-08 12:15:00', 'TRANSFER', 'b1e86e08-4402-11ee-be56-0242ac120003', 'c1e86e08-4402-11ee-be56-0242ac120004', 300.50);
INSERT INTO "transaction" (transaction_id, timestamp, type_transaction, from_account_id, to_account_id, amount)
VALUES ('4', '2023-09-08 14:30:00', 'CREDIT', NULL, 'e1e86e08-4402-11ee-be56-0242ac120006', 1000.00);
INSERT INTO "transaction" (transaction_id, timestamp, type_transaction, from_account_id, to_account_id, amount)
VALUES ('5', '2023-09-08 16:00:00', 'DEBIT', 'f1e86e08-4402-11ee-be56-0242ac120007', NULL, -750.00);
INSERT INTO "transaction" (transaction_id, timestamp, type_transaction, from_account_id, to_account_id, amount)
VALUES ('6', '2023-09-09 09:00:00', 'TRANSFER', 'd2e86e08-4402-11ee-be56-0242ac120008', 'g2e86e08-4402-11ee-be56-0242ac120011', 200.25);


