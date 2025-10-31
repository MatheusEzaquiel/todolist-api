CREATE TABLE task_priority (
    id UUID PRIMARY KEY,
    priority VARCHAR(60) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

-- Initial task priorities
INSERT INTO task_priority (id, priority, created_at, updated_at, enabled) VALUES
(gen_random_uuid(), 'High', CURRENT_TIMESTAMP, NULL, TRUE),
(gen_random_uuid(), 'Medium', CURRENT_TIMESTAMP, NULL, TRUE),
(gen_random_uuid(), 'Low', CURRENT_TIMESTAMP, NULL, TRUE);
