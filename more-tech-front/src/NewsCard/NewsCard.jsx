import * as React from 'react';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';
import img from '../assets/news-img.png';
import { useState } from 'react';
import { axiosConfig } from '../utils/axios';
import { useNavigate } from 'react-router-dom';
import Button from '@mui/material/Button';

export function NewsCard(item) {
	let navigate = useNavigate();
	const [likeCount, setLikeCount] = useState('');
	const handleLike = () => {
		axiosConfig
			.post(`/news/${item.item.id}/like`)
			.then((response) => setLikeCount(response.data.likeCount));
	};

	const handleOpenNews = () => {
		navigate(`/news/${item.item.id}`);
	};

	return (
		<Card sx={{ width: 345, marginBottom: 6 }}>
			<img height='194' width='345' src={img} alt='' />
			<CardHeader
				sx={{ height: 75 }}
				avatar={
					<Avatar sx={{ bgcolor: red[500] }} aria-label='recipe'>
						MM
					</Avatar>
				}
				title={item.item.title}
				subheader='Сентябрь 14, 2022'
			/>
			<CardContent sx={{ maxHeight: 120, overflow: 'hidden' }}>
				<Typography variant='body2' color='text.secondary'>
					{item.item.description}
				</Typography>
			</CardContent>
			<CardActions disableSpacing>
				<Button onClick={handleOpenNews} variant='text'>
					Подробнее
				</Button>
				<IconButton onClick={handleLike} aria-label='add to favorites'>
					<ThumbUpIcon />
					{likeCount || item.item.likeCount}
				</IconButton>
			</CardActions>
		</Card>
	);
}
