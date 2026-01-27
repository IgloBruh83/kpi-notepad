package edu.kpinotepad.services;

import edu.kpinotepad.enums.LessonStatus;
import edu.kpinotepad.enums.RepeatType;
import edu.kpinotepad.models.Lesson;
import edu.kpinotepad.models.ScheduleItem;
import edu.kpinotepad.repositories.LessonRepository;
import edu.kpinotepad.repositories.ScheduleItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleItemRepository scheduleItemRepository;
    private final LessonRepository lessonRepository;

    private static final Map<Short, LocalTime> LESSON_TIMES = Map.of(
            (short) 1, LocalTime.of(8, 30),
            (short) 2, LocalTime.of(10, 25),
            (short) 3, LocalTime.of(12, 20),
            (short) 4, LocalTime.of(14, 15),
            (short) 5, LocalTime.of(16, 10),
            (short) 6, LocalTime.of(18, 10)
    );

    @Transactional
    public void generateLessons(LocalDate rangeStart, LocalDate rangeEnd) {
        // Отримуємо всі доступні шаблони як "вічні" правила
        List<ScheduleItem> templates = scheduleItemRepository.findAll();
        List<Lesson> lessonsToSave = new ArrayList<>();

        // Цикл іде СТРОГО по датах з API
        for (LocalDate date = rangeStart; !date.isAfter(rangeEnd); date = date.plusDays(1)) {

            DayOfWeek currentDayOfWeek = date.getDayOfWeek();
            boolean isOddWeek = calculateIsOddWeek(date);

            for (ScheduleItem item : templates) {
                // Перевірка 1: Чи збігається день тижня (напр. чи це понеділок?)
                if (item.getDayOfWeek() != currentDayOfWeek) {
                    continue;
                }

                // Перевірка 2: Чи підходить парність тижня за правилом 5.01 = ODD
                if (isParityMatch(item.getRepeat(), isOddWeek)) {
                    lessonsToSave.add(convertToLesson(item, date));
                }
            }
        }

        if (!lessonsToSave.isEmpty()) {
            lessonRepository.saveAll(lessonsToSave);
        }
    }

    private boolean calculateIsOddWeek(LocalDate date) {
        // Точка відліку: 5 січня 2026 (початок непарного тижня)
        LocalDate referenceMonday = LocalDate.of(2026, 1, 5);
        long weeksBetween = ChronoUnit.WEEKS.between(referenceMonday, date);

        // weeks 0, 2, 4... -> ODD (true)
        // weeks 1, 3, 5... -> EVEN (false)
        return Math.abs(weeksBetween % 2) == 0;
    }

    private boolean isParityMatch(RepeatType repeat, boolean isOddWeek) {
        if (repeat == RepeatType.BOTH) return true;
        if (repeat == RepeatType.ODD) return isOddWeek;
        return !isOddWeek; // RepeatType.EVEN
    }

    private Lesson convertToLesson(ScheduleItem item, LocalDate date) {
        LocalTime startTime = LESSON_TIMES.getOrDefault(item.getPosition(), LocalTime.of(0, 0));

        return Lesson.builder()
                .template(item)
                .group(item.getGroup())
                .subject(item.getSubject())
                .teacher(item.getTeacher())
                .dateTime(date.atTime(startTime))
                .position(item.getPosition())
                .type(item.getType())
                .place(item.getPlace())
                .link(item.getLink())
                .status(LessonStatus.SCHEDULED)
                .build();
    }
}