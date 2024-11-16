CREATE TABLE learning_data (
    id SERIAL PRIMARY KEY,
    learning_data_name VARCHAR(50) NOT NULL,
    learning_time INT DEFAULT 0 NOT NULL,
    category_id INT NOT NULL,
    user_id INT NOT NULL,
    registered_month DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,

    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
        REFERENCES categories (id)
        ON DELETE CASCADE,
    
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE
);