import { useEffect, useState } from 'react';
import { axiosConfig } from '../utils/axios';
import { useParams } from 'react-router-dom';
import { ACard } from './ACard/ACard';

export function A() {
	const params = useParams();
	const [anekdots, setAnekdots] = useState([]);

	useEffect(() => {
		axiosConfig.get(`/anekdot`).then((response) => {
			console.log(setAnekdots(response.data));
		});
	}, [params.newsId]);

	return (
		<div className='news-wrapper'>
			<div className='main-news-wrapper'>
				{anekdots.map((a) => (
					<ACard item={a} key={a.id} />
				))}
			</div>
		</div>
	);
}
