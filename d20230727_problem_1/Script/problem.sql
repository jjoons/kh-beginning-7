CREATE DATABASE IF NOT EXISTS d20230727_problem_1;
USE d20230727_problem_1;

INSERT INTO student (hakbun, passwd, name, address, tel, email, major, grade, hakjom)
VALUES('201600000', '1111', '홍길동', '경기도 성남시 상대원동', '010-1111-1111','qwer@naver.com', 1, 4, 121);
INSERT INTO student (hakbun, passwd, name, address, tel, email, major, grade, hakjom)
VALUES('201800000', '1111', '김순홍', '강남구 광평로 34길 25-35(수서동)',  '010-8765-9827', 'abcd@naver.com', 2 , 2, 91);
INSERT INTO student (hakbun, passwd, name, address, tel, email, major, grade, hakjom)
VALUES('201700000', '1111', '김미영', '경기도 구리시 교문동',  '010-8765-9827', 'abcd@naver.com', 3 , 3, 55);

COMMIT;
