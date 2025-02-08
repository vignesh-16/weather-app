import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login';
import Home from './components/Home';
import SignUp from './components/SignUp';
import ResetPassword from './components/ResetPassword';
import Account from './components/Account';

function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Login />} />
      </Routes>
      <Routes>
        <Route path='/home' element={<Home />} />
      </Routes>
      <Routes>
        <Route path='/signup' element={<SignUp />} />
      </Routes>
      <Routes>
        <Route path='/forgotpassword' element={<ResetPassword />} />
      </Routes>
      <Routes>
        <Route path='/myAccount' element={<Account />} />
      </Routes>
    </Router> 
  );
}

export default App;
