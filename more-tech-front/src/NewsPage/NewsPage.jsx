import './NewsPage.css';
import Button from '@mui/material/Button';
import { useEffect, useState } from 'react';
import { axiosConfig } from '../utils/axios';
import Avatar from '@mui/material/Avatar';
import { useParams } from 'react-router-dom';
import { NewsComments } from './NewsComments/NewsComments';

export function NewsPage() {
	const params = useParams();
	const [newsInfo, setNewsInfo] = useState({});
	const [comment, setComment] = useState('');
	const [newsComment, setNewsComment] = useState([]);

	const onChangeComment = (e) => {
		const comment = e.target.value;
		setComment(comment);
	};

	const handleSentComment = () => {
		axiosConfig.post(`/news/${params.newsId}/comments`, { text: comment }).then((response) => {
			setComment('');
			axiosConfig.get(`/news/${params.newsId}/comments`).then((response) => {
				setNewsComment(response.data);
			});
		});
	};

	useEffect(() => {
		axiosConfig.get(`/news/${params.newsId}`).then((response) => setNewsInfo(response.data));
		axiosConfig
			.get(`/news/${params.newsId}/comments/`)
			.then((response) => setNewsComment(response.data));
	}, [params.newsId]);

	return (
		<div className='news-wrapper'>
			<div className='news-bg-img'>
				<div className='news-bg-block'>
					<div className='news-bg-title'>{newsInfo.title}</div>
					<div className='news-bg-date'>Сентябрь 14, 2022</div>
				</div>
			</div>
			<div className='news-blog-title'>{newsInfo.title}</div>
			<div className='news-blog-description'>{newsInfo.description}</div>
			<div className='news-blog-comments-count'>Коментарии ({newsComment.length})</div>
			<div className='news-blog-comments-wrapper'>
				{newsComment &&
					newsComment.map((comment) => (
						<NewsComments newsId={params.newsId} comment={comment} key={comment.id} />
					))}
			</div>
			<div className='news-blog-comments-input'>
				<Avatar sx={{ bgcolor: 'orange' }}>MM</Avatar>
				<input
					onChange={onChangeComment}
					value={comment}
					placeholder='Написать комментарий...'
					className='news-blog-comments-input-comp'
				/>
				<Button onClick={handleSentComment}>Отправить</Button>
			</div>
		</div>
	);
}
