import { useEffect, useState, useMemo, useCallback } from "react"
import { getActiveWorkOrdersApi, completeWorkOrderApi } from "../api/WorkOrdersApiService"
import TableContainer from '../table/TableContainer'
import { useAuth } from "../security/AuthContext"
import WorkOrderDetailsModal from './WorkOrderDetailsModal';
import ConfirmationModal from './ConfirmationModal';
import statusColors from '../statusColors.js';
import { Chip } from '@mui/material';

function ListActiveWorkOrdersComponent() {


  const authContext = useAuth()
  const userId = authContext.userId
  const userRole = authContext.userRole

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [showConfirmation, setShowConfirmation] = useState(false);
  const handleShowConfirmation = () => setShowConfirmation(true);

  function handleCloseConfirmation() {
    setShowConfirmation(false);
    setSelectedWorkOrder(null);
    setMessage("Work Order completed successfully")
  }
  function handleEscapeConfirmation() {
    setShowConfirmation(false);
    setSelectedWorkOrder(null);
    setMessage(null)
  }

  const [data, setData] = useState([])
  const [selectedWorkOrder, setSelectedWorkOrder] = useState(null);

  const refreshWorkOrders = useCallback(() => {
    getActiveWorkOrdersApi(userId)
      .then(response => {
        setData(response.data);
      })
      .catch(error => console.log(error));
  }, [userId]);

  useEffect(() => {
    if (userId) {
      refreshWorkOrders();
    }
  }, [userId, refreshWorkOrders]);

  function completeWorkOrder() {
    if (!selectedWorkOrder) {
      return;
    }
    return () => {
      completeWorkOrderApi(selectedWorkOrder.orderId)
        .then(() => {
          setData(prevData => prevData.filter(item => item.orderId !== selectedWorkOrder.orderId));
          handleCloseConfirmation();
        })
        .catch(error => console.log(error));
    }
  }

  const showWorkOrderDetails = useCallback((workOrder) => () => {
    setSelectedWorkOrder(workOrder);
    handleShow();
  }, []);

  const showConfirmationModal = useCallback((workOrder) => () => {
    setSelectedWorkOrder(workOrder);
    handleShowConfirmation();
  }, []);

  const [message, setMessage] = useState(null)


  const columns = useMemo(
    (props) => [
      {
        Header: "Order Name",
        accessor: "orderName",
        width: 130,
        Cell: ({ value }) => <div style={{fontWeight: 500}}>{value}</div>
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
        width: 150,
        Cell: ({ value }) => (
          <Chip
            sx={{
              px: "4px",
              backgroundColor: statusColors[value],
              color: "#fff"
            }}
            size="small"
            label={value}
          />
        )
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
      {
        Header: " ",
        Cell: ({ cell }) => (
          <button type="button" className="btn btn-primary" onClick={showWorkOrderDetails(cell.row.original)}><i className="bi bi-search"></i></button>
        ),
        disableSortBy: true,
        width: 30,
      },
      {
        Header: "  ",
        Cell: ({ cell }) => (
          <button type="button" className="btn btn-primary" onClick={showConfirmationModal(cell.row.original)}><i className="bi bi-check-lg"></i></button>
        ),
        disableSortBy: true,
        width: 30,
      },

    ],
    [showConfirmationModal, showWorkOrderDetails]
  )



  return (
    <>
      <div>
        <h2>Your active work orders </h2>
        {message && <div className="alert alert-success">{message}</div>}
      </div>
      <TableContainer
        columns={columns}
        data={data}
        nonSortableLastColumns={2} />
      <WorkOrderDetailsModal
        show={show}
        handleClose={handleClose}
        selectedWorkOrder={selectedWorkOrder}
        completeWorkOrder={completeWorkOrder()}
        fromActive={true}
        userRole={userRole}
      />
      <ConfirmationModal
        showConfirmation={showConfirmation}
        handleEscapeConfirmation={handleEscapeConfirmation}
        completeWorkOrder={completeWorkOrder()}
      />
    </>
  );
}

export default ListActiveWorkOrdersComponent