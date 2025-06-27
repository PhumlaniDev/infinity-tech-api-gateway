CREATE TABLE rate_user_id_limit_logs (
    id BIGSERIAL PRIMARY KEY,
    ip_address VARCHAR(50) NOT NULL,
    user_id VARCHAR(50) NOT NULL,
    endpoint VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);