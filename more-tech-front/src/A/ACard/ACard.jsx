import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';
import { useState } from 'react';
import { axiosConfig } from '../../utils/axios';
import { useNavigate } from 'react-router-dom';

export function ACard(item) {
	let navigate = useNavigate();
	const [likeCount, setLikeCount] = useState('');
	const handleLike = () => {
		axiosConfig
			.post(`/news/${item.item.id}/like`)
			.then((response) => setLikeCount(response.data.likes));
	};

	const handleOpenNews = () => {
		navigate(`/news/${item.item.id}`);
	};

	return (
		<Card sx={{ width: 345, marginBottom: 6 }}>
			<CardContent sx={{ maxHeight: 120, overflow: 'hidden' }}>
				<Typography variant='body2' color='text.secondary'>
					{item.item.text}
				</Typography>
			</CardContent>
			<CardActions disableSpacing>
				<IconButton aria-label='add to favorites'>
					<ThumbUpIcon />
					{likeCount || item.item.likes}
				</IconButton>
			</CardActions>
		</Card>
	);
}
