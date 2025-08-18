
MERGE INTO contact_type (type) KEY(type) VALUES ('EMAIL');
MERGE INTO contact_type (type) KEY(type) VALUES ('PHONE');
MERGE INTO contact_type (type) KEY(type) VALUES ('TELEGRAM');
MERGE INTO contact_type (type) KEY(type) VALUES ('FACEBOOK');
MERGE INTO contact_type (type) KEY(type) VALUES ('LINKEDIN');


INSERT INTO contacts_info (type_id, resume_id, contact_value)
SELECT ct.id, r.id, u.email
FROM resumes r
         JOIN users u ON u.id = r.applicant_id
         JOIN contact_type ct ON ct.type = 'EMAIL';


INSERT INTO contacts_info (type_id, resume_id, contact_value)
SELECT ct.id, r.id, u.phone_number
FROM resumes r
         JOIN users u ON u.id = r.applicant_id
         JOIN contact_type ct ON ct.type = 'PHONE';


INSERT INTO contacts_info (type_id, resume_id, contact_value)
SELECT ct.id, r.id,
       '@' || LOWER(REPLACE(u.name, ' ', '')) || '_' || CAST(r.id AS VARCHAR)
FROM resumes r
         JOIN users u ON u.id = r.applicant_id
         JOIN contact_type ct ON ct.type = 'TELEGRAM'
WHERE MOD(r.id, 3) = 0;


INSERT INTO contacts_info (type_id, resume_id, contact_value)
SELECT ct.id, r.id,
       'https://linkedin.com/in/' ||
       LOWER(REPLACE(u.name, ' ', '')) ||
       LOWER(REPLACE(u.surname, ' ', '')) ||
       CAST(r.id AS VARCHAR)
FROM resumes r
         JOIN users u ON u.id = r.applicant_id
         JOIN contact_type ct ON ct.type = 'LINKEDIN'
WHERE MOD(r.id, 4) = 0;


INSERT INTO contacts_info (type_id, resume_id, contact_value)
SELECT ct.id, r.id,
       'https://facebook.com/' ||
       LOWER(REPLACE(u.name, ' ', '')) || '.' ||
       LOWER(REPLACE(u.surname, ' ', '')) ||
       CAST(r.id AS VARCHAR)
FROM resumes r
         JOIN users u ON u.id = r.applicant_id
         JOIN contact_type ct ON ct.type = 'FACEBOOK'
WHERE MOD(r.id, 5) = 0;
