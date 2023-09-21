import { useEffect, useState, useMemo, useCallback } from "react"
import { getFilteredWorkOrdersApi } from "../api/WorkOrdersApiService"
import TableContainer from '../table/TableContainer'
import { useAuth } from "../security/AuthContext"
import WorkOrderDetailsModal from './WorkOrderDetailsModal';
import EditWorkOrderDetailsModal from './EditWorkOrderDetailsModal';
import CreateWorkOrderModal from './CreateWorkOrderModal';
import DatePickerComponent from "../DatePickerComponent";
import {Chip} from '@mui/material';
import statusColors from '../statusColors.js';

function ListAllWorkOrdersComponent() {


  const authContext = useAuth()
  const userRole = authContext.role;
  const [showDetailsModal, setShowDetailsModal] = useState(false);
  const handleCloseDetailsModal = () => setShowDetailsModal(false);
  const handleShowDetailsModal = useCallback(() => setShowDetailsModal(true), []);

  const [showEditModal, setShowEditModal] = useState(false);
  const handleCloseEditModal = () => setShowEditModal(false);
  const handleShowEditModal = useCallback(() => setShowEditModal(true), []);

  const [showCreateModal, setShowCreateModal] = useState(false);
  const handleCloseCreateModal = () => setShowCreateModal(false);
  const handleShowCreateModal = useCallback(() => setShowCreateModal(true), []);

  const [message, setMessage] = useState("");



  const [data, setData] = useState([])
  const [selectedWorkOrder, setSelectedWorkOrder] = useState(null);

  const today = useMemo(() => new Date(), []);
  const last30Days = useMemo(() => {
    const date = new Date(today);
    date.setDate(date.getDate() - 30);
    return date;
  }, [today]);

  const [startDate, setStartDate] = useState(last30Days);
  const [endDate, setEndDate] = useState(today);

  const refreshWorkOrders = useCallback((message = "") => {
    setMessage(message);
    const after = startDate ? startDate.toISOString().substring(0, 10) : last30Days.toISOString().substring(0, 10);
    const before = endDate ? endDate.toISOString().substring(0, 10) : today.toISOString().substring(0, 10);

    getFilteredWorkOrdersApi(undefined, after, before)
      .then(response => {
        setData(response.data);
      })
      .catch(error => console.log(error));
  }, [startDate, endDate, last30Days, today, setMessage, setData]);

  useEffect(() => {
    setMessage("");
    refreshWorkOrders();
  }, [refreshWorkOrders, setMessage, today, last30Days]);

  const showWorkOrderDetails = useCallback((workOrder) => {
    return () => {
      setSelectedWorkOrder(workOrder);
      handleShowDetailsModal();
    }
  }, [handleShowDetailsModal, setSelectedWorkOrder]);

  const editWorkOrderDetails = useCallback((workOrder) => {
    return () => {
      setSelectedWorkOrder(workOrder);
      handleShowEditModal();
    }
  }, [handleShowEditModal, setSelectedWorkOrder]);


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
        Header: "Comment",
        accessor: "comments",
        Cell: ({ cell }) => <div title={cell.value}>{cell.value.substring(0, 10)}...</div>,
        width: 100,
      },
      {
        Header: "Assigned to",
        accessor: cell => {
          if (!cell.userFirstName && !cell.userLastName) {
            return "";
          }
          return `${cell.userFirstName || ''} ${cell.userLastName || ''}`.trim();
        },
        width: 180,
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
          <button
            type="button"
            className="btn btn-primary"
            onClick={editWorkOrderDetails(cell.row.original)}
            disabled={userRole === "Engineer"}
          >
            <i className="bi bi-pencil"></i>
          </button>
        ),
        disableSortBy: true,
        width: 30,
      },

    ],
    [editWorkOrderDetails, showWorkOrderDetails, userRole]
  )



  return (
    <>
      <div style={{ width: "100%", display: "flex", alignItems: "center", justifyContent: "center", margin: "20px" }}>
        <div style={{ position: "absolute", left: "0", marginLeft: "45px" }}>
          <DatePickerComponent
            startDate={startDate}
            setStartDate={setStartDate}
            endDate={endDate}
            setEndDate={setEndDate} />
        </div>
        <h2 style={{ marginLeft: "10px", flex: "1", textAlign: "center" }}>Work Orders</h2>
        <button type="button" className="btn btn-primary" style={{ marginRight: "73px" }} disabled={userRole === "Engineer"} onClick={handleShowCreateModal}>Add new</button>
      </div>
      {message && <div className="alert alert-success">{message}</div>}
      <TableContainer
        columns={columns}
        data={data}
        defaultPageSize={10}
        pageSizeOptions={[10, 20, 30, 40, 50]}
        showPaginationBottom={true} />
      <WorkOrderDetailsModal
        show={showDetailsModal}
        handleClose={handleCloseDetailsModal}
        selectedWorkOrder={selectedWorkOrder}
        handleShowEditModal={handleShowEditModal}
        userRole={userRole}
      />
      <EditWorkOrderDetailsModal
        show={showEditModal}
        handleClose={handleCloseEditModal}
        selectedWorkOrder={selectedWorkOrder}
        refreshWorkOrders={refreshWorkOrders}
        setMessage={setMessage}
      />
      <CreateWorkOrderModal
        show={showCreateModal}
        handleClose={handleCloseCreateModal}
        refreshWorkOrders={refreshWorkOrders}
        setMessage={setMessage}
      />
    </>
  );
}

export default ListAllWorkOrdersComponent