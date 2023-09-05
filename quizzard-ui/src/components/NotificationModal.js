import ReactModal from 'react-modal';
import React from 'react';
import NotificationCard from './NotificationCard';

const customStyles = {
  overlay: {
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      backgroundColor: ' rgba(0, 0, 0, 0.7)',
  },
  content: {
      top: '50%',
      left: '50%',
      right: 'auto',
      bottom: 'auto',
      marginRight: '-50%',
      transform: 'translate(-50%, -50%)',
      padding: 0,
      borderRadius: 6,
      width: 'auto',
      height: 'auto',
  },
};

function NotificationModal({ onAccept, onReject, isOpen, senderUsername, category }) {
    const [open, setOpen] = React.useState(isOpen);
  
    React.useEffect(() => {
      setOpen(isOpen);
    }, [isOpen]);
  
    return (
      <ReactModal
        isOpen={open}
        onClose={() => setOpen(false)}
        style={customStyles}
      >
          <NotificationCard onAccept={onAccept} onReject={onReject} senderUsername={senderUsername} category={category}/>
        
      </ReactModal>
    )
  }
  
  export default NotificationModal;
  