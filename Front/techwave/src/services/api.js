import axios from 'axios';

const api = axios.create({
  baseURL: '/api' //Vai virar localhost8080
});

export default api;
