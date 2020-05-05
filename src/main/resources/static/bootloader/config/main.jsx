import React from 'react';
import ReactDOM from 'react-dom';
import $ from "jquery";

import Stepper from "@material-ui/core/Stepper";
import Step from "@material-ui/core/Step";
import StepLabel from "@material-ui/core/StepLabel";
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';
import { withStyles, ThemeProvider } from "@material-ui/core/styles";

import styles from "./jss_styles.js";
import mainTheme from "../common/jss_styles.jsx";

import ClipboardJS from "clipboard";

let steps = [
	
	{ui: ()=> {return <ProductKeyStage />}, name: "Product key verification"},
	{ui: ()=> {return <DatabaseInitStage />}, name: "Database settings"},
	{ui: ()=> {return <RootUserInitStage />}, name: "Root user settings"}

];

let configRef = React.createRef();

const goToNextStep = ()=> {

	let config = configRef.current;

	if(config.state.activeStep+1 != steps.length) {
	
		config.setState({activeStep: (config.state.activeStep+1)});
	}
	else {

		//TODO
	}
};

class RootUserInitStage extends React.Component {

	constructor(props) {
		super(props);

	}

	render() {

		return(
			<Typography>чет типо того</Typography>
		);
	}
}

class DatabaseInitStage extends React.Component {

	constructor(props) {
		super(props);

	}

	render() {

		return(
			<Typography>чет типо того</Typography>
		);
	}
}

class ProductKeyStage extends React.Component {

	constructor(props) {
		super(props);

		this.state = {

			helperText: "",
			productKeyValid: true
		}
	
		this.onClick = this.onClick.bind(this);
	}

	onClick() {

		$.ajax({

			url: "../bootloader/config/verify?productKey="+$("#product-key-field").val(),
			method: "GET",
			success: (good)=> {

				if(good) {

					goToNextStep();
				}
			}
		});
	}

	render() {

		return (

			<div style={{display: "flex", justifyContent: "center", alignItems: "center", boxSizing: "border-box", position: "relative", height: "100%", width: "100%"}}>
				
				<div style={{maxWidth: "50%", width: "max-content"}}>
				
					<Typography fullWidth align="center" color="textPrimary">Please enter your product key</Typography>
					<div style={{height: "10px"}}></div>
					<TextField fullWidth id="product-key-field" error={!this.state.productKeyValid} helperText={this.state.helperText} label="Product key" />

				</div>

				<button className="accent-button" onClick={this.onClick} style={{position: "absolute", bottom: "15px", right: "10px"}}>NEXT</button>

			</div>
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

				<div style={{position: "relative", display: "flex", justifyContent: "center", alignItems: "center", flexGrow: "3.5"}}>

					{steps[this.state.activeStep].ui()}

				</div>

				<div style={{position: "relative", flexBasis: "80px", flexShrink: "0.0000001", borderTop: "1px solid lightgrey"}}>
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

ReactDOM.render(<StyledConfig ref={configRef} />, document.querySelector("#react-body"));

new ClipboardJS(".btn");