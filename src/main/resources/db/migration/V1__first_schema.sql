CREATE TABLE merchants (
    id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL
);

CREATE TABLE transactions (
    id                 UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    merchant_id        UUID NOT NULL,
    card_number        INTEGER NOT NULL,
    amount             DOUBLE PRECISION NOT NULL,
    card_brand         VARCHAR(255) NOT NULL,
    transaction_status VARCHAR(50) NOT NULL CHECK (transaction_status IN ('APROBADO', 'RECHAZADO', 'PENDIENTE')),
    liquidation_status VARCHAR(50) NOT NULL CHECK (liquidation_status IN ('LIQUIDADO', 'NO_LIQUIDADO')),
    transaction_date   TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_merchant FOREIGN KEY (merchant_id) REFERENCES merchants(id)
);