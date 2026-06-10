-- Create the event_publication table with the correct TEXT type if it doesn't exist.
-- This ensures Spring Modulith can save large domain event payloads.
CREATE TABLE IF NOT EXISTS event_publication (
    id UUID NOT NULL,
    completion_date TIMESTAMP WITH TIME ZONE,
    event_type VARCHAR(255) NOT NULL,
    listener_id VARCHAR(255) NOT NULL,
    publication_date TIMESTAMP WITH TIME ZONE NOT NULL,
    serialized_event TEXT NOT NULL,
    PRIMARY KEY (id)
);

-- In case the table already existed with VARCHAR(255), we ensure it's converted to TEXT.
ALTER TABLE event_publication ALTER COLUMN serialized_event TYPE TEXT;
