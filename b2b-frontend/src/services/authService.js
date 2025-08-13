// src/services/authService.js
import axios from 'axios'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

export const registerUser = async (userData) => {
  const response = await axios.post(`${API_BASE_URL}/register`, userData)
  return response.data
}
export const loginUser = async (credentials) => {
  const response = await axios.post(`${API_BASE_URL}/login`, credentials)
  return response.data
}
export const googleLogin = async (credential) => {
  const response = await axios.post(`${API_BASE_URL}/google/login`, { credential });
  return response.data;
};

export const googleRegister = async (credential) => {
  const response = await axios.post(`${API_BASE_URL}/google/register`, { credential });
  return response.data;
};