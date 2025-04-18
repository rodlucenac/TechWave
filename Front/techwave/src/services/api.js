import axios from 'axios';

const api = axios.create({
  baseURL: '/api'    // <-- adiciona aqui o /api
});

export default api;
