import logo from '../assets/logo.svg';
import './Header.css';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';
import { axiosConfig } from '../utils/axios';
import { getUsername } from '../utils/tokenCheck';
import { useEffect, useState } from 'react';
import rublIcon from '../assets/rubl.svg';

export function Header() {
	const [balance, setBalance] = useState(0);

	let navigate = useNavigate();

	const handleLogoClick = () => {
		navigate('/');
	};

	const handlePlayGame = (e) => {
		axiosConfig
			.post('/ttt', { name: 'QWEQWE' })
			.then((response) => navigate('/game/' + response.data.id));
	};

	const handleAuction = () => {
		navigate('/auction');
	};

	const handleProfile = () => {
		navigate('/profile');
	};

	const handleMarket = () => {
		navigate('/market');
	};

	const handleNews = () => {
		navigate('/');
	};

	const handleCourse = () => {
		navigate('/course');
	};

	const handleA = () => {
		navigate('/anekdots');
	};

	useEffect(() => {
		axiosConfig.get('/user/' + getUsername()).then((data) => {
			setBalance(data.data.balance);
		});
	}, []);

	return (
		<div className='header'>
			<div onClick={handleLogoClick}>
				<img src={logo} alt='' />
			</div>
			<div>
				<span className='header-link' onClick={handleNews}>
					Новости
				</span>
				<span className='header-link' onClick={handleA}>Анекдоты</span>
				<span className='header-link' onClick={handleMarket}>
					Маркетплейс
				</span>
				<span className='header-link' onClick={handleCourse}>
					Курсы
				</span>
				<span className='header-link' onClick={handlePlayGame}>
					Игры
				</span>
				<span className='header-link' onClick={handleAuction}>
					Аукцион
				</span>
			</div>
			<div className='header-balance-profile'>
				<div className='header-balance-block'>
					<div>Balance: {balance}</div>
					<img width='20px' height='20px' src={rublIcon} alt='' />
				</div>

				<Button variant='contained' onClick={handleProfile}>
					Профиль
				</Button>
			</div>
		</div>
	);
}
