CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    stage VARCHAR(20) CHECK (stage IN ('in_progress', 'completed')) DEFAULT 'in_progress',
    category_id BIGINT,
    completion_date DATE,
    customer_id BIGINT,
    priority SMALLINT CHECK (priority BETWEEN 1 AND 5),
    is_flagged BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
