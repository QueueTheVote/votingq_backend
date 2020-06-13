# Add queue capacity

# --- !Ups

ALTER TABLE queues ADD COLUMN capacity int NOT NULL DEFAULT(1);

# --- !Downs

ALTER TABLE queues DROP COLUMN IF EXISTS capacity;
