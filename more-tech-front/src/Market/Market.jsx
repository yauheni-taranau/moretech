import './Market.css';
import { useEffect, useState } from 'react';
import { axiosConfig } from '../utils/axios';
import headerLogo from '../assets/marketHeader.svg';
import { MarketCard } from './MarketCard/MarketCard';

export function Market() {
	const [items, setItems] = useState([]);

	useEffect(() => {
		axiosConfig.get('/market/items').then((response) => setItems(response.data));
	}, []);

	return (
		<div className='market-wrapper'>
			<div className='market-header'>
				<div>
					<div className='market-header-title'>
						Лучшие предложения где можете обменять токены
					</div>
					<div className='market-header-description'>
						Лучшие предложения где можете обменять токены
					</div>
				</div>
				<img src={headerLogo} alt='' />
			</div>
			<div className='market-wrapper-main'>
				<div className='market-wrapper-main-title'>Популярные товары</div>
				<div className='market-main-card-wrapper'>
					{items && items.map((item) => <MarketCard key={item.id} item={item} />)}
				</div>
			</div>
		</div>
	);
}
