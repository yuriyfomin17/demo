package com.example.demo.student;

import java.util.Optional;

public interface StudentChecker {
    boolean notSame(Optional<String> param, String aStudent);
}
