
export interface LessonDTO {
    id: number;
    subjectName: string;
    teacherName: string;
    group: string;
    place: string;
    position: number;
    dateTime: string;
    type: "LECTURE" | "PRACTICE" | null;
    link: string | null;
}

export interface DayRenderDTO {
    date: string;
    lessons: LessonDTO[];
}

export interface MultiDayRenderDTO {
    days: DayRenderDTO[];
}
