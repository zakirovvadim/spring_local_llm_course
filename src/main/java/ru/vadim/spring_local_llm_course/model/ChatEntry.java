package ru.vadim.spring_local_llm_course.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.ai.chat.messages.Message;

import java.time.LocalDateTime;

@Data
@Entity
public class ChatEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public static ChatEntry toChatEntry(Message message) {
        return ChatEntry.builder()
                .role(Role.getRole(message.getMessageType().getValue()))
                .content(message.getText())
                .build();
    }

    public Message toMessage() {
        return role.getMessage(content);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ChatEntry() {
    }

    public ChatEntry(Long id, String content, Role role, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.role = role;
        this.createdAt = createdAt;
    }

    public static ChatEntryBuilder builder() {
        return new ChatEntryBuilder();
    }

    public static class ChatEntryBuilder {
        @Generated
        private Long id;
        @Generated
        private String content;
        @Generated
        private Role role;
        @Generated
        private LocalDateTime createdAt;

        @Generated
        ChatEntryBuilder() {
        }

        @Generated
        public ChatEntryBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        @Generated
        public ChatEntryBuilder content(final String content) {
            this.content = content;
            return this;
        }

        @Generated
        public ChatEntryBuilder role(final Role role) {
            this.role = role;
            return this;
        }

        @Generated
        public ChatEntryBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        @Generated
        public ChatEntry build() {
            return new ChatEntry(this.id, this.content, this.role, this.createdAt);
        }

        @Generated
        public String toString() {
            Long var10000 = this.id;
            return "ChatEntry.ChatEntryBuilder(id=" + var10000 + ", content=" + this.content + ", role=" + String.valueOf(this.role) + ", createdAt=" + String.valueOf(this.createdAt) + ")";
        }
    }
}
