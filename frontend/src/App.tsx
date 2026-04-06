import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import { Layout } from './components/layout/Layout/Layout';
import './App.css';

function App() {
  return (
    <div className="App">
      <Router>
        <Layout isAuthenticated={false} />
      </Router>
    </div>
  );
}

export default App;
