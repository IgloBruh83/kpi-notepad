import React, { useEffect, useState } from 'react';
import { SubjectDTO } from '../types';

const roleTranslations: Record<string, string> = {
    'LECTURE': 'Лектор',
    'PRACTICE': 'Практик',
    'BOTH': 'Лектор + Практик',
};

const SubjectList = () => {
    const [subjects, setSubjects] = useState<SubjectDTO[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        // Фетчимо дані з твого API
        fetch('/api/subjects')
            .then(response => response.json())
            .then(data => {
                setSubjects(data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching subjects:', error);
                setLoading(false);
            });
    }, []);

    if (loading) return (
      <div className="centering loading">
        <h3>Loading...</h3>
      </div>
    );

    return (
        <div className="centering">
            <div id="subjects-main">
                {subjects.map((subject) => (
                    <div className="block subject-item" key={subject.subjectId}>
                        <div className="subject-item-left">
                            <h3>{subject.subjectName}</h3>
                            <span style={{ color: subject.general ? 'red' : 'blue'}}>
                                <span style={{color: 'black'}}>Семестр 4 - </span><b>{subject.general ? 'Загальний предмет' : 'Вибірковий предмет'}</b>
                            </span>
                            <br />
                            
                            {/* Відображаємо посилання, лише якщо воно є в базі */}
                            {subject.officialLinks && subject.officialLinks.length > 0 && (
                                <div style={{ marginBottom: '15px' }}>
                                    {subject.officialLinks.map((link, index) => (
                                        <React.Fragment key={index}>
                                            <a href={link.url} target='_blank' rel="noreferrer">
                                                {link.label || link.url}
                                            </a>
                                            <br />
                                        </React.Fragment>
                                    ))}
                                </div>
                            )}
                            {subject.generalInfo && subject.generalInfo !== 'none' && (
                            <>
                            <span><b>Основна інформація: </b></span>
                            <div className='leftMarg' style={{ whiteSpace: 'pre-wrap' }}>
                                {/* Використовуємо pre-wrap для збереження твоїх переносів рядків */}
                                {subject.generalInfo}
                            </div>
                            <br />
                            </>
                            )}

                            {subject.conditions && subject.conditions !== 'none' && (
                            <>
                            <span><b>Умови зарахування: </b></span>
                            <div className='leftMarg' style={{ whiteSpace: 'pre-wrap' }}>
                                {subject.conditions}
                            </div>
                            </>
                            )}
                        </div>

                        <div className="subject-item-right">
                            {subject.teachers.map((t, index) => (
                                <div className="portrait" key={index}>
                                    <img 
                                        /* Якщо в БД null або пуста строка, підставиться шлях до заглушки */
                                        src={t.teacherAvatarUrl || '/img/placeholder.jpg'} 
                                        width="150px" 
                                        height="auto" 
                                        alt={t.teacherName} 
                                        /* Додатковий захист: якщо файл за вказаним посиланням не знайдено (помилка 404) */
                                        onError={(e) => {
                                            (e.target as HTMLImageElement).src = '/img/placeholder.jpg';
                                        }}
                                    />
                                    <span><b>{t.teacherName}</b></span>
                                    <span>{roleTranslations[t.teacherRole] || t.teacherRole}</span>
                                </div>
                            ))}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default SubjectList;