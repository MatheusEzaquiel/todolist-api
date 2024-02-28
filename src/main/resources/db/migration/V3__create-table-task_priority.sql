CREATE TABLE task_priority (
    id UUID PRIMARY KEY,
    priority VARCHAR(60) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);