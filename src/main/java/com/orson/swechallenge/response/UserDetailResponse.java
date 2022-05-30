package com.orson.swechallenge.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class UserDetailResponse implements Serializable {

    private List<com.orson.swechallenge.dto.UserDetailDTO> results;
}
