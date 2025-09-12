package ru.vadim.spring_local_llm_course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.vadim.spring_local_llm_course.services.ChatService;

@RestController
public class StreamingChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping(value = "/chat-stream/{chatId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter talkToModel(@PathVariable Long chatId, @RequestParam String userPrompt) {
        return chatService.proceedInteractionWithStreaming(chatId, userPrompt);
    }
}
