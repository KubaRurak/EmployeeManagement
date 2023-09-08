import React from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';

function ConfirmationModal({ showConfirmation, handleEscapeConfirmation, completeWorkOrder }) {
    return (
        <Modal show={showConfirmation} onHide={handleEscapeConfirmation}>
            <Modal.Header closeButton>
                <Modal.Title>Confirm Completion </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                Are you sure you want to complete this work order?
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleEscapeConfirmation}>
                    Cancel <i className="bi-x"></i>
                </Button>
                <Button variant="success" onClick={completeWorkOrder}>
                    Complete Work Order <i className="bi-check-lg"></i>
                </Button>
            </Modal.Footer>
        </Modal>
    );
}

export default ConfirmationModal;