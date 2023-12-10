DROP TABLE IF EXISTS response2;

CREATE TABLE response2 (
    id BIGINT NOT NULL AUTO_INCREMENT,
    status VARCHAR(50) NOT NULL,
    img_url VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    panel_id BIGINT NOT NULL,
    survey_id BIGINT NOT NULL,
    primary key (id, created_at)
);
ALTER TABLE response2 PARTITION BY RANGE (unix_timestamp(created_at)) (
    PARTITION pLAST VALUES LESS THAN (unix_timestamp('2023-12-01 00:00:00')),
    PARTITION pCurrent VALUES LESS THAN MAXVALUE
);