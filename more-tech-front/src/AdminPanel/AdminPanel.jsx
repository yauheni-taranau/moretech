import './Profile.css';
import { useEffect, useState } from 'react';
import { axiosConfig } from '../utils/axios';
import { AuctionCard } from '../AuctionCard/AuctionCard';
import { getUsername } from '../utils/tokenCheck';

export function Profile() {
	const [NFTs, setNFTs] = useState([]);

	useEffect(() => {
		axiosConfig.get('/user/' + getUsername()).then((response) => {
            console.log(response)
        });
	}, []);

	return (
		<div className='auction-wrapper'>
			<div className='auction-page-title'>ПРОФИЛЬ НАХУЙ</div>
			<div className='auction-page-all-item'>
				{NFTs && NFTs.map((item) => <AuctionCard key={item.id} item={item} />)}
			</div>
		</div>
	);
}
