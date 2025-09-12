package ru.vadim.spring_local_llm_course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vadim.spring_local_llm_course.model.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
