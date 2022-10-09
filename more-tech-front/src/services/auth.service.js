import axios from 'axios';
import { host } from '../utils/axios';

const API_URL = `${host}/user`;

const login = (username, password) => {
	return axios
		.post(API_URL + '/login', {
			username,
			password,
		})
		.then((response) => {
			if (response.data.token) {
				localStorage.setItem('user', JSON.stringify(response.data));
			}

			return response.data;
		});
};

const logout = () => {
	localStorage.removeItem('user');
};

const getCurrentUser = () => {
	return JSON.parse(localStorage.getItem('user'));
};

// eslint-disable-next-line import/no-anonymous-default-export
export default {
	login,
	logout,
	getCurrentUser,
};
