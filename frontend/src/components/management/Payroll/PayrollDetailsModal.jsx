import { useState, useEffect, useMemo } from 'react';
import { Modal, Row, Col, Table } from 'react-bootstrap';
import {fetchWorkOrdersByIdsApi} from '../api/WorkOrdersApiService';
import TableContainerModal from '../table/TableContainerModal';

function PayrollDetailsModal({ show, handleClose, data, workOrderIds }) {

    const [workOrders, setWorkOrders] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if (workOrderIds && workOrderIds.length > 0) {
            setLoading(true);
            fetchWorkOrdersByIdsApi(workOrderIds)
                .then(response => {
                    setWorkOrders(response.data);
                    setLoading(false);
                })
                .catch(error => {
                    console.error("Error fetching work order details:", error);
                    setLoading(false);
                });
        }
    }, [workOrderIds]);

    const columns = useMemo(
        (props) => [
          {
            Header: "Order Name",
            accessor: "orderName",
            width: 130
          },
          {
            Header: "Type",
            accessor: "orderType.orderTypeName",
            width: 100
          },
          {
            Header: "Price",
            accessor: "orderType.price",
            width: 100
          },
          {
            Header: "Status",
            accessor: "status",
            width: 100
          },
          {
            Header: "Start Time",
            accessor: "startTimeStamp",
            width: 100,
            Cell: ({ cell }) => cell.value ? <div title={cell.value}>{cell.value.substring(0, 10)}...</div> : <div></div>,
          },
          {
            Header: "End Time",
            accessor: "endTimeStamp",
            width: 100,
            Cell: ({ cell }) => cell.value ? <div title={cell.value}>{cell.value.substring(0, 10)}...</div> : <div></div>,
          },
          {
            Header: "Last Mod",
            accessor: "lastModificationTimeStamp",
            width: 100,
            Cell: ({ cell }) => cell.value ? <div title={cell.value}>{cell.value.substring(0, 10)}...</div> : <div></div>,
          },
          {
            Header: "Comments",
            accessor: "comments",
            Cell: ({ cell }) => <div title={cell.value}>{cell.value.substring(0, 10)}...</div>,
            width: 100,
          },
          {
            Header: "Customer",
            accessor: cell => `${cell.customerFirstName} ${cell.customerLastName}`,
            width: 180,
          },
          {
            Header: "Company",
            accessor: "customerCompanyName",
            width: 100,
          },
        ],
        []
      )

    if (!data) return null;

    return (
        <Modal show={show} onHide={handleClose} animation={false} size="xl">
            <Modal.Header closeButton>
                <Modal.Title>Payroll Details</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Row>
                    <Col>
                        <h5>Employee Details</h5>
                        <table className="table">
                            <tbody>
                                <tr>
                                    <td><strong>Name</strong></td>
                                    <td>{data.userFirstName} {data.userLastName}</td>
                                </tr>
                                <tr>
                                    <td><strong>Email</strong></td>
                                    <td>{data.userEmail}</td>
                                </tr>
                                <tr>
                                    <td><strong>Role</strong></td>
                                    <td>{data.userRole}</td>
                                </tr>
                            </tbody>
                        </table>
                    </Col>
                    <Col>
                        <h5>Payroll Information</h5>
                        <table className="table">
                            <tbody>
                                <tr>
                                    <td><strong>Month</strong></td>
                                    <td>{data.payrollMonth}</td>
                                </tr>
                                <tr>
                                    <td><strong>Time Worked [h]</strong></td>
                                    <td>{data.timeWorked}</td>
                                </tr>
                                <tr>
                                    <td><strong>Money Generated</strong></td>
                                    <td>{data.moneyGenerated}</td>
                                </tr>
                            </tbody>
                        </table>
                    </Col>
                </Row>
                <TableContainerModal columns={columns} data={workOrders} />
            </Modal.Body>
            <Modal.Footer>
                <button className="btn btn-secondary" onClick={handleClose}>Close</button>
            </Modal.Footer>
        </Modal>
    );
}


export default PayrollDetailsModal;