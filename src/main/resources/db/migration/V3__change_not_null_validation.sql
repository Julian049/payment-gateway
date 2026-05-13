ALTER TABLE transactions
    ALTER COLUMN card_brand DROP NOT NULL;

ALTER TABLE transactions
    ADD CONSTRAINT chk_card_brand
        CHECK (
            card_brand IS NULL
                OR card_brand IN ('Visa', 'Mastercard')
            );