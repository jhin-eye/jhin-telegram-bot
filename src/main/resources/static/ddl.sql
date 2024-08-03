--테이블 생성
CREATE TABLE "map_member_telegram_user" (
                                            telegram_user_id bigserial,
                                            member_id bigserial NOT NULL,
                                            primary key (telegram_user_id)
);
ALTER TABLE map_member_telegram_user
    ADD CONSTRAINT fk_map_member_telegram_user_member_id
        FOREIGN KEY (member_id)
            REFERENCES member (member_id);

