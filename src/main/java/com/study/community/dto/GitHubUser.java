package com.study.community.dto;

import lombok.Data;

@Data
public class GitHubUser {
    private Long id;
    private String name;
    private String bio;
    /**不用使用avatar_url，因为在配置文件(properties)里进行了下划线映射到驼峰*/
    private String avatarUrl;
}
