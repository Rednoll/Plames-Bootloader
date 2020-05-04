import React from 'react';
import ReactDOM from 'react-dom';
import $ from "jquery";

import Stepper from "@material-ui/core/Stepper";
import Step from "@material-ui/core/Step";
import StepLabel from "@material-ui/core/StepLabel";
import Typography from '@material-ui/core/Typography';
import { withStyles } from "@material-ui/core/styles";

import styles from "./jss_styles.js";
import mainTheme from "../common/jss_styles.jsx";

import ClipboardJS from "clipboard";

let steps = [
	
	{ui: <ProductKeyStage />, name: "Verify product key"}
];

class ProductKeyStage extends React.Component {

	constructor(props) {
		super(props);

	}

	redner() {

		return (
			<Typography>чет типо того</Typography>
		);
	}
}

class Config extends React.Component {

	constructor(props) {
		super(props);

		this.state = {

			activeStep: 0
		};
	}

	render() {

		return (

			<div style={{display: "flex", flexDirection: "column"}}>

				{steps[this.state.activeStep].ui}

				<Stepper alternativeLabel nonLinear activeStep={this.state.activeStep}>

				{steps.map((step, index)=> {

					return (
						
						<Step key={step.name}>
                        	<StepLabel completed={this.state.activeStep > index}>{step.name}</StepLabel>
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