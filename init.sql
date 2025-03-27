-- init.sql
CREATE TABLE IF NOT EXISTS transactions (
    transaction_external_id UUID PRIMARY KEY,
    account_external_id_debit VARCHAR(255),
    account_external_id_credit VARCHAR(255),
    transfer_type_id INT,
    value DECIMAL,
    transaction_type VARCHAR(50),
    transaction_status VARCHAR(50),
    created_at TIMESTAMP
);