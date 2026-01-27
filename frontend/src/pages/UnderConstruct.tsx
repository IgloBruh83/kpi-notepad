import React from 'react';
// @ts-ignore
import constructionCat from '../assets/img/ConstructionCat.png';

const UnderConstructionPage: React.FC = () => {
  return (
    <div className="centering" style={{ height: '80vh' }}>
      <img 
        src={constructionCat} 
        width="250px" 
        height="auto" 
        alt="Construction Cat" 
      />
      
      <div className="block" style={{ padding: '15px 45px' }}>
        <div className="centering">
          <h2 style={{ fontFamily: '"Arial Black", Gadget, sans-serif' }}>
            UNDER CONSTRUCTION
          </h2>
          <h5>Ця частина сайту ще не готова, бо розробник</h5>
            <h5>намагається вчити React на ходу і нічо не розуміє...</h5>
        </div>
      </div>
    </div>
  );
};

export default UnderConstructionPage;