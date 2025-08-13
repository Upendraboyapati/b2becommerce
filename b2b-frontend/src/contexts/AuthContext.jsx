import { createContext, useContext, useState, useEffect } from 'react';
import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(() => {
    const stored = localStorage.getItem('user');
    return stored ? JSON.parse(stored) : null;
  });
  const [token, setToken] = useState(() => localStorage.getItem('token') || null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Attach/remove Authorization header globally
  useEffect(() => {
    if (token) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    } else {
      delete axios.defaults.headers.common['Authorization'];
    }
  }, [token]);

  // Persist user and token
  useEffect(() => {
    user ? localStorage.setItem('user', JSON.stringify(user)) : localStorage.removeItem('user');
    token ? localStorage.setItem('token', token) : localStorage.removeItem('token');
  }, [user, token]);

  const register = async (userData) => {
    try {
      setLoading(true);
      setError(null);
      const response = await axios.post(`${API_BASE_URL}/users/register`, userData, {
        headers: { 'Content-Type': 'application/json' }
      });
      return { success: true, data: response.data };
    } catch (err) {
      const message = err.response?.data?.message || 'Registration failed';
      setError(message);
      console.error('Registration failed:', err);
      return { success: false, message };
    } finally {
      setLoading(false);
    }
  };

  const login = async ({ email, password }) => {
    try {
      setLoading(true);
      setError(null);
      const response = await axios.post(
        `${API_BASE_URL}/users/login`,
        { email, password },
        { headers: { 'Content-Type': 'application/json' } }
      );
      const { token, user } = response.data;
      setToken(token);
      setUser(user);
      return { success: true, data: user };
    } catch (err) {
      const message = err.response?.data?.message || 'Login failed';
      setError(message);
      console.error('Login failed:', err);
      return { success: false, message };
    } finally {
      setLoading(false);
    }
  };

  const loginWithGoogle = async (credential) => {
    try {
      setLoading(true);
      setError(null);
      const response = await axios.post(
        `${API_BASE_URL}/google/login`,
        { credential },
        { headers: { 'Content-Type': 'application/json' } }
      );
      const { token, user } = response.data;
      setToken(token);
      setUser(user);
      return { success: true, data: user };
    } catch (err) {
      const message = err.response?.data?.message || 'Google login failed';
      setError(message);
      console.error('Google login failed:', err);
      return { success: false, message };
    } finally {
      setLoading(false);
    }
  };

  const registerWithGoogle = async (credential) => {
    try {
      setLoading(true);
      setError(null);
      const response = await axios.post(
        `${API_BASE_URL}/google/register`,
        { credential },
        { headers: { 'Content-Type': 'application/json' } }
      );
      const { token, user } = response.data;
      setToken(token);
      setUser(user);
      return { success: true, data: user };
    } catch (err) {
      const message = err.response?.data?.message || 'Google registration failed';
      setError(message);
      console.error('Google registration failed:', err);
      return { success: false, message };
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    setUser(null);
    setToken(null);
    localStorage.clear();
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        token,
        login,
        loginWithGoogle,
        logout,
        register,
        registerWithGoogle,
        loading,
        error,
        isAuthenticated: !!token
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
