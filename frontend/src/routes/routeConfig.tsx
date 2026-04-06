import React from 'react';
import { useLocation } from 'react-router-dom';

interface RouteConfig {
  path: string;
  name: string;
  component: React.ComponentType;
  protected: boolean;
  icon?: string;
}

export const routes: RouteConfig[] = [
  {
    path: '/',
    name: 'Dashboard',
    component: React.lazy(() => import('../pages/Dashboard/Dashboard')),
    protected: true,
    icon: 'dashboard'
  },
  {
    path: '/settings',
    name: 'Settings',
    component: React.lazy(() => import('../pages/Settings/Settings')),
    protected: true,
    icon: 'settings'
  },
  {
    path: '/login',
    name: 'Login',
    component: React.lazy(() => import('../pages/Login/Login')),
    protected: false,
    icon: 'login'
  },
  {
    path: '/error',
    name: 'Error',
    component: React.lazy(() => import('../pages/Error/Error')),
    protected: false,
    icon: 'error'
  }
];

export const useCurrentRoute = () => {
  const location = useLocation();
  
  const currentRoute = routes.find(route => 
    route.path === location.pathname
  );
  
  return {
    currentRoute,
    isProtected: currentRoute?.protected || false,
    routeName: currentRoute?.name || 'Unknown'
  };
};
