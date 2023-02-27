import React from 'react';
import { Modal, Button } from 'react-bootstrap';

function WorkOrderDetailsModal({ show, handleClose, selectedWorkOrder, editWorkOrderDetails }) {
  return (
    <Modal show={show} onHide={handleClose} animation={false}>
      <Modal.Header closeButton>
        <Modal.Title>Work Order Details</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <p><strong>Order Id:</strong> {selectedWorkOrder?.orderId}</p>
        <p><strong>Order Name:</strong> {selectedWorkOrder?.orderName}</p>
        <p><strong>Order Type:</strong> {selectedWorkOrder?.orderType}</p>
        <p><strong>Order Price:</strong> {selectedWorkOrder?.price}</p>
        <p><strong>Status:</strong> {selectedWorkOrder?.isActive ? 'Active' : 'Inactive'}</p>
        <p><strong>Start Time:</strong> {selectedWorkOrder?.startTimeStamp}</p>
        <p><strong>End Time:</strong> {selectedWorkOrder?.endTimeStamp}</p>
        <p><strong>Last Modification:</strong> {selectedWorkOrder?.lastModificationTimeStamp}</p>
        <p><strong>Comments:</strong> {selectedWorkOrder?.comments}</p>
        <p><strong>Assigned To:</strong> {selectedWorkOrder?.assigneeEmail}</p>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
        <Button variant="primary" onClick={editWorkOrderDetails}>
          Edit
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default WorkOrderDetailsModal;

