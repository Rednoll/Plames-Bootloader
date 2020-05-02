import React from 'react';
import ReactDOM from 'react-dom';
import $ from "jquery";

import Stepper from "@material-ui/core/Stepper";
import Step from "@material-ui/core/Step";
import StepLabel from "@material-ui/core/StepLabel";
import Typography from '@material-ui/core/Typography';

import styles from "./jss_styles.js";
import mainTheme from "../common/jss_styles.jsx";

import ClipboardJS from "clipboard";

let steps = [
		
	<ProductKeyStage />
];

class ProductKeyStage extends React.Component {

	redner() {

		return (
			<Typography>чет типо того</Typography>
		);
	}

	getName() {

		return "Verify product key";
	}
}

class Config extends React.Component {

	constructor(props) {
		super(props);

		this.state = {

			activeStep: 0
		};

		steps.map((step, index)=> step.index = index);
	}

	render() {

		return (

			<div style={{display: "flex", flexDirection: "column"}}>

				{steps[this.state.activeStep]}

				<Stepper alternativeLabel nonLinear activeStep={this.state.activeStep}>

				{steps.map((step)=> {

					return (
						
						<Step key={step.getName()}>
                        	<StepLabel completed={this.state.activeStep > step.index}>{step.getName()}</StepLabel>
                        </Step>
					);
				})}

				</Stepper>

			</div>
		);
	}
}



const StyledConfig = withStyles(styles)(Config);

ReactDOM.render(<StyledConfig />, document.querySelector("#react-body"));

new ClipboardJS(".btn");