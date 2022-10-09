import { useEffect, useState } from 'react';
import './Main.css';
import { axiosConfig } from '../utils/axios';
import { NewsCard } from '../NewsCard/NewsCard';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import Paper from '@mui/material/Paper';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Avatar from '@mui/material/Avatar';
import Badge from '@mui/material/Badge';
import { red } from '@mui/material/colors';
import a from '../assets/a.jpg';
import { CourseCard } from '../CourseCard/CourseCard';

import './Main.css';

export function Main() {
	const [news, setNews] = useState([]);
	const [course, setCourse] = useState([]);

	useEffect(() => {
		axiosConfig.get('/news').then((response) => setNews(response));
		axiosConfig.get('/courses').then((response) => setCourse(response));
	}, []);

	return (
		<div className='main-wrapper'>
			<div className='profileContent'>
				<div className='profileAvatar'>
					<div className='circle-border'>
						<Badge
							overlap='circular'
							anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
							badgeContent={<div className='numberCircle'>80</div>}
						>
							<Avatar
								sx={{ bgcolor: red[500], width: 150, height: 150 }}
								aria-label='recipe'
								src={a}
							></Avatar>
						</Badge>
					</div>
					<div className='avatarName'>Марина Иванова</div>
				</div>
				<div className='profileBoard'>
					<TableContainer component={Paper}>
						<Table sx={{ minWidth: 650 }} aria-label='simple table'>
							<TableHead>
								<TableRow>
									<TableCell align='center'>Номер</TableCell>
									<TableCell align='center'>Имя пользователя</TableCell>
									<TableCell align='center'>Логин</TableCell>
									<TableCell align='center'>Уровень</TableCell>
								</TableRow>
							</TableHead>
							<TableBody>
								<TableRow>
									<TableCell align='center'>1</TableCell>
									<TableCell align='center'>Марина Иванова</TableCell>
									<TableCell align='center'>marinafree</TableCell>
									<TableCell align='center'>80</TableCell>
								</TableRow>
								<TableRow>
									<TableCell align='center'>2</TableCell>
									<TableCell align='center'>Жмышенко Валерий</TableCell>
									<TableCell align='center'>jmihvalera</TableCell>
									<TableCell align='center'>79</TableCell>
								</TableRow>
								<TableRow>
									<TableCell align='center'>3</TableCell>
									<TableCell align='center'>Андрей Костин</TableCell>
									<TableCell align='center'>kostyn</TableCell>
									<TableCell align='center'>30</TableCell>
								</TableRow>
								<TableRow>
									<TableCell align='center'>4</TableCell>
									<TableCell align='center'>Володимир Зеленский</TableCell>
									<TableCell align='center'>pamagiti</TableCell>
									<TableCell align='center'>0</TableCell>
								</TableRow>
							</TableBody>
						</Table>
					</TableContainer>
				</div>
			</div>
			<div className='news-title'>Новости</div>
			<div className='news-market-wrapper'>
				<div className='main-news-wrapper'>
					{news.data && news.data.map((item) => <NewsCard item={item} key={item.id} />)}
				</div>
			</div>
			<div className='news-title'>Курсы</div>
			<div className='news-market-wrapper'>
				<div className='main-news-wrapper'>
					{course.data &&
						course.data.map((item) => <CourseCard item={item} key={item.id} />)}
				</div>
			</div>
		</div>
	);
}
