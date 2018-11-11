CREATE TABLE users
(
  id character varying(300) NOT NULL,
  firstname character varying(300) NOT NULL,
  lastname character varying(300),
  email character varying,
  status character varying NOT NULL,
  account_expires_on timestamp without time zone,
  CONSTRAINT users_pk PRIMARY KEY (id),
  CONSTRAINT users_email_unique_constraint UNIQUE (email)
);

CREATE TABLE user_credential
(
  id character varying(300) NOT NULL,
  password character varying(200) NOT NULL,
  type character varying(30) NOT NULL,
  userid character varying(300),
  password_expires_on timestamp without time zone,
  CONSTRAINT user_credentials_constraint_pk PRIMARY KEY (id),
  CONSTRAINT user_credentials_constraint_fk FOREIGN KEY (userid)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE logged_in_history
(
  id character varying(300) NOT NULL,
  success boolean NOT NULL,
  userid character varying(300),
  logged_in_at timestamp without time zone,
  CONSTRAINT logged_in_history_constraint_pk PRIMARY KEY (id),
  CONSTRAINT logged_in_history_constraint_fk FOREIGN KEY (userid)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);