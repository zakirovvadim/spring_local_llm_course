package ru.vadim.spring_local_llm_course.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
public enum Role {
    USER("user"), ASSISTANT("assistant"), SYSTEM("system");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public static Role getRole(String roleName) {
        return Arrays.stream(Role.values()).filter(role -> role.role.equals(roleName)).findFirst().orElseThrow();
    }
}
