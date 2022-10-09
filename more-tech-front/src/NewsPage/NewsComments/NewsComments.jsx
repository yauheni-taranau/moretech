import IconButton from '@mui/material/IconButton';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';
import { axiosConfig } from '../../utils/axios';
import Avatar from '@mui/material/Avatar';
import './NewsComments.css';
import { useState } from 'react';

export const NewsComments = (props) => {
	const [likeCount, setLikeCount] = useState('');
	const handleLike = () => {
		axiosConfig
			.post(`/news/${props.newsId}/comments/${props.comment.id}/like/`)
			.then((response) => setLikeCount(response.data.likeCount));
	};

	return (
		<div className='news-blog-comment-wrapper'>
			<Avatar sx={{ bgcolor: 'orange' }}>{props.comment.username}</Avatar>
			<div className='news-blog-comments-input-comp'>{props.comment.text}</div>
			<IconButton onClick={handleLike} aria-label='add to favorites'>
				<ThumbUpIcon />
				{likeCount || props.comment.likeCount}
			</IconButton>
		</div>
	);
};
