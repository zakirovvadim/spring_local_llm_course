package ru.vadim.spring_local_llm_course;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.vadim.spring_local_llm_course.repository.ChatRepository;
import ru.vadim.spring_local_llm_course.services.PostgresChatMemory;

@SpringBootApplication
public class SpringLocalLlmCourseApplication {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private VectorStore vectorStore;

    public static void main(String[] args) {
        SpringApplication.run(SpringLocalLlmCourseApplication.class, args);
    }
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultAdvisors(getHistoryAdvisor()/*, getRagAdviser()*/).build();
    }

    private Advisor getHistoryAdvisor() {
        return MessageChatMemoryAdvisor.builder(getChatMemory()).order(-10).build();
    }

    private ChatMemory getChatMemory() {
        return new PostgresChatMemory(chatRepository, 12);
    }
}
