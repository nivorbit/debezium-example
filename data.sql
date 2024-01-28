-- Generate sample data using generate_series and random functions
WITH sample_data AS (
    SELECT
        g.OID,
        md5(random()::text)::varchar(50) AS first_name,
        md5(random()::text)::varchar(50) AS last_name,
        md5(random()::text)::varchar(100) AS email,
        '(555) ' || trunc(random() * 90000 + 10000)::text AS phone_number,
        md5(random()::text)::varchar(255) AS address,
        md5(random()::text)::varchar(50) AS city,
        md5(random()::text)::varchar(20) AS state,
        substr(md5(random()::text), 1, 5)::varchar(10) AS postal_code,
        (CASE WHEN random() < 0.5 THEN 'Savings' ELSE 'Checking' END)::varchar(20) AS account_type,
        round(random() * 10000 + 100)::decimal(10, 2) AS balance
    FROM generate_series(1, 50000) AS g
)

-- Insert the generated sample data
INSERT INTO customers (id, first_name, last_name, email, phone_number, address, city, state, postal_code, account_type, balance)
SELECT * FROM sample_data;

-- Update some existing data
UPDATE customers
SET balance = balance + 50
WHERE id IN (SELECT id FROM generate_series(1, 10000) AS g);

-- Delete some data randomly
DELETE FROM customers
WHERE random() < 0.1;

