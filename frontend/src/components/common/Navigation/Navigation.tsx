import React from 'react';
import { Link } from 'react-router-dom';

interface RouteItem {
  path: string;
  component: React.ComponentType;
  protected?: boolean;
}

interface NavigationProps {
  routes: RouteItem[];
  currentPath: string;
  isAuthenticated?: boolean;
}

export const Navigation: React.FC<NavigationProps> = ({ 
  routes, 
  currentPath,
  isAuthenticated = false 
}) => {
  const visibleRoutes = routes.filter(route => 
    !route.protected || isAuthenticated
  );

  return (
    <nav className="navigation">
      <ul className="navigation-list">
        {visibleRoutes.map((route) => (
          <li key={route.path} className="navigation-item">
            <Link 
              to={route.path}
              className={`navigation-link ${
                currentPath === route.path ? 'active' : ''
              }`}
            >
              {route.path === '/' && 'Dashboard'}
              {route.path === '/settings' && 'Settings'}
              {route.path === '/login' && 'Login'}
            </Link>
          </li>
        ))}
      </ul>
    </nav>
  );
};
