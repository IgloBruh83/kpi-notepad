import React, { useEffect, useState, RefObject } from 'react';

export const useContainerWidth = <T extends HTMLElement>(ref: RefObject<T | null>) => {
  const [width, setWidth] = useState(0);

  useEffect(() => {
    const element = ref.current;
    if (!element) return;
    const observer = new ResizeObserver((entries) => {
      if (entries[0].contentRect) {
        setWidth(entries[0].contentRect.width);
      }
    });
    observer.observe(element);
    return () => observer.disconnect();
  }, [ref]);
  return width;
};