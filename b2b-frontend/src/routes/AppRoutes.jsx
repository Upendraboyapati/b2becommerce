import { Routes, Route } from 'react-router-dom'
import HomePage from '../pages/HomePage'
import AboutPage from '../pages/AboutPage'
import RegisterPage from '../pages/RegisterPage'
import LoginPage from '../pages/LoginPage'
import PrivateRoute from '../components/PrivateRoute'
import DashboardPage from '../pages/DashboardPage'

function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/about" element={<AboutPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/dashboard" element={<DashboardPage />} />
      
      {/* Protect private routes like this: */}
      <Route path="/dashboard" element={
        <PrivateRoute>
          <div>Dashboard Page</div>
        </PrivateRoute>
      } />
    </Routes>
  )
}

export default AppRoutes