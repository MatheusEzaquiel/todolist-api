CREATE TABLE tasks (
    id UUID PRIMARY KEY,
    title VARCHAR(60) NOT NULL,
    description VARCHAR(400),
    done BOOLEAN NOT NULL DEFAULT FALSE,
    start_at_date DATE,
    start_at_time TIME,
    end_at_date DATE,
    end_at_time TIME,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    checklist_id UUID NOT NULL,
    priority_id UUID NOT NULL,
    FOREIGN KEY (checklist_id) REFERENCES checklists(id),
    FOREIGN KEY (priority_id) REFERENCES task_priority(id)
);
