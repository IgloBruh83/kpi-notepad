
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

export interface TeacherSubjectDTO {
    teacherId: number;
    teacherName: string;
    teacherAvatarUrl: string;
    teacherRole: string;
}

export interface SubjectDTO {
    subjectId: number;
    subjectName: string;
    general: boolean;
    officialLinks: LinkDTO[];
    generalInfo: string;
    conditions: string;
    teachers: TeacherSubjectDTO[];
}

export interface LinkDTO {
    label: string;
    url: string;
}