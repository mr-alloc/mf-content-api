```sql
create table mf_access_credentials (
account_id bigint not null primary key,
device_type integer not null,
credential varchar(500) not null,
latest_access_token varchar(500) not null
) engine=InnoDB;

create table mf_account (
id bigint not null auto_increment primary key,
email varchar(255) not null comment '이메일',
password varchar(255) not null comment '비밀번호',
phone varchar(255) comment '휴대폰 번호',
verified_phone bit comment '휴대폰 인증 여부',
verified_email bit comment '이메일 인증 여부',
created_at bigint not null comment '계정 생성일시',
last_signed_in_at bigint comment '마지막 로그인 일시'
) comment='계정 정보' engine=InnoDB;

create table mf_anniversary (
id bigint not null auto_increment primary key,
name varchar(255) not null comment '기념일명',
schedule_id bigint not null comment '스케쥴 ID',
created_at bigint not null comment '생성일시'
) comment='기념일 정보' engine=InnoDB;

create table mf_family (
id bigint not null auto_increment primary key,
creator_id bigint not null comment '생성자 ID',
name varchar(255) not null comment '패밀리명',
description varchar(255) comment '패밀리 설명',
logo_image_url varchar(255) comment '로고 이미지 URL',
default_color_hex varchar(255) comment '기본 색상 HEX 코드',
invite_code varchar(255) comment '초대 코드',
created_at bigint not null comment '생성일시'
) comment='패밀리 정보' engine=InnoDB;

create table mf_family_member (
family_id bigint not null comment '패밀리 ID',
member_id bigint not null comment '멤버 ID',
nickname varchar(255) not null comment '닉네임',
member_role integer not null comment '멤버 역할',
profile_image_url varchar(255) comment '프로필 이미지 URL',
registered_at bigint not null comment '가입일시',
primary key (family_id, member_id)
) engine=InnoDB;

create table mf_family_member_connect_request (
family_id bigint not null,
member_id bigint not null,
connect_direction integer not null,
introduce varchar(500) not null comment '가입요청 소개',
requested_at bigint not null comment '요청일시',
primary key (family_id, member_id, connect_direction)
) comment='패밀리 연결 요청(양방향)' engine=InnoDB;

create table mf_family_mission_detail (
mission_id bigint not null comment '미션 ID' primary key,
assignee_id bigint comment '담당자 아이디',
last_update_member bigint not null comment '마지막 업데이트 멤버'
) comment='패밀리 미션 상세 정보' engine=InnoDB;

create table mf_holiday (
id bigint not null primary key,
type integer comment '1: 양력 / 2:음력',
month integer comment '월',
day integer comment '일',
name varchar(255) comment '공휴일명'
) comment='공휴일 정보' engine=InnoDB;

create table mf_member (
id bigint not null auto_increment primary key,
aid bigint comment '계정 ID',
role integer comment '역할 [0: 게스트, 1: 일반회원, 2: 관리자]',
profile_image_url varchar(255) comment '프로필 이미지 URL',
nickname varchar(255) comment '닉네임',
is_blocked bit comment '이용 정지 여부',
registered_at bigint comment '가입일시'
) comment='일반 멤버 정보' engine=InnoDB;

create table mf_mission (
id bigint not null auto_increment primary key,
is_public bit not null comment '공개 여부',
mission_name varchar(255) not null comment '미션명',
sub_name varchar(255) comment '미션 부제',
schedule_id bigint not null comment '스케쥴 ID',
place_id bigint comment '장소 ID',
mission_type integer not null comment '미션 타입 1: 일반미션, 2: 미션팩, 3: 스텝미션',
watcher varchar(255) not null comment '관람자, 스케쥴일 경우 참가자',
updated_at bigint not null comment '수정일시',
created_at bigint not null comment '생성일시'
) comment='미션 메인 정보' engine=InnoDB;

create table mf_mission_comment (
id bigint not null auto_increment primary key,
state_id bigint not null comment '미션 상태 ID',
member_id bigint not null comment '작성자 ID',
content varchar(255) not null comment '댓글 내용',
created_at bigint not null comment '작성일시'
) engine=InnoDB;

create table mf_mission_detail (
mission_id bigint not null primary key,
deadline bigint comment '제한시간(초): 시작 시 적용'
) comment='미션 상세 정보' engine=InnoDB;

create table mf_mission_state (
id bigint not null auto_increment primary key,
mission_id bigint not null comment '미션 ID',
mission_status integer not null comment '미션 상태[0: 생성, 1: 진행중, 2: 완료, 3: 삭제, 4: 항상진행(일정)]',
start_stamp bigint comment '미션 시작시간 (timestamp)',
end_stamp bigint comment '미션 종료시간 (timestamp)'
) comment='미션 상태 정보' engine=InnoDB;

create table mf_place (
id bigint not null auto_increment primary key,
register_id bigint not null,
name varchar(255) not null,
address varchar(255),
phone varchar(255),
description varchar(255),
image_url varchar(255),
latitude float(53),
longitude float(53),
created_at bigint not null
) engine=InnoDB;

create table mf_schedule (
id bigint not null auto_increment primary key,
type integer comment '스케쥴 타입 1: 기념일, 2: 미션',
reporter bigint not null comment '생성멤버',
family bigint comment '패밀리 ID',
mode integer not null comment '스케쥴 모드[1: 단일, 2: 다중, 3: 기간, 4: 반복]',
start_at bigint comment '시작일',
schedule_time bigint comment '스케쥴 시간',
end_at bigint comment '종료일',
repeat_option integer not null comment '반복 옵션[-1: 없음, 0: 매주, 1: 매월, 2: 매년]',
repeat_value integer not null comment '반복 값[repeat_option 0: 각 요일별 Bit 위치 합, 2 또는 3: 기준일]'
) comment='스케쥴 정보' engine=InnoDB;

alter table mf_access_credentials
add constraint idx_credential unique (credential);

alter table mf_account
add constraint idx_email unique (email);

create index idx_schedule_id
on mf_anniversary (schedule_id);

alter table mf_family
add constraint uk_invite_code unique (invite_code);

alter table mf_member
add constraint uk_aid unique (aid);

alter table mf_member
add constraint uk_nickname unique (nickname);

alter table mf_member
add constraint idx_aid unique (aid);

create index idx_schedule_id
on mf_mission (schedule_id);

create index idx_place_id
on mf_mission (place_id);

create index idx_state_id
on mf_mission_comment (state_id);

create index idx_family
on mf_schedule (family);

create index idx_reporter
on mf_schedule (reporter);

create index idx_between_period
on mf_schedule (start_at, end_at);

alter table mf_family_member
add constraint FKtb2wnqjhdbcw4gviq4wntecx3
foreign key (family_id)
references mf_family (id);

alter table mf_mission
add constraint FKayfdy4fuoj0pmc3lc6gjplcm7
foreign key (schedule_id)
references mf_schedule (id);
```