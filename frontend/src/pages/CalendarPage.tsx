import React, { useRef, useEffect, useLayoutEffect, useState } from 'react';
import { DayRenderDTO, MultiDayRenderDTO, LessonDTO } from '../types';
import { lessonService } from '../services/lessonService';

const getLessonTime = (position: number) => {
  const times: Record<number, string> = {
    1: "08:30 - 10:05", 2: "10:25 - 12:00", 3: "12:20 - 13:55",
    4: "14:15 - 15:50", 5: "16:10 - 17:45", 6: "18:10 - 19:45",
  };
  return times[position] || "Час не визначено";
};

const isLive = (dateStr: string, position: number) => {
  const now = new Date();
  const today = now.toISOString().split('T')[0];
  if (dateStr !== today) return false;

  const schedule: Record<number, { s: string; e: string }> = {
    1: { s: "08:30", e: "10:05" }, 2: { s: "10:25", e: "12:00" },
    3: { s: "12:20", e: "13:55" }, 4: { s: "14:15", e: "15:50" },
    5: { s: "16:10", e: "17:45" }, 6: { s: "18:10", e: "19:45" },
  };

  const time = schedule[position];
  if (!time) return false;

  const currentTime = now.getHours() * 60 + now.getMinutes();
  const [sH, sM] = time.s.split(':').map(Number);
  const [eH, eM] = time.e.split(':').map(Number);
  return currentTime >= (sH * 60 + sM) && currentTime <= (eH * 60 + eM);
};

const CalendarPage: React.FC = () => {
  const calendarRef = useRef<HTMLDivElement>(null);
  const targetScrollLeft = useRef<number>(0);
  const [schedule, setSchedule] = useState<DayRenderDTO[]>([]);
  const [loading, setLoading] = useState(true);

  const dayWidth = 287;
  const gap = 10;
  const scrollStep = dayWidth + gap;

  useEffect(() => {
    const loadData = async () => {
      try {
        setLoading(true);
        const data: MultiDayRenderDTO = await lessonService.getRangeSchedule("2026-01-01", 150); 
        setSchedule(data.days);
      } finally {
        setLoading(false);
      }
    };
    loadData();
  }, []);

  useLayoutEffect(() => {
    if (calendarRef.current && schedule.length > 0) {
      const today = new Date().toISOString().split('T')[0];
      const todayIdx = schedule.findIndex(d => d.date === today);
      if (todayIdx !== -1) {
        const offset = todayIdx * scrollStep;
        calendarRef.current.scrollLeft = offset;
        targetScrollLeft.current = offset;
      }
    }
  }, [schedule]);

  const scroll = (direction: 'left' | 'right') => {
    if (calendarRef.current) {
      targetScrollLeft.current += (direction === 'left' ? -scrollStep : scrollStep);
      const max = (schedule.length - 1) * scrollStep;
      targetScrollLeft.current = Math.max(0, Math.min(targetScrollLeft.current, max));
      calendarRef.current.scrollTo({ left: targetScrollLeft.current, behavior: 'smooth' });
    }
  };

  const renderDayLessons = (day: DayRenderDTO) => {
  if (!day.lessons || day.lessons.length === 0) {
    if (new Date(day.date) < new Date('2026-02-02')) {
      return (
          <div className="sch-noclass">
            <i style={{display: 'block', textAlign: 'center'}}>Канікули</i>
          </div>
      );
    } else {
      return (
          <div className="sch-noclass">
            <i style={{display: 'block', textAlign: 'center'}}>Пари відсутні</i>
          </div>
      );
    }
  }

  const sorted = [...day.lessons].sort((a, b) => a.position - b.position);
  const maxPos = sorted[sorted.length - 1].position;
  const lessonMap = new Map(sorted.map(l => [l.position, l]));
  const elements = [];

  for (let p = 1; p <= maxPos; p++) {
    const lesson = lessonMap.get(p);
    
    if (lesson) {
      elements.push(
        <div key={lesson.id} className={lesson.type === 'LECTURE' ? 'sch-class-lec' : 'sch-class-prac'}>
          <h6><b>({lesson.position})</b> {lesson.subjectName}</h6>
          {lesson.place && <i className="fa-regular fa-circle-play"></i> && <span>{lesson.place}</span>}
          {isLive(day.date, lesson.position) && <span style={{ color: 'red' }}> ● Зараз</span>}
          <i className="fa-regular fa-clock"></i>
          <span>{getLessonTime(lesson.position)}</span><br />
          <i className="fa-regular fa-user"></i>
          <span>{lesson.teacherName}</span>
        </div>
      );
    } else {
      let nextWithLesson = p + 1;
      while(nextWithLesson <= maxPos && !lessonMap.has(nextWithLesson)) {
        nextWithLesson++;
      }
      
      const gapStart = p;
      const gapEnd = nextWithLesson - 1;
      const gapText = gapStart === gapEnd 
        ? `${gapStart} пара відсутня`
        : `${gapStart}-${gapEnd} пари відсутні`;
      
      elements.push(
        <div key={`gap-${p}`} className="sch-noclass">
          <i style={{ display: 'block', textAlign: 'center' }}>{gapText}</i>
        </div>
      );
      
      p = gapEnd;
    }
  }
  return elements;
};

  if (loading) return (
      <div className="centering loading">
        <h3>Loading...</h3>
      </div>
  );

  return (
    <div className="calendar-wrapper">
      <div className="nav-zone left-zone" onClick={() => scroll('left')}><i className="fa-solid fa-chevron-left"></i></div>
      <div className="nav-zone right-zone" onClick={() => scroll('right')}><i className="fa-solid fa-chevron-right"></i></div>

      <div id="calendar-main" ref={calendarRef}>
        {schedule.map((day) => {
          const isToday = day.date === new Date().toISOString().split('T')[0];
          return (
            <div className="calendar-day" key={day.date}>
              <div className="block">
                <h5 style={{ textAlign: 'center', margin: 0 }}>
                  {new Date(day.date).toLocaleDateString('uk-UA', { day: '2-digit', month: '2-digit' })} - {new Date(day.date).toLocaleDateString('uk-UA', { weekday: 'long' })}
                </h5>
                {isToday && <h6 style={{ textAlign: 'center', margin: 0, color: 'red' }}>Сьогодні</h6>}
              </div>
              <div className="block">
                {renderDayLessons(day)}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default CalendarPage;