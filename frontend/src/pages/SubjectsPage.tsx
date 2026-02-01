import React, { useEffect, useState } from 'react';
import { SubjectDTO } from '../types';
import { protectedFetch } from '../services/protectedFetch';
import { useWindowWidth } from '../hooks/useWindowWidth';

const roleTranslations: Record<string, string> = {
    'LECTURE': 'Лектор',
    'PRACTICE': 'Практик',
    'BOTH': 'Лектор + Практик',
};

const SubjectList = () => {
    const [subjects, setSubjects] = useState<SubjectDTO[]>([]);
    const [loading, setLoading] = useState(true);
    const width = useWindowWidth();
    const isMobile = width <= 768;

    useEffect(() => {
        protectedFetch('/api/subjects')
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

    const RenderTeacher = (t: any, index: number) => (
        <div className="portrait" key={index}>
            <img 
                src={t.teacherAvatarUrl || '/img/placeholder.jpg'} 
                className="teacher-img"
                alt={t.teacherName} 
                onError={(e) => {
                    (e.target as HTMLImageElement).src = '/img/placeholder.jpg';
                }}
            />
            <span><b>{t.teacherName}</b></span>
            <span>{roleTranslations[t.teacherRole] || t.teacherRole}</span>
        </div>
    );

    // --- MOBILE VERSION ---
    if (isMobile) {
        return (
            <div className="subjects-mobile-container">
                {subjects.map((subject) => (
                    <div className="block subject-card-mobile" key={subject.subjectId}>
                        <div className="mobile-header">
                            <h3>{subject.subjectName}</h3>
                            <span style={{ color: subject.general ? 'red' : 'blue'}}>
                                <span style={{color: 'black'}}>Семестр 4 - </span><b>{subject.general ? 'Загальний предмет' : 'Вибірковий предмет'}</b>
                            </span><br />
                        </div>

                        <div className="mobile-body">
                            {subject.officialLinks && subject.officialLinks.length > 0 && (
                                <div className="mobile-links-section">
                                    {subject.officialLinks.map((link, idx) => (
                                        <a key={idx} href={link.url} target='_blank' rel="noreferrer" className="mobile-action-link">
                                            {link.label || 'Посилання'}
                                        </a>
                                    ))}
                                </div>
                            )}

                            {subject.generalInfo && subject.generalInfo !== 'none' && (
                                <div className="mobile-info-block">
                                    <p><b>Інформація:</b></p>
                                    <div className="text-content">{subject.generalInfo}</div>
                                </div>
                            )}

                            <div className="mobile-teachers-grid">
                                {subject.teachers.map((t, idx) => RenderTeacher(t, idx))}
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        );
    }

    // --- PC VERSION ---
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
                            {subject.teachers.map((t, index) => RenderTeacher(t, index))}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default SubjectList;