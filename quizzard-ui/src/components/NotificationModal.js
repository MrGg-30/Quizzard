import { Button,Modal } from 'semantic-ui-react'
import React from 'react';
import NotificationCard from './NotificationCard';

function NotificationModal({ onAccept, onReject, isOpen, senderUsername, category }) {
    const [open, setOpen] = React.useState(isOpen);
  
    React.useEffect(() => {
      setOpen(isOpen);
    }, [isOpen]);
  
    return (
      <Modal
        open={open}
        onClose={() => setOpen(false)}
        size='tiny'
        centered
        style={{ display: 'flex', justifyContent: "center", alignItems: "center", width: 456 }}
      >
        <Modal.Content style={{ background: "linear-gradient(236deg, #1D5B79 0%, #77bfe3 100%)", padding: 0 }}>
          <NotificationCard onAccept={onAccept} onReject={onReject} senderUsername={senderUsername} category={category} />
        </Modal.Content>
      </Modal>
    )
  }
  
  export default NotificationModal;
  