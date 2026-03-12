CREATE TABLE operator (
    operator_id VARCHAR(10) PRIMARY KEY,  
    name VARCHAR(150) NOT NULL,
    country_code INT NOT NULL
);

CREATE TABLE tenant (
	tenant_id SERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL
);

CREATE TABLE api_key (
	api_key_id SERIAL PRIMARY KEY,
	api_key VARCHAR(100) NOT NULL,
	tenant_id INT NOT NULL,
	active BOOLEAN NOT NULL DEFAULT TRUE,
	CONSTRAINT fk_tenant_api_key FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id)
);

CREATE TABLE provider (
	provider_id VARCHAR(20) PRIMARY KEY NOT NULL,
	name VARCHAR(50) NOT NULL
);

CREATE TABLE sms_routing (
	tenant_id INT NOT NULL,
	operator_id VARCHAR(10) NOT NULL,
	provider_id VARCHAR(30) NOT NULL,
	priority INT NOT NULL,
	PRIMARY KEY (tenant_id, operator_id, provider_id),
	CONSTRAINT fk_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id),
	CONSTRAINT fk_operator FOREIGN KEY (operator_id) REFERENCES operator(operator_id),
	CONSTRAINT fk_provider FOREIGN KEY (provider_id) REFERENCES provider(provider_id)
);

CREATE TABLE sms_sender (
	tenant_id INT NOT NULL,
	provider_id VARCHAR(30) NOT NULL,
	sender_name VARCHAR(30) NOT NULL,
	PRIMARY KEY (tenant_id, provider_id),
	CONSTRAINT fk_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id),
	CONSTRAINT fk_provider FOREIGN KEY (provider_id) REFERENCES provider(provider_id),
	CONSTRAINT uq_sms_sender UNIQUE (tenant_id, provider_id)
);

INSERT INTO operator (operator_id, name, country_code)
VALUES
    ('MPT', 'Myanmar Post and Telecommunications', 95),
    ('MEC', 'Myanmar Economic Corporation', 95),
    ('TIM', 'Telecom International Myanmar Co.,Ltd', 95),
    ('ATOM', 'ATOM Myanmar Limited', 95),
    ('U9', 'Ooredoo Myanmar Limited', 95);

INSERT INTO tenant (name)
VALUES 
	('MMBusTicket'),
	('ShweBooking');

INSERT INTO provider (provider_id, name)
VALUES 
	('INFOBIP','Infobip'),
	('SMSPOH', 'SMSPoh');

INSERT INTO sms_routing (tenant_id, operator_id, provider_id, priority)
VALUES
	((SELECT tenant_id FROM tenant WHERE name = 'MMBusTicket'), 'MPT', 'SMSPOH', 1),
	((SELECT tenant_id FROM tenant WHERE name = 'MMBusTicket'), 'MPT', 'INFOBIP', 2),
	((SELECT tenant_id FROM tenant WHERE name = 'MMBusTicket'), 'U9', 'INFOBIP', 1),
	((SELECT tenant_id FROM tenant WHERE name = 'MMBusTicket'), 'U9', 'SMSPOH', 2),
	((SELECT tenant_id FROM tenant WHERE name = 'ShweBooking'), 'MPT', 'INFOBIP', 1),
	((SELECT tenant_id FROM tenant WHERE name = 'ShweBooking'), 'U9', 'INFOBIP', 1);
	
INSERT INTO sms_sender (tenant_id, provider_id, sender_name)
VALUES
    ((SELECT tenant_id FROM tenant WHERE name = 'MMBusTicket'), 'SMSPOH', 'MMBUS'),
	((SELECT tenant_id FROM tenant WHERE name = 'MMBusTicket'), 'INFOBIP', 'MMBUSTICKET');