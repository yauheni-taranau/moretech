import './AuctionCard.css';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';
import rublIcon from '../assets/rubl.svg';
import { host } from '../utils/axios';

export function AuctionCard(props) {
	const navigate = useNavigate();

	const handleMakeDeal = () => {
		navigate(`/auction/${props.item.id}`);
	};

	return (
		<div className='auction-card-wrapper'>
			<img
				className='auction-card-img'
				src={`${host}/images/${props.item.nftImageId}`}
				alt=''
			/>
			<div className='auction-card-title'>{props.item.name}</div>
			<div className='auction-card-cost-block'>
				<div className='auction-card-label'>Начальная цена</div>
				<div className='auction-card-cost'>
					<div>{props.item.initialPrice}</div>
					<img width='20px' height='20px' src={rublIcon} alt='' />
				</div>
			</div>
			<Button className='auction-card-button' onClick={handleMakeDeal} variant='outlined'>
				Сделать ставку
			</Button>
		</div>
	);
}
