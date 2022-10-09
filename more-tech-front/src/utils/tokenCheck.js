export const tokenCheck = () => {
	return !!localStorage.getItem('user');
};

export const tokenGet = () => {
	if (localStorage.getItem('user')) {
		return JSON.parse(localStorage.getItem('user')).token;
	}
};

export const getUsername = () => {
	return JSON.parse(atob(tokenGet())).username;
};
