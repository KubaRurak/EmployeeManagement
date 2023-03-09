import React from 'react';
import { useState, useEffect } from 'react'
import { Modal, Button, Row, Col } from 'react-bootstrap';
import { Formik, Form, Field, ErrorMessage } from 'formik'
import { editWorkOrderApi } from "./api/WorkOrdersApiService"
import { retrieveCustomersApi } from "./api/CustomerApiService"
import { retrieveOrderTypesApi } from "./api/OrderTypeApiService"


function EditWorkOrderDetailsModal({ show, handleClose, selectedWorkOrder, closeWorkOrder, refreshWorkOrders }) {


  const [customers, setCustomers] = useState();
  const [orderTypes, setOrderTypes] = useState();


  useEffect(() => {
    Promise.all([
      retrieveCustomersApi(),
      retrieveOrderTypesApi()
    ])
      .then(([customerResponse, ordertypeResponse]) => {
        setCustomers(customerResponse.data);
        setOrderTypes(ordertypeResponse.data);
      })
      .catch(error => console.log(error));
  }, []);

  console.log("customers ", customers)
  console.log("OrderTypes ", orderTypes)


  const [initialValues, setInitialValues] = useState({});

  useEffect(() => {
    setInitialValues({
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
      userFirstName: selectedWorkOrder?.userFirstName,
      userLastName: selectedWorkOrder?.userLastName,
      userEmail: selectedWorkOrder?.userEmail,
      customerId: selectedWorkOrder?.customerId,
      customerFirstName: selectedWorkOrder?.customerFirstName,
      customerLastName: selectedWorkOrder?.customerLastName,
      customerEmail: selectedWorkOrder?.customerEmail,
      customerCompanyName: selectedWorkOrder?.customerCompanyName
    });
  }, [selectedWorkOrder]);

  function onSubmit(values) {

    const workOrder = {
      orderId: selectedWorkOrder?.orderId,
      orderName: values.orderName,
      orderType: {
        id: selectedWorkOrder?.orderType.id,
        orderTypeName: values.orderTypeName,
        expectedDays: values.expectedDays,
        price: values.price,
      },
      status: values.status,
      startTimeStamp: selectedWorkOrder?.startTimeStamp,
      endTimeStamp: selectedWorkOrder?.endTimeStamp,
      lastModificationTimeStamp: selectedWorkOrder?.endTimeStamp,
      comments: values.comments,
      userId: selectedWorkOrder?.userId,
      userFirstName: selectedWorkOrder?.userFirstName,
      userLastName: selectedWorkOrder?.userLastName,
      userEmail: selectedWorkOrder?.userEmail,
      customerId: selectedWorkOrder?.customerId,
      customerFirstName: selectedWorkOrder?.customerFirstName,
      customerLastName: selectedWorkOrder?.customerLastName,
      customerEmail: selectedWorkOrder?.customerEmail,
      customerCompanyName: selectedWorkOrder?.customerCompanyName
    }

    console.log("Workorder ", workOrder)
    console.log("Order id ", selectedWorkOrder?.orderId)
    editWorkOrderApi(selectedWorkOrder?.orderId, workOrder)
      .then(response => {
        handleClose();
        refreshWorkOrders();
        console.log("submitted")
      })
      .catch(error => console.log(error))
  }

  // function onCustomerEmailChange(email, setValues) {
  //   const customer = customers.find(customer => customer.customerEmail === email);
  //   if (customer) {
  //     setValues({
  //       ...initialValues,
  //       customerId: customer.customerId,
  //       customerFirstName: customer.customerFirstName,
  //       customerLastName: customer.customerLastName,
  //       customerEmail: customer.customerEmail,
  //       customerCompanyName: customer.customerCompanyName
  //     });
  //   }
  // }

  // const customerEmailOptions = customers.map(customer => (
  //   <option key={customer.customerId} value={customer.customerEmail}>
  //     {customer.customerEmail}
  //   </option>
  // ));


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
                  <ErrorMessage
                    name="description"
                    component="div"
                    className="alert alert-warning"
                  />

                  <ErrorMessage
                    name="targetDate"
                    component="div"
                    className="alert alert-warning"
                  />
                  <Row>
                    <Col>
                      <h5>Order Details</h5>
                      <table className="table">
                        <tbody>
                          <tr>
                            <fieldset className="form-group">
                              <td><label>Order Name</label></td>
                              <td><Field type="text" className="form-control" name="orderName" value={props.values.orderName} /></td>
                            </fieldset>
                          </tr>
                          <tr>
                            <fieldset className="form-group">
                              <td><label>Type</label></td>
                              <td><Field type="text" className="form-control" name="orderTypeName" value={props.values.orderTypeName} /></td>
                            </fieldset>
                          </tr>
                          <tr>
                            <fieldset className="form-group">
                              <td><label>Price</label></td>
                              <td><Field type="text" className="form-control" name="price" value={props.values.price} /></td>
                            </fieldset>
                          </tr>
                          <tr>
                            <fieldset className="form-group">
                              <td><label>Expected days</label></td>
                              <td><Field type="text" className="form-control" name="expectedDays" value={props.values.expectedDays} /></td>
                            </fieldset>
                          </tr>
                          <tr>
                            <fieldset className="form-group">
                              <td><label>Status</label></td>
                              <td><Field type="text" className="form-control" name="status" value={props.values.status} /></td>
                            </fieldset>
                          </tr>
                          <tr>
                            <fieldset className="form-group">
                              <td><label>Comments</label></td>
                              <td><Field type="text" className="form-control" name="comments" value={props.values.comments} /></td>
                            </fieldset>
                          </tr>
                        </tbody>
                      </table>
                    </Col>
                    {/* <Col>
                      <h5>Customer</h5>
                      <table className="table">
                        <tbody>
                          <tr>
                            <fieldset className="form-group">
                              <td><label>Customer Email</label></td>
                              <td><select value={props.values.customerEmail} onChange={onCustomerEmailChange}>
                                <option value="">Select an email</option>
                                {customers.map(customer => (
                                  <option key={customer.customerId} value={customer.customerEmail}>
                                    {customer.email}
                                  </option>
                                ))}
                              </select></td>
                            </fieldset>
                          </tr>
                          <tr>
                            <fieldset className="form-group">
                              <td><label>First Name</label></td>
                              <td><Field type="text" className="form-control" name="customerFirstName" value={props.values.customerFirstName} readOnly/></td>
                            </fieldset>
                          </tr>
                          <tr>
                          <fieldset className="form-group">
                              <td><label>Last Name</label></td>
                              <td><Field type="text" className="form-control" name="customerLastName" value={props.values.customerLastName} readOnly/></td>
                            </fieldset>
                          </tr>
                          <tr>
                            <fieldset className="form-group">
                              <td><label>Company name</label></td>
                              <td><Field type="text" className="form-control" name="customerCompanyName" value={props.values.customerCompanyName} readOnly/></td>
                            </fieldset>
                          </tr>
                        </tbody>
                      </table>
                    </Col> */}
                  </Row>
                  <div>
                    <button className="btn btn-success m-5" type="submit">Save</button>
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

export default EditWorkOrderDetailsModal;

