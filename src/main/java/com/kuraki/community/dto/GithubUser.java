package com.kuraki.community.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class GithubUser {

    private String name;
    private Long id;
    private String bio;
}
