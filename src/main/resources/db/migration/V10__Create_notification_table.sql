CREATE TABLE notification
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    notifier BIGINT NOT NULL COMMENT '要通知的人',
    receiver BIGINT NOT NULL COMMENT '被评论或回复的人',
    outerId BIGINT NOT NULL COMMENT '评论id或问题id',
    type INT NOT NULL,
    gmt_create BIGINT NOT NULL,
    status INT DEFAULT 0 NOT NULL
);