import { Routes, Route } from 'react-router-dom';
import { Header } from '../Header/Header';
import { Login } from '../Login/Login';
import { Auction } from '../Auction/Auction';
import { Main } from '../Main/Main';
import { SpinPage } from '../SpinPage/SpinPage';
import { Game } from '../Game/Game';
import { NewsPage } from '../NewsPage/NewsPage';
import { useEffect, useState } from 'react';
import { tokenCheck } from '../utils/tokenCheck';
import { Auctioneer } from '../Auctioneer/Auctioneer';
import { Profile } from '../Profile/Profile';
import { Market } from '../Market/Market';
import { MarketInfo } from '../Market/MarketInfo/MarketInfo';
import { CoursePage } from '../CoursePage/CoursePage';
import { CourseInfo } from '../CourseInfo/CourseInfo';
import { A } from '../A/A';

export function App() {
	const [isLogin, setIsLogin] = useState(false);

	useEffect(() => {
		setIsLogin(tokenCheck());
	}, []);

	return (
		<div className='wrapper'>
			{isLogin && <Header />}
			<Routes>
				<Route path='/' element={<Main />}></Route>
				<Route path='/login' element={<Login />}></Route>
				<Route path='/auction' element={<Auction />}></Route>
				<Route path='/market' element={<Market />}></Route>
				<Route path='/market/:marketId' element={<MarketInfo />}></Route>
				<Route path='/course' element={<CoursePage />}></Route>
				<Route path='/course/:courseId' element={<CourseInfo />}></Route>
				<Route path='/slot' element={<SpinPage />}></Route>
				<Route path='/auction/:aucId' element={<Auctioneer />}></Route>
				<Route path='/news/:newsId' element={<NewsPage />}></Route>
				<Route path='/game/:gameId' element={<Game />}></Route>
				<Route path='/game/:gameId/join' element={<Game />}></Route>
				<Route path='/profile' element={<Profile />}></Route>
				<Route path='/anekdots' element={<A />}></Route>
			</Routes>
		</div>
	);
}
