
CREATE TABLE expenses (
  id          UUID          PRIMARY KEY,                    -- generated in the domain, not by the DB
  amount      NUMERIC(12,2) NOT NULL CHECK (amount > 0),    -- NUMERIC, never FLOAT, for money
  currency    VARCHAR(3)    NOT NULL,                       -- ISO code, e.g. EUR (VARCHAR avoids a Hibernate bpchar mismatch)
  description VARCHAR(255)  NOT NULL,
  category    VARCHAR(50)   NOT NULL,                       -- enum stored as its name
  spent_on    DATE          NOT NULL,                       -- the day you spent it
  created_at  TIMESTAMPTZ   NOT NULL                        -- when the record was made
);
