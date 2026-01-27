package edu.kpinotepad.services;

import edu.kpinotepad.dto.DayRenderDTO;
import edu.kpinotepad.dto.LessonDTO;
import edu.kpinotepad.dto.MultiDayRenderDTO;
import edu.kpinotepad.models.Lesson;
import edu.kpinotepad.repositories.LessonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public MultiDayRenderDTO getMultiDaySchedule(LocalDate startDate, int daysCount, String login) {
        LocalDate endDate = startDate.plusDays(daysCount);
        List<Lesson> allLessons = lessonRepository.findFilteredSchedule(
                login,
                startDate.atStartOfDay(),
                endDate.atTime(LocalTime.MAX)
        );
        Map<LocalDate, List<LessonDTO>> lessonsByDate = allLessons.stream()
                .map(this::convertToDto)
                .collect(Collectors.groupingBy(dto -> dto.getDateTime().toLocalDate()));
        List<DayRenderDTO> dayRenders = new ArrayList<>();
        for (int i = 0; i < daysCount; i++) {
            LocalDate date = startDate.plusDays(i);
            List<LessonDTO> lessonsForDay = lessonsByDate.getOrDefault(date, new ArrayList<>());
            dayRenders.add(new DayRenderDTO(date, lessonsForDay));
        }
        return new MultiDayRenderDTO(dayRenders);
    }

    public DayRenderDTO getDaySchedule(LocalDate date) {
        List<Lesson> lessons = lessonRepository.findAllByDateTimeBetweenOrderByDateTimeAsc(
                date.atStartOfDay(),
                date.atTime(LocalTime.MAX)
        );
        List<LessonDTO> lessonDtoList = lessons.stream()
                .map(this::convertToDto)
                .toList();
        return new DayRenderDTO(date, lessonDtoList);
    }

    public LessonDTO convertToDto(Lesson lesson) {
        return LessonDTO.builder()
                .id(lesson.getId())
                .subjectName(lesson.getSubject().getName())
                .type(lesson.getType().toString())
                .teacherName(lesson.getTeacher() != null ? lesson.getTeacher().getDisplayName() : "NoTeacher")
                .group(lesson.getGroup().getName())
                .place(lesson.getPlace())
                .dateTime(lesson.getDateTime())
                .position(lesson.getPosition())
                .link(lesson.getLink())
                .build();
    }
}