import React from 'react';
import { Modal, Button, Row, Col } from 'react-bootstrap';

function WorkOrderDetailsModal({ show, handleClose, handleShowEditModal, selectedWorkOrder, completeWorkOrder, fromActive, userRole}) {

  const handleCompleteWorkOrder = () => {
    handleClose();
    completeWorkOrder();
  }

  const handleEditWorkOrder = () => {
    handleClose();
    handleShowEditModal();
  }

  return (
    <Modal show={show} onHide={handleClose} animation={false} size="xl">
      <Modal.Header closeButton>
        <Modal.Title>Work Order Details</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Row>
          <Col>
            <h5>Order Details</h5>
            <table className="table">
              <tbody>
                <tr>
                  <td><strong>Order Id</strong></td>
                  <td>{selectedWorkOrder?.orderId}</td>
                </tr>
                <tr>
                  <td><strong>Order Name</strong></td>
                  <td>{selectedWorkOrder?.orderName}</td>
                </tr>
                <tr>
                  <td><strong>Type</strong></td>
                  <td>{selectedWorkOrder?.orderType.orderTypeName}</td>
                </tr>
                <tr>
                  <td><strong>Price</strong></td>
                  <td>{selectedWorkOrder?.orderType.price}</td>
                </tr>
                <tr>
                  <td><strong>Status</strong></td>
                  <td>{selectedWorkOrder?.status}</td>
                </tr>
                <tr>
                  <td><strong>Start Time</strong></td>
                  <td>{selectedWorkOrder?.startTimeStamp}</td>
                </tr>
                <tr>
                  <td><strong>End Time</strong></td>
                  <td>{selectedWorkOrder?.endTimeStamp}</td>
                </tr>
                <tr>
                  <td><strong>Last Modification</strong></td>
                  <td>{selectedWorkOrder?.lastModificationTimeStamp}</td>
                </tr>
                <tr>
                  <td><strong>Comments</strong></td>
                  <td>{selectedWorkOrder?.comments}</td>
                </tr>
              </tbody>
            </table>
          </Col>
          <Col>
            <h5>Assignee Details</h5>
            <table className="table">
              <tbody>
                <tr>
                  <td><strong>Name</strong></td>
                  <td>{selectedWorkOrder?.userFirstName} {selectedWorkOrder?.userLastName}</td>
                </tr>
                <tr>
                  <td><strong>Email</strong></td>
                  <td>{selectedWorkOrder?.userEmail}</td>
                </tr>
              </tbody>
            </table>
            <h5>Customer Details</h5>
            <table className="table">
              <tbody>
                <tr>
                  <td><strong>Name</strong></td>
                  <td>{selectedWorkOrder?.customerFirstName} {selectedWorkOrder?.customerLastName}</td>
                </tr>
                <tr>
                  <td><strong>Email</strong></td>
                  <td>{selectedWorkOrder?.customerEmail}</td>
                </tr>
                <tr>
                  <td><strong>Company</strong></td>
                  <td>{selectedWorkOrder?.customerCompanyName}</td>
                </tr>
              </tbody>
            </table>
          </Col>
        </Row>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="primary" onClick={handleEditWorkOrder} style = {{marginRight: "auto" }} disabled={userRole==="Engineer" || fromActive}>
          Edit
        </Button>
        <Button variant="success" onClick={handleCompleteWorkOrder} disabled={!fromActive}>
          Complete <i className="bi-check-lg"></i>
        </Button>
        <Button variant="secondary" onClick={handleClose}>
          Close <i className="bi-x"></i>
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default WorkOrderDetailsModal;

