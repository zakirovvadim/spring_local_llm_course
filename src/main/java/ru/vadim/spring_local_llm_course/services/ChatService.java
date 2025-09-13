package ru.vadim.spring_local_llm_course.services;

import lombok.SneakyThrows;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.vadim.spring_local_llm_course.model.Chat;
import ru.vadim.spring_local_llm_course.model.ChatEntry;
import ru.vadim.spring_local_llm_course.model.Role;
import ru.vadim.spring_local_llm_course.repository.ChatRepository;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ChatClient chatClient;
    @Autowired
    private ChatService myProxy;

    public List<Chat> getAllChats() {
        return chatRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Chat getChat(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow();
    }

    public Chat createNewChat(String title) {
        Chat chat = Chat.builder().title(title).build();
        return chatRepository.save(chat);
    }

    public void deleteChat(Long chatId) {
        chatRepository.deleteById(chatId);
    }

    @Transactional
    public void proceedInteraction(Long chatId, String prompt) {
        myProxy.addChatEntry(chatId, prompt, Role.USER);
        String answer = chatClient.prompt().user(prompt).call().content();
        myProxy.addChatEntry(chatId, answer, Role.ASSISTANT);
    }

    @Transactional
    public void addChatEntry(Long chatId, String prompt, Role role) {
        Chat chat = chatRepository.findById(chatId).orElseThrow();
        chat.addEntry(ChatEntry.builder().content(prompt).role(role).build());
    }

    public SseEmitter proceedInteractionWithStreaming(Long chatId, String prompt) {
        StringBuilder answer = new StringBuilder();
        SseEmitter sseEmitter = new SseEmitter(0L);

        chatClient.prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, chatId))
                .user(prompt).stream()
                .chatResponse()
                .subscribe(response -> processToken(sseEmitter, response, answer),
                        sseEmitter::completeWithError);
        return sseEmitter;
    }

    @SneakyThrows
    private void processToken(SseEmitter sseEmitter, ChatResponse response, StringBuilder answer) {
        var token = response.getResult().getOutput();
        sseEmitter.send(token);
        answer.append(token.getText());
    }
}
