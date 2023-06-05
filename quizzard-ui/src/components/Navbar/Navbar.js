import React, { Component } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHippo, faHamburger, faExchange} from '@fortawesome/free-solid-svg-icons'
import {MenuItems} from "./MenuItems"
import './Navbar.css'
import {Button} from "../Button"

class Navbar extends Component {
    state = {clicked: false}

    handleClick = () => {
        this.setState( {clicked: !this.state.clicked})
    }

    render() {
        return (
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
                <Button> Sign up</Button>
            </nav>
        )
    }
}

export default Navbar