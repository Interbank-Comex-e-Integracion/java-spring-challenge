-- Insertar tipos de transacción si no existen
INSERT INTO transaction_type (id, name)
VALUES (1, 'Transfer')
    ON CONFLICT (id) DO NOTHING;

-- Insertar estados de transacción si no existen
INSERT INTO transaction_status (id, name)
VALUES (1, 'Pending')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO transaction_status (id, name)
VALUES (2, 'Approved')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO transaction_status (id, name)
VALUES (3, 'Rejected')
    ON CONFLICT (id) DO NOTHING;