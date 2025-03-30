package com.example.demo.domain.feedback.api;

import com.example.demo.domain.feedback.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackApiRepository extends JpaRepository<Feedback, Long> {
    Page<Feedback> findAllByUserId(Long userId, Pageable pageable);
    Page<Feedback> findAllByUserIdAndPositive(Long userId, boolean positive, Pageable pageable);

    Page<Feedback> findAllByPositive(boolean positive, Pageable pageable);
}
