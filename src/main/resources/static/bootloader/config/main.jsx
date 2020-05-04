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
	
	{ui: ()=> {return <ProductKeyStage />}, name: "Product key verification"},
	{ui: ()=> {return <DatabaseInitStage />}, name: "Database settings"},
	{ui: ()=> {return <RootUserInitStage />}, name: "Root user settings"}

];

class RootUserInitStage extends React.Component {

	constructor(props) {
		super(props);

	}

	render() {

		return(

		);
	}
}

class DatabaseInitStage extends React.Component {

	constructor(props) {
		super(props);

	}

	render() {

		return(

		);
	}
}

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
			<div style={{display: "flex", flexDirection: "column", boxSizing: "border-box", height:"100%", padding: "15px"}}>

				<div style={{display: "flex", justifyContent: "center", alignItems: "center", flexGrow: "3.5"}}>

					{steps[this.state.activeStep].ui()}

				</div>

				<div style={{position: "relative", flexBasis: "80px", flexShrink: "0.0000001"}}>
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