
sqlplus ibatis/ibatis

-- 컬럼 출력 폭 지정
COLUMN user_id     FORMAT A12     HEADING "User ID"
COLUMN user_name   FORMAT A15     HEADING "User Name"
COLUMN password    FORMAT A10     HEADING "Password"
COLUMN age         FORMAT 999     HEADING "Age"
COLUMN grade       FORMAT 999     HEADING "Grade"
COLUMN reg_date    FORMAT A19     HEADING "Registration Date"

-- 테이블 데이터 조회
SELECT * FROM users;




==========================
 회원  table 생성 및 data insert
==========================

==========================
Drop Table
==========================
DROP TABLE users;


==============================
 users TABLE, CONSTRAINT CREATE   
==============================
CREATE TABLE users ( 
	user_id		VARCHAR2(12)	PRIMARY KEY,
	user_name	VARCHAR2(15)	NOT NULL,
	password 	VARCHAR2(10)	NOT NULL,
	age 			NUMBER(3),
	grade		NUMBER(3),
	reg_date		DATE
);


==============================
 users TABLE INSERT   
==============================
INSERT INTO users 
VALUES('user01','홍길동','user01',10,1,
			  TO_DATE( '01/10/2011 01:10:11 오전', 'MM/DD/YYYY HH:MI:SS AM'));

INSERT INTO users 
VALUES('user02','이순신','user02',20,2,
			  TO_DATE( '02/20/2022 02:20:22 오후', 'MM/DD/YYYY HH:MI:SS AM'));

INSERT INTO users 
VALUES('user03','김유신','user03',30,3,
			  TO_DATE( '03/30/2033 03:30:33 오전', 'MM/DD/YYYY HH:MI:SS AM'));

INSERT INTO users 
VALUES('ibatis01','홍길동iba','ibatis01',10,1,
			  TO_DATE( '01/10/2011 01:10:11 오전', 'MM/DD/YYYY HH:MI:SS AM'));

INSERT INTO users 
VALUES('ibatis02','이순신iba','ibatis02',20,2,
			  TO_DATE( '02/20/2022 02:20:22 오후', 'MM/DD/YYYY HH:MI:SS AM'));

INSERT INTO users 
VALUES('ibatis03','김유신iba','ibatis03',30,3,
			  TO_DATE( '03/30/2033 03:30:33 오전', 'MM/DD/YYYY HH:MI:SS AM'));

==============================
 users TABLE INSERT ROW COMMIT  
==============================
COMMIT;


==============================
 users TABLE QUERY   
==============================
SELECT * FROM users;

