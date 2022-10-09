import axios from 'axios';
import authHeader from './auth-header';
import { host } from '../utils/axios';

const API_URL = `${host}/user`;

const getPublicContent = () => {
	return axios.get(API_URL + 'all');
};

const getUserBoard = () => {
	return axios.get(API_URL + 'user', { headers: authHeader() });
};

const getModeratorBoard = () => {
	return axios.get(API_URL + 'mod', { headers: authHeader() });
};

const getAdminBoard = () => {
	return axios.get(API_URL + 'admin', { headers: authHeader() });
};

// eslint-disable-next-line import/no-anonymous-default-export
export default {
	getPublicContent,
	getUserBoard,
	getModeratorBoard,
	getAdminBoard,
};
