import './App.css';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Home from './components/Home';
import SignUp from './components/SignUp';
import ResetPassword from './components/ResetPassword';
import Account from './components/Account';

const ProtectedRoute = ({ children }) => {
  const token = localStorage.getItem("authToken");
  return token ? children : <Navigate to="/" />;
};

function App() {
  return (
    <Router>
      <Routes>

        <Route path='/' element={<Navigate to="/login" />} />
  
        <Route path='/login' element={<Login />} />
     
        <Route path='/home' element={<ProtectedRoute><Home /></ProtectedRoute>} />
      
        <Route path='/signup' element={<ProtectedRoute><SignUp /></ProtectedRoute>} />
      
        <Route path='/forgotpassword' element={<ProtectedRoute><ResetPassword /></ProtectedRoute>} />
      
        <Route path='/myAccount' element={<ProtectedRoute><Account /></ProtectedRoute>} />
        
      </Routes>
    </Router> 
  );
}

export default App;
