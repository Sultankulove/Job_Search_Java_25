UPDATE users
SET email = 'admin@example.com'
WHERE account_type = 'ADMIN';

UPDATE users
SET email = 'applicant' || id || '@example.com'
WHERE account_type = 'APPLICANT';

UPDATE users
SET email = 'employer' || id || '@example.com'
WHERE account_type = 'EMPLOYER';
