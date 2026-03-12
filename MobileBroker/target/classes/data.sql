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
	((SELECT tenant_id FROM tenant WHERE tenant_name = 'MMBusTicket'), 'MPT', 'SMSPOH', 1),
	((SELECT tenant_id FROM tenant WHERE tenant_name = 'MMBusTicket'), 'MPT', 'INFOBIP', 2),
	((SELECT tenant_id FROM tenant WHERE tenant_name = 'MMBusTicket'), 'U9', 'INFOBIP', 1),
	((SELECT tenant_id FROM tenant WHERE tenant_name = 'MMBusTicket'), 'U9', 'SMSPOH', 2),
	((SELECT tenant_id FROM tenant WHERE tenant_name = 'ShweBooking'), 'MPT', 'INFOBIP', 1),
	((SELECT tenant_id FROM tenant WHERE tenant_name = 'ShweBooking'), 'U9', 'INFOBIP', 1);
	
INSERT INTO sms_sender (tenant_id, provider_id, sender_name)
VALUES
    ((SELECT tenant_id FROM tenant WHERE tenant_name = 'MMBusTicket'), 'SMSPOH', 'MMBUS'),
	((SELECT tenant_id FROM tenant WHERE tenant_name = 'MMBusTicket'), 'INFOBIP', 'MMBUSTICKET');