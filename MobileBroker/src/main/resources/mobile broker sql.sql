CREATE TABLE operator (
    operator_id VARCHAR(10) PRIMARY KEY,  
    operator_name VARCHAR(150) NOT NULL,
    country_code INT NOT NULL
)

CREATE TABLE operator_prefix (
    operator_prefix_id SERIAL PRIMARY KEY,
    operator_id VARCHAR(10) NOT NULL,
    ndc INT NOT NULL,
    prefix_start INT NOT NULL,
    prefix_end INT NOT NULL,
    CONSTRAINT fk_prefix_operator FOREIGN KEY (operator_id) REFERENCES operator(operator_id),
    CONSTRAINT fk_prefix_ndc FOREIGN KEY (ndc) REFERENCES ndc(ndc)
)

CREATE TABLE ndc (
    ndc INT PRIMARY KEY,
    area VARCHAR(50) NOT NULL,
    number_type VARCHAR(20) NOT NULL
)

CREATE TABLE tenant (
	tenant_id SERIAL PRIMARY KEY,
	tenant_name VARCHAR(50) NOT NULL,
)

CREATE TABLE api_key (
	api_key_id SERIAL PRIMARY KEY,
	api_key VARCHAR(100) NOT NULL,
	tenant_id INT NOT NULL,
	active BOOLEAN NOT NULL DEFAULT TRUE
	CONSTRAINT fk_tenant_api_key FOREIGN KEY (tenant_id) REFERENCE tenant(tenant_id)
)

CREATE TABLE provider (
	provider_id VARCHAR(20) PRIMARY KEY NOT NULL,
	provider_name VARCHAR(50) NOT NULL
)

CREATE TABLE tenant_provider_sender_name (
	tenant_id INT NOT NULL,
	provider_id VARCHAR(20) NOT NULL,
	sender_name VARCHAR(50) NOT NULL,
	priority INT NOT NULL,
	PRIMARY KEY (tenant_id, provider_id),
	CONSTRAINT fk_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id),
	CONSTRAINT fk_provider FOREIGN KEY (provider_id) REFERENCES provider(provider_id)
)


INSERT INTO operator (operator_id, operator_name, country_code)
VALUES
    ('MPT', 'Myanmar Post and Telecommunications', 95),
    ('MEC', 'Myanmar Economic Corporation', 95),
    ('TIM', 'Telecom International Myanmar Co.,Ltd', 95),
    ('ATOM', 'ATOM Myanmar Limited', 95),
    ('U9', 'Ooredoo Myanmar Limited', 95);

INSERT INTO tenant (tenant_name)
VALUES 
	('MMBusTicket'),
	('ShweBooking');

INSERT INTO provider (provider_id, provider_name)
VALUES 
	('INFOBIP','Infobip'),
	('SMSPOH', 'SMSPoh');

INSERT INTO tenant_provider_sender_name (tenant_id, provider_id, sender_name, priority)
VALUES 
	(1, 'INFOBIP', 'MMBusTicket', 0),
	(1, 'SMSPOH', 'MMBus', 1);








-- OLD DESIGN

CREATE TABLE operator (
	operator_id SERIAL PRIMARY KEY,
	operator_name VARCHAR(150) NOT NULL,
	country_code INT NOT NULL
)

CREATE TABLE operator_prefix (
	operator_prefix_id SERIAL PRIMARY KEY,
	operator_id INT NOT NULL,
	ndc INT NOT NULL,
	prefix_start INT NOT NULL,
	prefix_end INT NOT NULL,
	CONSTRAINT fk_prefix_operator FOREIGN KEY (operator_id) REFERENCES operator(operator_id),
	CONSTRAINT fk_prefix_ndc FOREIGN KEY (ndc) REFERENCES ndc(ndc)
)

CREATE TABLE ndc (
	ndc INT PRIMARY KEY,
	area VARCHAR(50) NOT NULL,
	number_type VARCHAR(20) NOT NULL
)


INSERT INTO operator (operator_name, country_code) 
VALUES 
	('Myanmar Post and Telecommunications',95),
	('Myanmar Economic Corporation',95),
	('Telecom International Myanmar Co.,Ltd',95),
	('ATOM Myanmar Limited',95),
	('Ooredoo Myanmar Limited',95)

select * from operator

INSERT INTO operator_prefix(operator_id, ndc, prefix_start, prefix_end)
VALUES 	((SELECT operator_id FROM operator WHERE operator_name = 'Myanmar Post and Telecommunications'),9, 20, 24),
		((SELECT operator_id FROM operator WHERE operator_name = 'Myanmar Post and Telecommunications'),9, 250, 259),
		((SELECT operator_id FROM operator WHERE operator_name = 'Myanmar Economic Corporation'),9, 300, 309),
		((SELECT operator_id FROM operator WHERE operator_name = 'Telecom International Myanmar Co.,Ltd'),9, 6500, 6509),
		((SELECT operator_id FROM operator WHERE operator_name = 'ATOM Myanmar Limited'),9, 7400, 7409),
		((SELECT operator_id FROM operator WHERE operator_name = 'Ooredoo Myanmar Limited'),9, 9400, 9401)

INSERT INTO operator_prefix(operator_id, ndc, prefix_start, prefix_end)
VALUES
    ('MPT', 9, 20, 24),
    ('MPT', 9, 250, 259),
    ('MEC', 9, 300, 309),
    ('TIM', 9, 6500, 6509),
    ('ATOM', 9, 7400, 7409),
    ('OOREDOO', 9, 9400, 9401);

select * from operator_prefix

INSERT INTO ndc 
VALUES 	(2, 'Mandalay', 'GEOGRAPHIC'),
		(1, 'Yangon', 'GEOGRAPHIC'),
		(9, 'Mobile', 'MOBILE')

select * from ndc

SELECT * FROM operator_prefix p
        WHERE p.ndc = 9
        AND 254 BETWEEN p.prefix_start AND p.prefix_end

SELECT 
    o.operator_name,
    op.ndc,
    n.area AS area,
    n.number_type,
    op.prefix_start,
    op.prefix_end
FROM operator o
JOIN operator_prefix op ON o.operator_id = op.operator_id
JOIN ndc n ON op.ndc = n.ndc
ORDER BY o.operator_name, op.ndc, op.prefix_start

select * from api_key