import './SpinPage.css';
import { useState } from 'react';
import Winwheel from 'winwheel';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';

export function SpinPage() {
	const [wheelSpinning, setWheelSpinning] = useState(false);
	function alertPrize() {
		let winningSegment = winningCount();
		// Do basic alert of the segment text. You would probably want to do something more interesting with this information.
		alert('You have won ' + winningSegment.text);
	}
	// const alertPrize = (indicatedSegment) => {
	// 	console.log('sadad');
	// 	setPrizeText(indicatedSegment.text);
	// 	alert('You have won ' + prizeText);
	// };

	let theWheel = new Winwheel({
		numSegments: 8, // Specify number of segments.
		outerRadius: 212, // Set outer radius so wheel fits inside the background.
		textFontSize: 28, // Set font size as desired.
		// Define segments including colour and text.
		segments: [
			{ fillStyle: '#eae56f', text: 'x0' },
			{ fillStyle: '#89f26e', text: 'x5' },
			{ fillStyle: '#7de6ef', text: 'x2' },
			{ fillStyle: '#e7706f', text: 'x0' },
			{ fillStyle: '#eae56f', text: 'x0.5' },
			{ fillStyle: '#89f26e', text: 'x3' },
			{ fillStyle: '#7de6ef', text: 'x0.5' },
			{ fillStyle: '#e7706f', text: 'x1' },
		],
		// Specify the animation to use.
		animation: {
			type: 'spinToStop',
			duration: 5,
			spins: 8,
			callbackFinished: alertPrize,
		},
	});

	const winningCount = () => {
		return theWheel.getIndicatedSegment();
	};

	const handleSpin = () => {
		if (wheelSpinning === false) {
			theWheel.startAnimation();
			setWheelSpinning(true);
		} else {
			setTimeout(console.log(theWheel.getIndicatedSegment()), 5000);
			theWheel.stopAnimation(false);
			theWheel.draw();
			setWheelSpinning(false);
			theWheel.startAnimation();
		}
	};

	return (
		<div>
			<table cellpadding='0' cellspacing='0' border='0'>
				<tr>
					<td
						width='438'
						height='582'
						className='the_wheel'
						align='center'
						valign='center'
					>
						<canvas id='canvas' width='434' height='434'></canvas>
					</td>
				</tr>
			</table>
			<Button onClick={handleSpin} variant='contained'>
				КРУТИТЬ
			</Button>
			<TextField id='standard-basic' label='Ставка' variant='standard' />
		</div>
	);
}
