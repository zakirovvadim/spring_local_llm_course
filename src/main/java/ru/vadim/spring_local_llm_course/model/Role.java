package ru.vadim.spring_local_llm_course.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.Arrays;

public enum Role {
    USER("user") {
        @Override
        Message getMessage(String message) {
            return new UserMessage(message);
        }
    },
    ASSISTANT("assistant"){
        @Override
        Message getMessage(String message) {
            return new AssistantMessage(message);
        }
    },
    SYSTEM("system"){
        @Override
        Message getMessage(String message) {
            return new SystemMessage(message);
        }
    };

    private final String role;

    Role(String role) {
        this.role = role;
    }

    abstract Message getMessage(String prompt);

    public static Role getRole(String roleName) {
        return Arrays.stream(Role.values()).filter(role -> role.role.equals(roleName)).findFirst().orElseThrow();
    }
}
