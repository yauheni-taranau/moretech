import React, { useState, useRef } from 'react';
import './Login.css';
import logo from '../assets/logo.svg';
import { useNavigate } from 'react-router-dom';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

import AuthService from '../services/auth.service';

export const Login = () => {
	let navigate = useNavigate();

	const form = useRef();

	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');

	const onChangeUsername = (e) => {
		const username = e.target.value;
		setUsername(username);
	};

	const onChangePassword = (e) => {
		const password = e.target.value;
		setPassword(password);
	};

	const handleLogin = (e) => {
		e.preventDefault();

		AuthService.login(username, password).then(
			() => {
				navigate('/');
				window.location.reload();
			},
			(error) => {}
		);
	};

	return (
		<div className='wrapper-login'>
			<form className='form' onSubmit={handleLogin} ref={form}>
				<div className='login-logo'>
					<img src={logo} alt='' />
				</div>
				<div className='form-group'>
					<TextField
						label='Логин'
						type='text'
						className='form-control'
						name='username'
						value={username}
						onChange={onChangeUsername}
					/>
				</div>

				<div className='form-group'>
					<TextField
						label='Пароль'
						type='password'
						className='form-control'
						name='password'
						value={password}
						onChange={onChangePassword}
					/>
				</div>

				<div className='form-group'>
					<Button className='login-button' onClick={handleLogin} variant='contained'>
						<span>Войти</span>
					</Button>
				</div>
			</form>
			<div className='login-img'></div>
		</div>
	);
};
