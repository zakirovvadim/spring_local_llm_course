package ru.vadim.spring_local_llm_course.services;

import lombok.Builder;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import ru.vadim.spring_local_llm_course.model.Chat;
import ru.vadim.spring_local_llm_course.model.ChatEntry;
import ru.vadim.spring_local_llm_course.repository.ChatRepository;

import java.util.Comparator;
import java.util.List;

public class PostgresChatMemory implements ChatMemory {

    private ChatRepository chatRepository;

    private int maxMessages;

    public PostgresChatMemory(ChatRepository chatRepository, int maxMessages) {
        this.chatRepository = chatRepository;
        this.maxMessages = maxMessages;
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        Chat chat = chatRepository.findById(Long.valueOf(conversationId)).orElseThrow();
        for (Message message : messages) {
            chat.addEntry(ChatEntry.toChatEntry(message));
        }
        chatRepository.save(chat);
    }

    @Override
    public List<Message> get(String conversationId) {
        Chat chat = chatRepository.findById(Long.valueOf(conversationId)).orElseThrow();
        return chat.getHistory().stream()
                .sorted(Comparator.comparing(ChatEntry::getCreatedAt))
                .map(ChatEntry::toMessage)
                .limit(maxMessages)
                .toList();
    }

    @Override
    public void clear(String conversationId) {

    }
}
