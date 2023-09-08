import React, { useState, useEffect, useCallback, useMemo } from 'react';
import PropTypes from 'prop-types';
import { Modal, Button, Row, Col } from 'react-bootstrap';
import { Formik, Form, Field } from 'formik';
import { createWorkOrderApi } from "../api/WorkOrdersApiService";
import { retrieveCustomersApi } from "../api/CustomerApiService";
import { retrieveOrderTypesApi } from "../api/OrderTypeApiService";
import { retrieveUserApi } from "../api/UserApiService";
import './WorkOrderDetailsModal.css'

function CreateWorkOrderModal({ show, handleClose, refreshWorkOrders, setMessage }) {
  const [customers, setCustomers] = useState();
  const [orderTypes, setOrderTypes] = useState();
  const [users, setUsers] = useState();

  const fetchData = useCallback(async () => {
    try {
      const [customerResponse, orderTypeResponse, userResponse] = await Promise.all([
        retrieveCustomersApi(),
        retrieveOrderTypesApi(),
        retrieveUserApi(),
      ]);
      setCustomers(customerResponse.data);
      setOrderTypes(orderTypeResponse.data);
      setUsers(userResponse.data);
    } catch (error) {
      console.error(error);
    }
  }, []);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  const initialValues = useMemo(() => ({
    orderId: '',
    orderName: '',
    orderTypeId: '',
    orderTypeName: '',
    expectedDays: '',
    price: '',
    status: 'UNASSIGNED',
    startTimeStamp: '',
    endTimeStamp: '',
    lastModificationTimeStamp: '',
    comments: '',
    userId: '',
    userName: '',
    userFirstName: '',
    userLastName: '',
    userEmail: '',
    customerId: '',
    customerFirstName: '',
    customerLastName: '',
    customerEmail: '',
    customerCompanyName: '',
  }), []);

  const onUserEmailChange = useCallback((event, setValues, values) => {
    const emailId = event.target.value;
    const user = users.find(user => user.emailId === emailId);
    const userName = `${user.firstName} ${user.lastName}`;

    if (user) {
      setValues({
        ...values,
        userId: user.userId,
        userName,
        userFirstName: user.firstName,
        userLastName: user.lastName,
        userEmail: user.emailId,
      });
    }
  }, [users]);

  const onOrderTypeChange = useCallback((event, setValues, values) => {
    const orderTypeName = event.target.value;
    const orderType = orderTypes.find(orderType => orderType.orderTypeName === orderTypeName);

    if (orderType) {
      setValues({
        ...values,
        orderTypeName: orderType.orderTypeName,
        expectedDays: orderType.expectedDays,
        price: orderType.price,
        orderTypeId: orderType.id
      });
    }
  }, [orderTypes]);

  const onCustomerEmailChange = useCallback((event, setValues, values) => {
    const email = event.target.value;
    const customer = customers.find(customer => customer.customerEmail === email);
    if (customer) {

      const customerName = `${customer.customerFirstName} ${customer.customerLastName}`;

      setValues({
        ...values,
        customerId: customer.customerId,
        customerName,
        customerFirstName: customer.customerFirstName,
        customerLastName: customer.customerLastName,
        customerEmail: customer.customerEmail,
        customerCompanyName: customer.customerCompanyName
      });
    }
  }, [customers]);

  const onSubmit = useCallback((values) => {
    const workOrder = createWorkOrderObject(values);
    createWorkOrderApi(workOrder)
      .then(response => {
        handleClose();
        refreshWorkOrders("Work Order created successfully!");
        setMessage("Work Order created successfully!");
      })
      .catch(error => {
        console.error(error);
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



  return (
    <div>
      <Modal show={show} onHide={handleClose} animation={false} size="xl">
        <Modal.Header closeButton>
          <Modal.Title>Create Work Order</Modal.Title>
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
                                onChange={event => onOrderTypeChange(event, props.setValues, props.values)}
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
                                onChange={event => onUserEmailChange(event, props.setValues, props.values)}
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
                          <tr>
                            <h5>Customer details</h5>
                          </tr>
                          <tr>
                            <td><label>Email</label></td>
                            <td>
                              <select
                                value={props.values.customerEmail}
                                onChange={event => onCustomerEmailChange(event, props.setValues, props.values)}
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
                  <div className="form-group d-flex justify-content-between">
                    <Button variant="success" type="submit">Save</Button>
                    <Button variant="secondary" onClick={handleClose} style={{ marginLeft: "10px" }}>Close<i className="bi-x"></i></Button>
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

CreateWorkOrderModal.propTypes = {
  show: PropTypes.bool.isRequired,
  handleClose: PropTypes.func.isRequired,
  refreshWorkOrders: PropTypes.func.isRequired,
};

export default CreateWorkOrderModal;