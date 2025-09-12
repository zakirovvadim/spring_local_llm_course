package ru.vadim.spring_local_llm_course.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatEntry> history;

    public void addEntry(ChatEntry entry) {
        history.add(entry);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<ChatEntry> getHistory() {
        return history;
    }

    public static ChatBuilder builder() {
        return new ChatBuilder();
    }

    public Chat() {
    }

    public Chat(Long id, String title, LocalDateTime createdAt, List<ChatEntry> history) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.history = history;
    }

    public static class ChatBuilder {
        @Generated
        private Long id;
        @Generated
        private String title;
        @Generated
        private LocalDateTime createdAt;
        @Generated
        private List<ChatEntry> history;

        @Generated
        ChatBuilder() {
        }

        @Generated
        public ChatBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        @Generated
        public ChatBuilder title(final String title) {
            this.title = title;
            return this;
        }

        @Generated
        public ChatBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        @Generated
        public ChatBuilder history(final List<ChatEntry> history) {
            this.history = history;
            return this;
        }

        @Generated
        public Chat build() {
            return new Chat(this.id, this.title, this.createdAt, this.history);
        }

        @Generated
        public String toString() {
            Long var10000 = this.id;
            return "Chat.ChatBuilder(id=" + var10000 + ", title=" + this.title + ", createdAt=" + String.valueOf(this.createdAt) + ", history=" + String.valueOf(this.history) + ")";
        }
    }
}
