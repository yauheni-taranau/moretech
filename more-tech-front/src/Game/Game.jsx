import './Game.css';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import * as StompJs from 'stomp-websocket';
import * as SockJs from 'sockjs-client';
import { getUsername } from '../utils/tokenCheck';
import { axiosConfig } from '../utils/axios';
import { host } from '../utils/axios';

export function Game() {
	const [currentUsername, setCurrentUsername] = useState(null);
	const [mySymbol, setMySymbol] = useState(null);
	const [clicked, setClicked] = useState(false);
	const [bid, setBid] = useState(0);
	const [name, setName] = useState('test');
	const params = useParams();

	const handleClick = (e) => {
		let cellId = e.target.getAttribute('id');

		if (currentUsername == getUsername() && !clicked) {
			e.target.innerText = mySymbol;
			setClicked(true);
			axiosConfig.post('/ttt/' + params.gameId + '/turn', {
				cellId: cellId,
			});
		}

		// axiosConfig.post('/ttt', { "name": "QWEQWE" }).then((response) => navigate('/game/' + response.data.id));
	};

	useEffect(() => {
		let join = window.location.href.includes('join');

		if (join) {
			axiosConfig.post('/ttt/' + params.gameId + '/join', {});
		}

		axiosConfig.get('/ttt/' + params.gameId).then((response) => {
			setBid(response.data.bid);
			setName(response.data.name);
		});
		let socket = new SockJs(`${host}/game`);
		let stompClient = StompJs.over(socket);
		stompClient.connect({}, function (frame) {
			stompClient.subscribe('/topic/' + params.gameId + '/events', function (lol) {
				let message = JSON.parse(lol.body);
				if (message.gameEvent == 'PLAYER_JOINED') {
					setCurrentUsername(message.currentTurnUsername);
					let s = message.currentTurnType;
					if (s == 'KRESTIK') {
						setMySymbol('X');
					} else {
						setMySymbol('O');
					}
				}
				if (message.gameEvent == 'TURN') {
					setCurrentUsername(message.currentTurnUsername);
					setClicked(false);
					let s = message.currentTurnType;
					if (s == 'KRESTIK') {
						setMySymbol('X');
					} else {
						setMySymbol('O');
					}
					var symbol = null;
					if (message.currentTurnType == 'KRESTIK') {
						symbol = 'O';
					} else {
						symbol = 'X';
					}
					document.getElementById(message.cellId).innerText = symbol;
				}
				if (message.gameEvent == 'WIN') {
					document.getElementById('ann').innerText =
						'Поздравляем, ' + message.usernameWinner + '!';
					document.getElementById('ann').classList.remove('hide');
					let s = message.currentTurnType;
					if (s == 'KRESTIK') {
						setMySymbol('X');
					} else {
						setMySymbol('O');
					}
					var symbol = null;
					if (message.currentTurnType == 'KRESTIK') {
						symbol = 'O';
					} else {
						symbol = 'X';
					}
					document.getElementById(message.cellId).innerText = symbol;
				}
				if (message.gameEvent == 'DRAW') {
					document.getElementById('ann').innerText = 'Ничья :(';
					document.getElementById('ann').classList.remove('hide');
					let s = message.currentTurnType;
					if (s == 'KRESTIK') {
						setMySymbol('X');
					} else {
						setMySymbol('O');
					}
					var symbol = null;
					if (message.currentTurnType == 'KRESTIK') {
						symbol = 'O';
					} else {
						symbol = 'X';
					}
					document.getElementById(message.cellId).innerText = symbol;
				}
			});
		});
	}, [params.gameId]);

	return (
		<div>
			<section class='title'>
				<h1>Крестики-нолики. Ставка: {bid}</h1>
				<h5>Название лобби: {name}</h5>
			</section>
			<section class='container'>
				<div class='tile' onClick={handleClick} id='0'></div>
				<div class='tile' onClick={handleClick} id='1'></div>
				<div class='tile' onClick={handleClick} id='2'></div>
				<div class='tile' onClick={handleClick} id='3'></div>
				<div class='tile' onClick={handleClick} id='4'></div>
				<div class='tile' onClick={handleClick} id='5'></div>
				<div class='tile' onClick={handleClick} id='6'></div>
				<div class='tile' onClick={handleClick} id='7'></div>
				<div class='tile' onClick={handleClick} id='8'></div>
			</section>
			<section class='display announcer hide' id='ann'></section>
		</div>
	);
}
