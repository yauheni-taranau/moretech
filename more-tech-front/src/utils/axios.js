import axios from 'axios';
import { tokenGet } from './tokenCheck';
export const host = 'http://192.168.0.167:8080';

axios.defaults.headers.common['Authorization'] = tokenGet();
export const axiosConfig = axios.create({
	baseURL: host,
});

axiosConfig.defaults.headers.common['Authorization'] = tokenGet();
