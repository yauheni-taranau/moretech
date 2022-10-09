import './HistoryLog.css';
import Avatar from '@mui/material/Avatar';

export function HistoryLog(props) {
	let date = new Date(props.item.createdAt);
	let hours = date.getHours();
	let minutes = "0" + date.getMinutes();
	let seconds = "0" + date.getSeconds();
	return (
		<div className='history-log-wrapper'>
			<div className='history-log-avatar'>
				<Avatar sx={{ bgcolor: 'orange', mr: '16px' }} aria-label='recipe'>
					MM
				</Avatar>
				<div className='history-log-block'>
					<div className='history-log-bet'>{props.item.value}</div>
					<div className='history-log-username'>{props.item.username}</div>
				</div>
			</div>
			<div className='history-log-time'>
				{hours}:{minutes.substr(-2)}:{seconds.substr(-2)}
			</div>
		</div>
	);
}
