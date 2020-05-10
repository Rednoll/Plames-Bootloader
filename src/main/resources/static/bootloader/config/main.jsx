import React from 'react';
import ReactDOM from 'react-dom';
import $ from "jquery";

import Stepper from "@material-ui/core/Stepper";
import Step from "@material-ui/core/Step";
import StepLabel from "@material-ui/core/StepLabel";
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';
import InputLabel from '@material-ui/core/InputLabel';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import { withStyles, ThemeProvider } from "@material-ui/core/styles";
import CircularProgress from '@material-ui/core/CircularProgress';

import styles from "./jss_styles.js";
import mainTheme from "../common/jss_styles.jsx";

import ClipboardJS from "clipboard";

import "./main.css";

const usernameValidateRegex = /^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\d.-_]{3,16}$/;

let steps = [
	
	{ui: ()=> {return <ProductKeyStage />}, name: "Product key verification"},
	{ui: ()=> {return <DatabaseInitStage />}, name: "Database settings"},
	{ui: ()=> {return <RootUserInitStage />}, name: "Root user settings"},
	{ui: ()=> {return <RebootStage />}, name: "Plames reboot"}
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

		this.state = {

			usernameValid: true,
			usernameHelperText: "",
			passValid: true,
			passReValid: true,
			passReHelperText: ""
		}

		this.passValidator = this.passValidator.bind(this);
		this.usernameValidator = this.usernameValidator.bind(this);
		this.onClick = this.onClick.bind(this);
	}

	usernameValidator() {

		let username = document.querySelector("#root-username-field").value;

		if(username.match(usernameValidateRegex)) {

			this.setState({

				usernameValid: true,
				usernameHelperText: ""
			});
		}
		else {

			this.setState({

				usernameValid: false,
				usernameHelperText: "Username incorrect! Only latin letters and numbers. 3-16 chars."
			});
		}
	}

	passValidator() {

		let pass = document.querySelector("#root-pass-field").value;
		let passRe = document.querySelector("#root-pass-re-field").value;

		if(pass.localeCompare(passRe) == 0) {

			this.setState({

				passReValid: true,
				passReHelperText: ""
			});
		}
		else {

			this.setState({

				passReValid: false,
				passReHelperText: "Passwords not equals!"
			});
		}
	}

	onClick() {

		let me = this;

		$.ajax({

			url: "../bootloader/config/root_user/data",
			method: "POST",
			data: {

				username: $("#root-username-field").val(),
				pass: $("#root-pass-field").val()
			}

		}).always((valid)=> {

			if(valid) {

				goToNextStep();
			}
		});
	}

	render() {

		return(

			<div class="config-stage-container">

				<div style={{width: "35%"}}>

					<TextField fullWidth onChange={this.usernameValidator} id="root-username-field" error={!this.state.usernameValid} helperText={this.state.usernameHelperText} label="Username" />
					<div style={{height: "15px"}}></div>
					<TextField fullWidth onChange={this.passValidator} type="password" id="root-pass-field" error={!this.state.passValid} label="Password" />
					<div style={{height: "15px"}}></div>
					<TextField fullWidth onChange={this.passValidator} type="password" id="root-pass-re-field" error={!this.state.passReValid} helperText={this.state.passReHelperText} label="Re enter" />
					
				</div>

				<button className="accent-button" onClick={this.onClick} style={{position: "absolute", bottom: "15px", right: "10px"}}>NEXT</button>

			</div>
		);
	}
}

class RebootStage extends React.Component {

	constructor(props) {
		super(props);

		this.waitReboot = this.waitReboot.bind(this);
	}

	reboot() {

		$.ajax({

			url: "../bootloader/reboot",
			async: false
		});
	}

	waitReboot() {

		let me = this;

		$.ajax({

			url: "../bootloader/time",
			timeout: 3600
		
		}).success(()=> {

			window.location.href = "../";

		}).fail(()=> {

			me.waitReboot();
		});
	}

	componentDidMount() {

		this.reboot();
		this.waitReboot();
	}

	render() {

		const { classes } = this.props;

		return(

			<div class="config-stage-container" style={{flexDirection: "column"}}>
				
				<div style={{width: "50%"}}>
					
					<img class="plames-icon" src="../resources/bootloader/common/images/plames-color-icon.svg"></img>

					<CircularProgress />

				</div>

			</div>
		);
	}
}

class DatabaseInitStage extends React.Component {

	constructor(props) {
		super(props);

		this.state = {

			usernameValid: true,
			usernameHelperText: "",
			passwordValid: true,
			passwordHelperText: "",
			urlValid: true,
			urlHelperText: "",

			mainErrorText: ""
		};

		this.databasePlatforms = [];

		let me = this;

		$.get("../bootloader/config/db/platforms", (data)=> {

			me.databasePlatforms = data;
			me.setState({});
		});

		this.onClick = this.onClick.bind(this);
	}

	onClick() {

		let me = this;

		$.ajax({

			url: "../bootloader/config/db/data",
			method: "POST",
			data: {

				username: $("#db-username-field").val(),
				password: $("#db-password-field").val(),
				url: $("#db-url-field").val(),
				platform: $("#db-platform-field").val()
			}

		}).always((valid)=> {

			if(valid == true) {

				this.setState({mainErrorText: ""});

				goToNextStep();
			}
			else {

				this.setState({mainErrorText: "Can't use database! Please check data, also database settings!"});
			}
		});
	}

	render() {

		const { classes } = this.props;

		return(

			<div class="config-stage-container" style={{flexDirection: "column"}}>
				
				<div style={{width: "35%"}}>

					<TextField fullWidth id="db-username-field" error={!this.state.usernameValid} helperText={this.state.usernameHelperText} label="Username" />
					<div style={{height: "15px"}}></div>
					<TextField fullWidth id="db-password-field" error={!this.state.passwordValid} helperText={this.state.passwordHelperText} label="Password" />
					<div style={{height: "15px"}}></div>
					<TextField fullWidth id="db-url-field" error={!this.state.urlValid} helperText={this.state.urlHelperText} label="Database url" />
					<div style={{height: "15px"}}></div>

					<FormControl className={classes.formControl}>
					<InputLabel id="db-platform-label" color="primary">Database platform</InputLabel>

					<Select inputProps={{ id: "db-platform-field" }} labelWidth="100%" id="db-platform-select" labelId="db-platform-label">

						{this.databasePlatforms.map((platform) => <MenuItem value={platform}>{platform}</MenuItem>)}

					</Select>
					</FormControl>

				</div>

				<div style={{height: "15px"}}></div>
				
				<div style={{width: "50%"}}>
					
					<Typography fullWidth align="center" color="error">{this.state.mainErrorText}</Typography>
				
				</div>

				<button className="accent-button" onClick={this.onClick} style={{position: "absolute", bottom: "15px", right: "10px"}}>NEXT</button>

			</div>
		);
	}
}

DatabaseInitStage = withStyles(styles)(DatabaseInitStage);

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

			<div class="config-stage-container">
				
				<div style={{width: "45%"}}>
				
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