import './CourseCard.css';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import courseImg from '../assets/course.png';

export function CourseCard(props) {
	const navigate = useNavigate();
	const handleCourseMore = () => {
		navigate(`/course/${props.item.id}`);
	};

	return (
		<div className='course-card-wrapper'>
			<img className='course-card-img' src={courseImg} alt='' />
			<div className='course-card-block'>
				<div className='course-card-date'>Сентябрь 14, 2022</div>
				<div className='course-card-title'>{props.item.title}</div>
                <div className='course-card-description'>{props.item.description}</div>
				<Button
					onClick={handleCourseMore}
					className='course-card-button'
					variant='contained'
				>
					Подробнее
				</Button>
			</div>
		</div>
	);
}
