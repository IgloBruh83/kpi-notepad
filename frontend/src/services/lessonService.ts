import { DayRenderDTO } from '../types';
import { protectedFetch } from './protectedFetch';

export const lessonService = {
  async getDaySchedule(date: string): Promise<DayRenderDTO> {
    const response = await protectedFetch(`/api/lessons/day?date=${date}`, {
    credentials: 'include'
});
    if (!response.ok) {
      throw new Error('Could not load day schedule');
    }
    return response.json();
  },

  async getRangeSchedule(startDate: string, count: number = 9) {
    const response = await protectedFetch(`/api/lessons/range?start=${startDate}&count=${count}`, {
    credentials: 'include'
});
    if (!response.ok) {
      throw new Error('Could not load range of schedules');
    }
    return response.json();
  }
};