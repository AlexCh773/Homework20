package com.example.homework20;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus()
public class EmployeeBadParameters extends RuntimeException {
    public EmployeeBadParameters() {
    }

    public EmployeeBadParameters(String message) {
        super(message);
    }
}
