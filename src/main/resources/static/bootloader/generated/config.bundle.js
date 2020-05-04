/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = "./src/main/resources/static/bootloader/config/main.jsx");
/******/ })
/************************************************************************/
/******/ ({

/***/ "./src/main/resources/static/bootloader/config/main.jsx":
/*!**************************************************************!*\
  !*** ./src/main/resources/static/bootloader/config/main.jsx ***!
  \**************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

eval("throw new Error(\"Module build failed (from ./node_modules/babel-loader/lib/index.js):\\nSyntaxError: C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\src\\\\main\\\\resources\\\\static\\\\bootloader\\\\config\\\\main.jsx: Expected corresponding JSX closing tag for <div> (77:3)\\n\\n  75 | \\n  76 | \\t\\t\\t</div>\\n> 77 | \\t\\t\\t</ThemeProvider>\\n     | \\t\\t\\t^\\n  78 | \\t\\t);\\n  79 | \\t}\\n  80 | }\\n    at Object._raise (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:742:17)\\n    at Object.raiseWithData (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:735:17)\\n    at Object.raise (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:729:17)\\n    at Object.jsxParseElementAt (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:4576:16)\\n    at Object.jsxParseElementAt (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:4544:32)\\n    at Object.jsxParseElementAt (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:4544:32)\\n    at Object.jsxParseElement (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:4602:17)\\n    at Object.parseExprAtom (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:4609:19)\\n    at Object.parseExprSubscripts (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:9602:23)\\n    at Object.parseMaybeUnary (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:9582:21)\\n    at Object.parseExprOps (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:9452:23)\\n    at Object.parseMaybeConditional (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:9425:23)\\n    at Object.parseMaybeAssign (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:9380:21)\\n    at Object.parseParenAndDistinguishExpression (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:10193:28)\\n    at Object.parseExprAtom (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:9947:21)\\n    at Object.parseExprAtom (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:4614:20)\\n    at Object.parseExprSubscripts (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:9602:23)\\n    at Object.parseMaybeUnary (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:9582:21)\\n    at Object.parseExprOps (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:9452:23)\\n    at Object.parseMaybeConditional (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:9425:23)\\n    at Object.parseMaybeAssign (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:9380:21)\\n    at Object.parseExpression (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:9332:23)\\n    at Object.parseReturnStatement (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:11448:28)\\n    at Object.parseStatementContent (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:11129:21)\\n    at Object.parseStatement (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:11081:17)\\n    at Object.parseBlockOrModuleBlockBody (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:11656:25)\\n    at Object.parseBlockBody (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:11642:10)\\n    at Object.parseBlock (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:11626:10)\\n    at Object.parseFunctionBody (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:10634:24)\\n    at Object.parseFunctionBodyAndFinish (C:\\\\Projects\\\\Plames Project\\\\Plames-Bootloader\\\\node_modules\\\\@babel\\\\parser\\\\lib\\\\index.js:10617:10)\");\n\n//# sourceURL=webpack:///./src/main/resources/static/bootloader/config/main.jsx?");

/***/ })

/******/ });