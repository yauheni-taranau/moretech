import './CourseInfo.css';
import { useEffect, useState } from 'react';
import { axiosConfig } from '../utils/axios';
import { useParams } from 'react-router-dom';
import rublIcon from '../assets/rubl.svg';
import { Button } from '@mui/material';

export function CourseInfo() {
	const [course, setCourse] = useState([]);
	const params = useParams();

	useEffect(() => {
		axiosConfig.get(`/courses/${params.courseId}`).then((response) => setCourse(response.data));
	}, [params.courseId]);
	console.log(course);
	return (
		<div className='course-info-wrapper'>
			<div className='course-info-header'>
				<div className='course-info-header-work'>Профессия</div>
				<div className='course-info-header-title'>{course.title}</div>
				<div className='course-info-header-description'>{course.description}</div>
				<div className='course-info-header-start'>
					<div className='course-info-header-start-block'>
						<div className='course-info-header-prize'>Награда</div>
						<div>{course.rewardAmount}</div>
						<img
							className='course-info-header-start-img'
							width='20px'
							height='20px'
							src={rublIcon}
							alt=''
						/>
					</div>
					<Button
						sx={{ backgroundColor: '#FFFFFF', width: '200px' }}
						className='course-info-header-start-btn'
						variant='outlined'
					>
						Пройти курс
					</Button>
				</div>
				</div>
				<div className='course-main'>
					<div className='course-main-title'>Программа</div>
					<div className='course-main-description'>
						Программа курса предполагает самостоятельное изучение материала и основана
						на современных методиках дистанционного образования. Для слушателей
						разработан адаптированный лекционный материал с учетом актуальной
						научно-методической базы. Вся текстовая информация сопровождается обучающими
						роликами, проводятся вебинары и онлайн-конференции, также есть специальный
						раздел для офлайн-консультаций и ответов на вопрось.
					</div>
				</div>
		
		</div>
	);
}
