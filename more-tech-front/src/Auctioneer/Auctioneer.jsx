import './Auctioneer.css';
import { useEffect, useState } from 'react';
import { axiosConfig } from '../utils/axios';
import rublIcon from '../assets/rubl.svg';
import { useParams } from 'react-router-dom';
import { Button } from '@mui/material';
import { HistoryLog } from './HistoryLog/HistoryLog';
import * as StompJs from 'stomp-websocket';
import * as SockJs from 'sockjs-client';
import { host } from '../utils/axios';

export function Auctioneer() {
	const [NFTs, setNFTs] = useState([]);
	const [time, setTime] = useState(10);
	const [bids, setBids] = useState([]);
	const [currentBid, setCurrentBid] = useState([]);
	const [showWinner, setShowWinner] = useState(false);
	const [socketInit, setSocketInit] = useState(false);

	const params = useParams();

	const handleBet = (bet) => {
		axiosConfig.post('/auction/lots/' + params.aucId + '/bid', {
			bid: NFTs.step * bet + currentBid,
		});
	};

	useEffect(() => {
		if (socketInit === false) {
			let socket = new SockJs(`${host}/game`);
			let stompClient = StompJs.over(socket);
			stompClient.connect({}, function (frame) {
				stompClient.subscribe('/topic/' + params.aucId + '/auc', function (lol) {
					let msg = JSON.parse(lol.body);

					if (msg.type === 'BID') {
						setBids(msg.history);
						setCurrentBid(msg.currentBid);
						setTime(10);
					} else if (msg.type === 'SOLD') {
						setBids(msg.history);
						setShowWinner(true);
						setTime(0);
						setShowWinner(true);
					}
				});
			});

			axiosConfig.get(`/auction/lots/${params.aucId}`).then((response) => {
				setNFTs(response.data);
				setBids(response.data.bids);
				setCurrentBid(response.data.initialPrice);
			});
			setSocketInit(true);
		}

		const interval = setInterval(() => {
			if (time <= 0) {
				clearInterval(interval);
				axiosConfig.post('/auction/lots/' + params.aucId + '/finish');
			} else {
				setTime((time) => time - 1);
			}
		}, 1000);
		return () => clearInterval(interval);
	}, [time]);
	return (
		<div className='auctioneer-wrapper'>
			<div className='auctioneer-left'>
				<div className='auctioneer-info'>
					ID: {NFTs.id} by {NFTs.creatorUsername}
				</div>
				<div className='auctioneer-title'>{NFTs.title}</div>
				<div className='auctioneer-description'>{NFTs.description}</div>
			</div>
			<img className='auctioneer-image' src={`${host}/images/${NFTs.nftImageId}`} alt='' />
			<div>
				<div className='auctioneer-head'>
					<div className='auctioneer-head-bet-block'>
						<div className='auctioneer-live-label'>ТЕКУЩАЯ СТАВКА</div>
						<div className='auctioneer-live-bet'>
							<span>{currentBid && currentBid}</span>
							<img width='20px' height='20px' src={rublIcon} alt='' />
						</div>
					</div>
					<div>
						<div className='auctioneer-live-label'>ЗАКАНЧИВАЕТСЯ ЧЕРЕЗ</div>
						<div className='auctioneer-live-timer'>
							<span>{time}</span>
						</div>
					</div>
				</div>
				<div className='auctioneer-head-step'>
					<div className='auctioneer-live-label'>ШАГ АУКЦИОНА</div>
					<div className='auctioneer-live-bet'>
						<span>{NFTs && NFTs.step}</span>
						<img width='20px' height='20px' src={rublIcon} alt='' />
					</div>
				</div>
				<div className='auctioneer-head-step'>
					<Button className='bet-btn' variant='outlined' onClick={() => handleBet(1)}>
						X1
					</Button>
					<Button className='bet-btn' variant='outlined' onClick={() => handleBet(2)}>
						X2
					</Button>
					<Button className='bet-btn' variant='outlined' onClick={() => handleBet(3)}>
						X3
					</Button>
					<Button className='bet-btn' variant='outlined' onClick={() => handleBet(4)}>
						X4
					</Button>
					<Button className='bet-btn' variant='outlined' onClick={() => handleBet(5)}>
						X5
					</Button>
				</div>
				<div className='auctioneer-head-step'>
					<div className='auctioneer-live-label'>ИСТОРИЯ СТАВОК</div>
					<div className='auctioneer-bet-history'>
						{bids &&
							bids.map((item) => <HistoryLog key={item.createdAt[6]} item={item} />)}
					</div>
				</div>
				{showWinner && <h1>Победитель: {bids[0].username}</h1>}
			</div>
		</div>
	);
}
