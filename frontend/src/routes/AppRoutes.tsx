import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { Dashboard } from '../pages/Dashboard/Dashboard';
import { Settings } from '../pages/Settings/Settings';
import { Login } from '../pages/Login/Login';
import { Error } from '../pages/Error/Error';
import { NotFound } from '../components/common/NotFound';
import { ProtectedRoute } from '../components/common/ProtectedRoute';

interface AppRoutesProps {
  isAuthenticated?: boolean;
}

export const AppRoutes: React.FC<AppRoutesProps> = ({ 
  isAuthenticated = false 
}) => {
  return (
    <Routes>
      {/* Public Routes */}
      <Route path="/login" element={<Login />} />
      <Route path="/error" element={<Error />} />
      
      {/* Protected Routes */}
      <Route 
        path="/" 
        element={
          <ProtectedRoute isAuthenticated={isAuthenticated}>
            <Dashboard />
          </ProtectedRoute>
        } 
      />
      
      <Route 
        path="/settings" 
        element={
          <ProtectedRoute isAuthenticated={isAuthenticated}>
            <Settings />
          </ProtectedRoute>
        } 
      />
      
      {/* Catch all route */}
      <Route path="/404" element={<NotFound />} />
      <Route path="*" element={<Navigate to="/404" replace />} />
    </Routes>
  );
};
