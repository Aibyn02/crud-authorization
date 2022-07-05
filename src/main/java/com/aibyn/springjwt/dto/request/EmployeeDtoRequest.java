package com.aibyn.springjwt.dto.request;

import lombok.Data;

@Data
public class EmployeeDtoRequest {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String phone;

    private String address;
}
