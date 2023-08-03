-- 트랜잭션 실행
START TRANSECTION;

UPDATE account SET balance = balance - 200000 WHERE id = '1';
UPDATE account SET balance = balance + 200000 WHERE id = '2';

-- 지금까지 작업한 내용을 DB에 반영하고 트랜잭션 종료

COMMIT;

// 자동 실행 중일 때 1
SELECT @@autocommit;
SET autocommit = 0;
