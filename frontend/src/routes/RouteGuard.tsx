import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';

interface RouteGuardProps {
  children: React.ReactNode;
  requiredRole?: string;
  isAuthenticated?: boolean;
  userRole?: string;
}

export const RouteGuard: React.FC<RouteGuardProps> = ({ 
  children, 
  requiredRole,
  isAuthenticated = false,
  userRole 
}) => {
  const location = useLocation();

  // Check if user is authenticated
  if (!isAuthenticated) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  // Check if user has required role
  if (requiredRole && userRole !== requiredRole) {
    return <Navigate to="/error" replace />;
  }

  return <>{children}</>;
};
