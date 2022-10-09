import './CoursePage.css';
import { useEffect, useState } from 'react';
import { axiosConfig } from '../utils/axios';
import { CourseCard } from '../CourseCard/CourseCard';

export function CoursePage() {
	const [course, setCourse] = useState([]);

	useEffect(() => {
		axiosConfig.get('/courses').then((response) => setCourse(response.data));
	}, []);

	return (
		<div className='course-wrapper'>
			<div className='course-page-title'>Все направления</div>
			<div className='course-page-all-item'>
				{course && course.map((item) => <CourseCard key={item.id} item={item} />)}
			</div>
		</div>
	);
}
