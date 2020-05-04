import React from 'react';
import ReactDOM from 'react-dom';
import $ from "jquery";

import Stepper from "@material-ui/core/Stepper";
import Step from "@material-ui/core/Step";
import StepLabel from "@material-ui/core/StepLabel";
import Typography from '@material-ui/core/Typography';
import { withStyles, ThemeProvider } from "@material-ui/core/styles";

import styles from "./jss_styles.js";
import mainTheme from "../common/jss_styles.jsx";

import ClipboardJS from "clipboard";

let steps = [
	
	{ui: ()=> {return <ProductKeyStage />}, name: "Verify product key"}
];

class ProductKeyStage extends React.Component {

	constructor(props) {
		super(props);

	}

	render() {

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

			<ThemeProvider theme={mainTheme}>
			<div style={{display: "flex", flexDirection: "column"}}>

				<div style={{flexGrow: "3.5"}}>

					{steps[this.state.activeStep].ui()}

				</div>

				<div style={{position: "relative", height: "40", flexBasis: "80px", flexShrink: "0.0000001"}}>
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

			</div>
			</ThemeProvider>
		);
	}
}



const StyledConfig = withStyles(styles)(Config);

ReactDOM.render(<StyledConfig />, document.querySelector("#react-body"));

new ClipboardJS(".btn");