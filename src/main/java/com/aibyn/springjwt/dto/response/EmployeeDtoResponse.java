package com.aibyn.springjwt.dto.response;

import lombok.Data;

@Data
public class EmployeeDtoResponse {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String phone;

    private String address;
}
