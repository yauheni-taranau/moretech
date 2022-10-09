import './Profile.css';
import { useEffect, useState } from 'react';
import { axiosConfig } from '../utils/axios';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import Paper from '@mui/material/Paper';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Button from '@mui/material/Button';
import deleteIcon from '../assets/delete.svg';
import Avatar from '@mui/material/Avatar';
import { red } from '@mui/material/colors';
import Dialog from '@mui/material/Dialog';
import TextField from '@mui/material/TextField';
import rubleIcon from '../assets/rubl.svg';

export function Profile() {
	const [users, setUsers] = useState([]);
	const [name, setName] = useState([]);
	const [unitId, setUnitId] = useState([]);
	const [open, setOpen] = useState(false);
	const [balance, setBalance] = useState(0);
	const [userToSend, setUserToSend] = useState(0);
	const [toSendAmt1, setToSendAmt1] = useState(0);

	const setToSendAmt = (e) => {
		setToSendAmt1(e.target.value);
	};

	const handleTokens = (e) => {
		setOpen(true);
		setUserToSend(e);
	};

	const handleClose = (e) => {
		setOpen(false);
	};

	const handleTokensGo = (e) => {
		let request = {
			amount: +toSendAmt1,
			toUsername: userToSend,
		};
		axiosConfig
			.post('/admin/unit/' + unitId + '/transferRubles', request)
			.then((e) => {})
			.catch((e) => {});
		setOpen(false);
	};

	useEffect(() => {
		axiosConfig.get('/admin/unit/current').then((response) => {
			setUsers(response.data.users);
			setName(response.data.name);
			setBalance(response.data.balance);
			setUnitId(response.data.id);
		});
	}, []);

	return (
		<div className='auction-wrapper'>
			<div className='auction-page-title'>{'Группа ' + name}</div>
			<div className='auction-page-title, cxz'>
				<img width='40px' height='40px' src={rubleIcon} alt='' />
				<div>{balance}</div>
			</div>
			<div className='auction-page-all-item'>
				<TableContainer component={Paper}>
					<Table sx={{ minWidth: 650 }} aria-label='simple table'>
						<TableHead>
							<TableRow>
								<TableCell align='center'>Пользователь</TableCell>
								<TableCell align='center'>Логин</TableCell>
								<TableCell align='center'>Токены</TableCell>
								<TableCell align='center'></TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
							{users.map((user) => (
								<TableRow
									key={user.id}
									sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
								>
									<TableCell
										className='zxc'
										component='th'
										scope='row'
										align='center'
									>
										<Avatar
											className='qwe'
											sx={{ bgcolor: red[500] }}
											aria-label='recipe'
										>
											MM
										</Avatar>
										{user.name + ' ' + user.surname}
									</TableCell>
									<TableCell align='center'>{user.username}</TableCell>
									<TableCell align='center'>
										<Button
											variant='outlined'
											onClick={() => handleTokens(user.username)}
										>
											Начислить токен
										</Button>
									</TableCell>
									<TableCell align='center'>
										<img width='20px' height='20px' src={deleteIcon} alt='' />

										<Dialog onClose={handleClose} open={open}>
											<TableContainer component={Paper}>
												<TableRow>
													<TableCell align='center'>Количество</TableCell>
												</TableRow>
												<TableRow>
													<TableCell>
														<TextField
															onChange={setToSendAmt}
															id='outlined-basic'
															label='Outlined'
															variant='outlined'
														/>
													</TableCell>
												</TableRow>
												<TableRow align='center'>
													<TableCell align='center'>
														<Button
															variant='outlined'
															onClick={handleTokensGo}
														>
															Начислить
														</Button>
													</TableCell>
												</TableRow>
											</TableContainer>
										</Dialog>
									</TableCell>
								</TableRow>
							))}
						</TableBody>
					</Table>
				</TableContainer>
			</div>
		</div>
	);
}
