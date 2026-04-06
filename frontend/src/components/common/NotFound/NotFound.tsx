import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

interface NotFoundProps {
  message?: string;
  showGoHome?: boolean;
}

export const NotFound: React.FC<NotFoundProps> = ({ 
  message = 'Page not found',
  showGoHome = true 
}) => {
  const navigate = useNavigate();

  return (
    <div className="not-found">
      <div className="not-found-content">
        <h1 className="not-found-title">404</h1>
        <p className="not-found-message">{message}</p>
        
        {showGoHome && (
          <div className="not-found-actions">
            <button 
              onClick={() => navigate('/')}
              className="not-found-button primary"
            >
              Go to Dashboard
            </button>
            <button 
              onClick={() => window.history.back()}
              className="not-found-button secondary"
            >
              Go Back
            </button>
          </div>
        )}
      </div>
    </div>
  );
};
