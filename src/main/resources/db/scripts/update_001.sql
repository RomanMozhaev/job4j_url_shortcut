CREATE TABLE web_site
(
    domain   character varying(255) PRIMARY KEY,
    login    character varying(255) NOT NULL UNIQUE,
    password character varying(255) NOT NULL
);

CREATE TABLE link
(
    url     character varying(255) PRIMARY KEY,
    code    character varying(255) NOT NULL UNIQUE,
    count   integer                NOT NULL,
    website character varying(255),
    FOREIGN KEY (website) REFERENCES web_site (domain)
);