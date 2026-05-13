ALTER TABLE transactions
ALTER COLUMN card_number TYPE VARCHAR(20);

INSERT INTO merchants (id, name)
VALUES ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Eventos SAS');