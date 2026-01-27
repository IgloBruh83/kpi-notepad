package edu.kpinotepad.controllers;

import edu.kpinotepad.dto.DayRenderDTO;
import edu.kpinotepad.dto.MultiDayRenderDTO;
import edu.kpinotepad.services.LessonService;
import edu.kpinotepad.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CalendarController {

    private final LessonService lessonService;
    private final ScheduleService scheduleService;

    /**
     * Отримати розклад на один конкретний день
     * URL: GET /api/lessons/day?date=2026-01-14
     */
    @GetMapping("/day")
    public DayRenderDTO getDaySchedule(
            @CookieValue(value = "authToken", required = false) String authToken,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return lessonService.getDaySchedule(date);
    }

    /**
     * Отримати розклад на кілька днів (наприклад, 9 для безшовного скролу)
     * URL: GET /api/lessons/range?start=2026-01-14&count=9
     */
    @GetMapping("/range")
    public MultiDayRenderDTO getMultiDaySchedule(
            @CookieValue(value = "authToken", required = false) String login,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "count", defaultValue = "9") int count) {
        return lessonService.getMultiDaySchedule(startDate, count, login);
    }

    @GetMapping("/generate")
    public ResponseEntity<String> generate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        scheduleService.generateLessons(start, end);
        return ResponseEntity.ok("Генерація завершена успішно для періоду: " + start + " - " + end);
    }
}