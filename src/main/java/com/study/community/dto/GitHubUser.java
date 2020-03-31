package com.study.community.dto;

import lombok.Data;

@Data
public class GitHubUser {
    private Long id;
    private String name;
    private String bio;
    //不用使用avatar_url，因为
    private String avatarUrl;
}
