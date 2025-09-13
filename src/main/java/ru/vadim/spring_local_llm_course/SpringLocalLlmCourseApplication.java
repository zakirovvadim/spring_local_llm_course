package ru.vadim.spring_local_llm_course;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.vadim.spring_local_llm_course.model.Chat;
import ru.vadim.spring_local_llm_course.repository.ChatRepository;
import ru.vadim.spring_local_llm_course.services.PostgresChatMemory;

import java.util.List;

@SpringBootApplication
public class SpringLocalLlmCourseApplication {

    @Autowired
    private ChatRepository chatRepository;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringLocalLlmCourseApplication.class, args);
        ChatClient chatClient = context.getBean(ChatClient.class);
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultAdvisors(getAdvisor()).build();
    }

    private Advisor getAdvisor() {
       return MessageChatMemoryAdvisor.builder(getChatMemory()).build();
    }

    private ChatMemory getChatMemory() {
        return PostgresChatMemory.builder()
                .maxMessages(2)
                .chatRepository(chatRepository)
                .build();
    }

}
