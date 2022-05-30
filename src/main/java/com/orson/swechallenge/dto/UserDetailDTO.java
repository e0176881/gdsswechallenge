package com.orson.swechallenge.dto;

import com.orson.swechallenge.entity.UserDetail;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDetailDTO {
    private String name;
    private Float salary;

    public UserDetailDTO(UserDetail userDetail) {
        this.name = userDetail.getName();
        this.salary = userDetail.getSalary();
    }

}
