# Add centers data

# --- !Ups

INSERT INTO centers (name, street1, city, state, zip, current_polling_hours) VALUES('Chapel Hills Mall', '1910 Briargate Boulevard', 'Colorado Springs', 'CO', '80920', 'Tue, Jun 30: 7 am - 7 pm');
INSERT INTO centers (name, street1, city, state, zip, current_polling_hours) VALUES('Vista Grande Baptist Church', '5680 Stetson Hills Boulevard', 'Colorado Springs', 'CO', '80917', 'Tue, Jun 30: 7 am - 7 pm');
INSERT INTO centers (name, street1, city, state, zip, current_polling_hours) VALUES('EPC Clerk''s Office North Branch', '8830 North Union Boulevard', 'Colorado Springs', 'CO', '80920', 'Tue, Jun 30: 7 am - 7 pm');

# --- !Downs

DELETE FROM centers where name = 'Chapel Hills Mall' and zip = '80920';
DELETE FROM centers where name = 'Vista Grande Baptist Church' and zip = '80917';
DELETE FROM centers where name = 'EPC Clerk''s Office North Branch' and zip = '80920';
