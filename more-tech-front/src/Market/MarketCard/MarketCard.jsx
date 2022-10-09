import './MarketCard.css';
import cardImg from '../../assets/tshort.svg';
import { Button } from '@mui/material';
import Rating from '@mui/material/Rating';
import { useNavigate } from 'react-router-dom';
import rublIcon from '../../assets/rubl.svg';

export function MarketCard(props) {
	const navigate = useNavigate();
	const handleMarketMore = () => {
		navigate(`/market/${props.item.id}`);
	};

	return (
		<div className='market-card-wrapper'>
			<img src={cardImg} alt='' />
			<div className='market-card-cost'>
				<div>{props.item.price}</div>
				<img width='20px' height='20px' src={rublIcon} alt='' />
			</div>
			<div className='market-card-title'>{props.item.name}</div>
			<Rating className='market-card-rating' name='read-only' value={4.5} readOnly />
			<Button onClick={handleMarketMore} className='market-card-button' variant='outlined'>
				КУПИТЬ
			</Button>
		</div>
	);
}
