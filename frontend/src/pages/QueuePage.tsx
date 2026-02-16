import React, { useEffect, useState } from 'react';
import { MultiSubjectQueueDTO, SubjectQueueDTO } from '../types';
import { protectedFetch } from '../services/protectedFetch';

const priorityText: Record<string, string> = {
    1: 'Низький',
    2: 'Знижений',
    3: 'Середній',
    4: 'Високий',
    5: 'Найвищий'
};

const QueuePage: React.FC = () => {
  const [data, setData] = useState<SubjectQueueDTO[]>([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [loading, setLoading] = useState(true);
  const [currentUsername, setCurrentUsername] = useState<string | null>(null);

  // Стани для форми
  const [selectedSubjectId, setSelectedSubjectId] = useState<number>(3);
  const [task, setTask] = useState('');
  const [priority, setPriority] = useState<number>(4); // За замовчуванням "Високий"
  const [isSubmitting, setIsSubmitting] = useState(false);

  const fetchQueue = () => {
    protectedFetch('/api/queue/all')
      .then(res => res.json())
      .then((json: MultiSubjectQueueDTO) => {
        setData(json.subjects);
        // Якщо предмет ще не обраний, встановлюємо перший зі списку
        if (json.subjects.length > 0 && selectedSubjectId === 0) {
          setSelectedSubjectId(json.subjects[0].subjectId);
        }
        setLoading(false);
      })
      .catch(err => {
        console.error("Помилка завантаження черги:", err);
        setLoading(false);
      });
  };

  useEffect(() => {
    const storedUsername = localStorage.getItem('username');
    setCurrentUsername(storedUsername);
    fetchQueue();
  }, []);

  const handleNext = () => setCurrentIndex(prev => (prev + 1) % data.length);
  const handlePrev = () => setCurrentIndex(prev => (prev === 0 ? data.length - 1 : prev - 1));

  const handleSubmit = async () => {
    if (!task.trim()) {
      alert("Будь ласка, введіть назву роботи");
      return;
    }

    setIsSubmitting(true);
    try {
      const response = await protectedFetch('/api/queue/add', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          subjectId: selectedSubjectId,
          studentLogin: currentUsername,
          task: task,
          priority: priority
        }),
      });

      if (response.ok) {
        setTask('');
        fetchQueue();
      } else {
        const errorData = await response.json();
        alert(`Помилка: ${errorData.message || 'Не вдалося додати запис'}`);
      }
    } catch (err) {
      console.error("Помилка при відправці:", err);
      alert("Сталася помилка при з'єднанні з сервером");
    } finally {
      setIsSubmitting(false);
    }
  };

  if (loading) return <div className='block'>Синхронізація черг...</div>;
  if (data.length === 0) return <div className='block'>Черг для обов'язкових предметів не знайдено</div>;

  const current = data[currentIndex];

  return (
    <div id="queue-main">
      <div id="queue-left" className='queue-part'>
        <div className="block" id="queue-left-header">
          <div className='button-div' onClick={handlePrev}><i className="fa-solid fa-chevron-left"></i></div>
          <div style={{ width: '350px', textAlign: 'center' }}>
            <h5>{current.subjectName}</h5>
            <b style={{color: 'red'}}>Загальний предмет</b>
          </div>
          <div className='button-div' onClick={handleNext}><i className="fa-solid fa-chevron-right"></i></div>
        </div>
        
        <div className='block'>
          <div className='queue-element' id='queue-table-head'>
            <span>Студент</span><span>|</span>
            <span>Завдання</span><span>|</span>
            <span>Пріоритет</span>
          </div>
          <hr />
          {current.queue.map(item => (
            <React.Fragment key={item.id}>
              <div className={`queue-element ${item.studentLogin === currentUsername ? 'queue-me' : ''}`}>
                <span>{item.studentFullName}</span><span>|</span>
                <span>{item.task}</span><span>|</span>
                <span>{priorityText[item.priority]}</span>
              </div>
              <hr />
            </React.Fragment>
          ))}
        </div>
      </div>

      <div id="queue-right" className='block queue-part'>
        <h4 style={{textAlign: 'center', marginBottom: '20px'}}>- Додати новий запис -</h4>

        <label style={{fontSize: '14px', marginLeft: '5px', display: 'block', textAlign: 'center'}}>Обери предмет</label>
        <select 
            value={selectedSubjectId} 
            onChange={(e) => setSelectedSubjectId(Number(e.target.value))}
        >
            {data.map(subject => (
                <option key={subject.subjectId} value={subject.subjectId}>
                    {subject.subjectName}
                </option>
            ))}
        </select>

        <label style={{fontSize: '14px', marginLeft: '5px', display: 'block', textAlign: 'center', marginTop: '10px'}}>
            Яку роботу(-и) будеш здавати?
        </label>
        <input 
            type="text" 
            placeholder="Введіть текст..." 
            value={task}
            onChange={(e) => setTask(e.target.value)}
        />

        <label style={{fontSize: '14px', marginLeft: '5px', marginTop: '10px', display: 'block', textAlign: 'center'}}>Пріоритет</label>
        <select 
            value={priority} 
            onChange={(e) => setPriority(Number(e.target.value))}
        >
            <option value={4}>Високий - Здаю поточну лабу</option>
            <option value={3}>Середній - Не хочу здавати одним з перших</option>
            <option value={2}>Знижений - Здаю лаби наперед / Здаю багато за раз</option>
            <option value={1}>Низький - Борги / Хочу здавати одним з останніх</option>
        </select>

        <button 
            className="btn btn-primary" 
            style={{width: '100%', marginTop: '20px'}}
            onClick={handleSubmit}
            disabled={isSubmitting}
        >
            {isSubmitting ? 'Відправка...' : 'Додати запис'}
        </button>
      </div>
    </div>
  );
};

export default QueuePage;