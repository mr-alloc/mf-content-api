## DB 컨테이너 생성
`docker run --name zibi-db -e MYSQL_ROOT_PASSWORD=<root-password> -d mysql:8.4.0`

## Database 생성

`create database mf_dev default character set utf8mb4 collate utf8mb4_general_ci;`

## Docker Network 사용

도커 네트워크 내부에서는 mission-db 처럼 컨테이너 이름으로 통신이 가능하다.
따라서 mission-db:3306으로 연결할 수 있다.
