package ru.vadim.spring_local_llm_course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @GetMapping("/")
    public String mainPage()  {
        return "chat";
    }
}
