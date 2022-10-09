export default function authHeader() {
	if (localStorage.getItem('user')) {
		const user = JSON.parse(localStorage.getItem('user'));
	} else {
		const user = null;
	}

	if (user && user.token) {
		return { Authorization: user.token };
	} else {
		return {};
	}
}
