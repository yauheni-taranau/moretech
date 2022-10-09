import './MarketInfo.css';
import * as React from 'react';
import { useEffect, useState } from 'react';
import { axiosConfig } from '../../utils/axios';
import { useParams } from 'react-router-dom';
import cardImg from '../../assets/tshort.svg';
import { Button } from '@mui/material';
import Rating from '@mui/material/Rating';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import rublIcon from '../../assets/rubl.svg';

export function MarketInfo() {
	const [item, setItem] = useState({});
	const [open, setOpen] = useState(false);

	const params = useParams();

	useEffect(() => {
		axiosConfig
			.get(`/market/items/${params.marketId}`)
			.then((response) => setItem(response.data));
	}, [params.marketId]);

	const handleBuyItem = () => {
		axiosConfig.post(`/market/items/${params.marketId}/buy`);
		setOpen(true);
	};

	const action = (
		<React.Fragment>
			<IconButton size='small' aria-label='close' color='inherit'>
				<CloseIcon fontSize='small' />
			</IconButton>
		</React.Fragment>
	);

	return (
		<div className='market-item-wrapper'>
			<img className='market-item-img' src={cardImg} alt='' />
			<div className='market-item-info'>
				<div className='market-item-title'>{item.name}</div>
				<div className='market-item-rating'>
					<Rating className='market-item-ratings' name='read-only' value={4.5} readOnly />
					<div className='market-item-review'>4 Отзыва</div>
					<div className='market-item-add-review'>Оставить отзыв</div>
				</div>
				<div className='market-item-cost'>
					<div>{item.price}</div> <img width='20px' height='20px' src={rublIcon} alt='' />
				</div>
				<Select value={10} label='Размер' className='market-item-size'>
					<MenuItem value={10}>S</MenuItem>
					<MenuItem value={20}>M</MenuItem>
					<MenuItem value={30}>L</MenuItem>
				</Select>
				<Button variant='contained' onClick={handleBuyItem}>
					КУПИТЬ
				</Button>
			</div>
			<Snackbar
				open={open}
				autoHideDuration={3000}
				message='Спасибо за Покупку:)'
				action={action}
			/>
		</div>
	);
}
