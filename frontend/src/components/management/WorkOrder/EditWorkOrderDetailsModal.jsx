import React, { useState, useEffect, useCallback, useMemo } from 'react';
import PropTypes from 'prop-types';
import { Modal, Row, Col } from 'react-bootstrap';
import { Formik, Form, Field } from 'formik';
import { editWorkOrderApi } from "../api/WorkOrdersApiService";
import { retrieveCustomersApi } from "../api/CustomerApiService";
import { retrieveOrderTypesApi } from "../api/OrderTypeApiService";
import { retrieveUserApi } from "../api/UserApiService";
import { retrieveStatusTypesApi } from "../api/WorkOrderStatusApiService";
import './WorkOrderDetailsModal.css'

function EditWorkOrderDetailsModal({ show, handleClose, selectedWorkOrder, refreshWorkOrders, setMessage }) {
  const [customers, setCustomers] = useState();
  const [orderTypes, setOrderTypes] = useState();
  const [users, setUsers] = useState();
  const [statusTypes, setStatusTypes] = useState();

  const fetchData = useCallback(async () => {
    try {
      const [customerResponse, orderTypeResponse, userResponse, statusTypeResponse] = await Promise.all([
        retrieveCustomersApi(),
        retrieveOrderTypesApi(),
        retrieveUserApi(),
        retrieveStatusTypesApi()
      ]);
      setCustomers(customerResponse.data);
      setOrderTypes(orderTypeResponse.data);
      setUsers(userResponse.data);
      setStatusTypes(statusTypeResponse.data);
      console.log(statusTypeResponse);
    } catch (error) {
      console.error(error);
    }
  }, []);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  const initialValues = useMemo(() => ({
    orderId: selectedWorkOrder?.orderId,
    orderName: selectedWorkOrder?.orderName,
    orderTypeId: selectedWorkOrder?.orderType.id,
    orderTypeName: selectedWorkOrder?.orderType.orderTypeName,
    expectedDays: selectedWorkOrder?.orderType.expectedDays,
    price: selectedWorkOrder?.orderType.price,
    status: selectedWorkOrder?.status,
    startTimeStamp: selectedWorkOrder?.startTimeStamp,
    endTimeStamp: selectedWorkOrder?.endTimeStamp,
    lastModificationTimeStamp: selectedWorkOrder?.lastModificationTimeStamp,
    comments: selectedWorkOrder?.comments,
    userId: selectedWorkOrder?.userId,
    userName: `${selectedWorkOrder?.userFirstName} ${selectedWorkOrder?.userLastName}`,
    userEmail: selectedWorkOrder?.userEmail,
    customerId: selectedWorkOrder?.customerId,
    customerName: `${selectedWorkOrder?.customerFirstName} ${selectedWorkOrder?.customerLastName}`,
    customerEmail: selectedWorkOrder?.customerEmail,
    customerCompanyName: selectedWorkOrder?.customerCompanyName
  }), [selectedWorkOrder]);

  const onUserEmailChange = useCallback((event, setValues) => {
    const emailId = event.target.value;
    const user = users.find(user => user.emailId === emailId);
    const userName = `${user.firstName} ${user.lastName}`;

    if (user) {
      setValues({
        ...initialValues,
        userId: user.userId,
        userName,
        userEmail: user.EmailId,
      });
    }
  }, [users, initialValues]);

  const onOrderTypeChange = useCallback((event, setValues) => {
    const orderTypeName = event.target.value;
    const orderType = orderTypes.find(orderType => orderType.orderTypeName === orderTypeName);

    if (orderType) {
      setValues({
        ...initialValues,
        orderTypeName: orderType.orderTypeName,
        expectedDays: orderType.expectedDays,
        price: orderType.price,
        orderTypeId: orderType.id
      });
    }
  }, [orderTypes, initialValues]);

  const onCustomerEmailChange = useCallback((event, setValues) => {
    const email = event.target.value;
    const customer = customers.find(customer => customer.customerEmail === email);
    if (customer) {

      const customerName = `${customer.customerFirstName} ${customer.customerLastName}`;

      setValues({
        ...initialValues,
        customerId: customer.customerId,
        customerName,
        customerEmail: customer.customerEmail,
        customerCompanyName: customer.customerCompanyName
      });
    }
  }, [customers, initialValues]);

  const onSubmit = useCallback((values) => {
    const workOrder = createWorkOrderObject(values);
    editWorkOrderApi(values.orderId, workOrder)
      .then(response => {
        handleClose();
        refreshWorkOrders("Work Order edited successfully!");
        setMessage("Work Order edited successfully!");
      })
      .catch(error => {
        console.error(error);
        // show error message to user
      });
  }, [handleClose, refreshWorkOrders, setMessage]);

  const createWorkOrderObject = (values) => ({
    orderId: values.orderId,
    orderName: values.orderName,
    orderType: {
      id: values.orderTypeId,
      orderTypeName: values.orderTypeName,
      expectedDays: values.expectedDays,
      price: values.price,
    },
    status: values.status,
    startTimeStamp: values.startTimeStamp,
    endTimeStamp: values.endTimeStamp,
    lastModificationTimeStamp: values.endTimeStamp,
    comments: values.comments,
    userId: values.userId,
    userFirstName: values.userFirstName,
    userLastName: values.userLastName,
    userEmail: values.userEmail,
    customerId: values.customerId,
    customerFirstName: values.customerFirstName,
    customerLastName: values.customerLastName,
    customerEmail: values.customerEmail,
    customerCompanyName: values.customerCompanyName,
  });

  const onDelete = useCallback(() => {
    const workOrder = createWorkOrderObject({ ...initialValues, status: 'CANCELLED' });
    editWorkOrderApi(initialValues.orderId, workOrder)
      .then(response => {
        handleClose();
        refreshWorkOrders("Work Order canceled successfully!");
        setMessage("Work Order canceled successfully!");
      })
      .catch(error => {
        console.error(error);
      });
  }, [handleClose, refreshWorkOrders, setMessage, initialValues, createWorkOrderObject]);

  return (
    <div>
      <Modal show={show} onHide={handleClose} animation={false} size="xl">
        <Modal.Header closeButton>
          <Modal.Title>Edit Order Details</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Formik initialValues={initialValues}
            onSubmit={onSubmit}
            enableReinitialize={true}
            validateOnChange={false}
            validateOnBlur={false}
          >
            {
              (props) => (
                <Form>
                  <Row>
                    <Col>
                      <h5>Order Details</h5>
                      <table className="table">
                        <tbody>
                          <tr>
                            <td><label>Order Name</label></td>
                            <td><Field type="text" className="form-control" name="orderName" /></td>
                          </tr>
                          <tr>
                            <td><label>Order Type</label></td>
                            <td>
                              <select
                                value={props.values.orderTypeName}
                                onChange={event => onOrderTypeChange(event, props.setValues)}
                                className="form-control"
                                name="orderTypeName"
                              >
                                <option value="">Select type</option>
                                {orderTypes && orderTypes.map(orderType => (
                                  <option key={orderType.Id} value={orderType.orderTypeName}>
                                    {orderType.orderTypeName}
                                  </option>
                                ))}
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td><label>Price</label></td>
                            <td>{props.values.price}</td>
                          </tr>
                          <tr>
                            <td><label>Status</label></td>
                            <td>
                              <Field as="select" className="form-control" name="status">
                                <option value="">Select status</option>
                                {statusTypes && statusTypes.map((statusType, index) => (
                                  <option key={index} value={statusType}>
                                    {statusType}
                                  </option>
                                ))}
                              </Field>
                            </td>
                          </tr>
                          <tr>
                            <td><label>End Time</label></td>
                            <td><Field type="text" className="form-control" name="endTimeStamp" /></td>
                          </tr>
                          <tr>
                            <td><label>Comments</label></td>
                            <td><Field type="text" className="form-control" name="comments" /></td>
                          </tr>
                        </tbody>
                      </table>
                    </Col>
                    <Col>
                      <h5>Asignee details</h5>
                      <table className="table">
                        <tbody>
                          <tr>
                            <td><label>Email</label></td>
                            <td>
                              <select
                                value={props.values.userEmail}
                                onChange={event => onUserEmailChange(event, props.setValues)}
                                className="form-control"
                                name="userEmail"
                              >
                                <option value="">Select an email</option>
                                {users && users.map(user => (
                                  <option key={user.userId} value={user.emailId}>
                                    {user.emailId}
                                  </option>
                                ))}
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td><label>Name</label></td>
                            <td>{props.values.userName}</td>
                          </tr>
                        </tbody>
                      </table>
                      <h5>Customer details</h5>
                      <table className='table'>
                        <tbody>
                          <tr>
                            <td><label>Email</label></td>
                            <td>
                              <select
                                value={props.values.customerEmail}
                                onChange={event => onCustomerEmailChange(event, props.setValues)}
                                className="form-control"
                                name="customerEmail"
                              >
                                <option value="">Select an email</option>
                                {customers && customers.map(customer => (
                                  <option key={customer.customerId} value={customer.customerEmail}>
                                    {customer.customerEmail}
                                  </option>
                                ))}
                              </select>
                            </td>
                          </tr>
                          <tr>
                            <td><label>Name</label></td>
                            <td>{props.values.customerName}</td>
                          </tr>
                          <tr>
                            <td><label>Company</label></td>
                            <td>{props.values.customerCompanyName}</td>
                          </tr>
                        </tbody>
                      </table>
                    </Col>
                  </Row>
                  <div className="form-group d-flex">
                    <button type="submit" className="btn btn-success">Save</button>
                    <button type="button" className="btn btn-danger" onClick={onDelete} style={{ marginLeft: "10px" }}>Delete</button>
                    <button type="button" className="btn btn-secondary" onClick={handleClose} style={{ marginLeft: "auto" }}>Close<i className="bi-x"></i></button>
                  </div>
                </Form>
              )
            }
          </Formik>
        </Modal.Body>
      </Modal>
    </div>
  );
}

EditWorkOrderDetailsModal.propTypes = {
  show: PropTypes.bool.isRequired,
  handleClose: PropTypes.func.isRequired,
  selectedWorkOrder: PropTypes.object.isRequired,
  refreshWorkOrders: PropTypes.func.isRequired,
};

export default EditWorkOrderDetailsModal;