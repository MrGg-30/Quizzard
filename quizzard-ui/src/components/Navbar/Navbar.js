import React, { Component } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHippo, faHamburger, faExchange} from '@fortawesome/free-solid-svg-icons'
import {MenuItems} from "./MenuItems"
import './Navbar.css'
import {Button} from "../Button"
import SignUpModal from '../SignUp/SignUpModal';
import Modal from '../SignUp/Modal'

class Navbar extends Component {
    state = {clicked: false, 
            isSignUpModalOpen: false}

    
    handleClick = () => {
        this.setState( {clicked: !this.state.clicked})
    }
    handleSignUpButtonClick = () => {
        this.setState({ isSignUpModalOpen: true });
    }
    handleCloseModal = () => {
        this.setState({ isSignUpModalOpen: false });
      }

    render() {
        return (
            <div>

            <nav className='NavbarItems'>
                <h1 className="navbar-logo">Quizzard <FontAwesomeIcon icon={faHippo} /></h1>
                <div className='menu-icon' onClick={this.handleClick}>
                    <FontAwesomeIcon icon={this.state.clicked ? faExchange : faHamburger} 
                    onClick={this.handleClick}
                    className={this.state.clicked ? 'exchange-icon' : 'hamburger-icon'}
                    />
                </div>
                <ul className={this.state.clicked ? 'nav-menu active' : 'nav-menu'}>
                    {MenuItems.map((item, index) => {
                        return (
                            <li key={index}> 
                                <a className={item.cName} href={item.url}>
                                    {item.title} 
                                </a>
                            </li>
                        )
                    })} 
                </ul>
                <Button onClick={this.handleSignUpButtonClick}> Sign up</Button>
            </nav>
            {this.state.isSignUpModalOpen && <Modal onClose={this.handleCloseModal} />}
            {/* {this.state.isSignUpModalOpen && <SignUpModal onClose={this.handleCloseModal} />} */}
            </div>
        )
    }
}

export default Navbar