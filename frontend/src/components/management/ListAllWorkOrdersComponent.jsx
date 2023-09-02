import { useEffect, useState, useMemo } from "react"
import { getFilteredWorkOrdersApi } from "./api/WorkOrdersApiService"
import TableContainer from './table/TableContainer'
import { useAuth } from "./security/AuthContext"
import WorkOrderDetailsModal from './WorkOrderDetailsModal';
import EditWorkOrderDetailsModal from './EditWorkOrderDetailsModal';
import CreateWorkOrderModal from './CreateWorkOrderModal';
import DatePickerComponent from "./DatePickerComponent";


function ListAllWorkOrdersComponent() {


  const authContext = useAuth()
  const [showDetailsModal, setShowDetailsModal] = useState(false);
  const handleCloseDetailsModal = () => setShowDetailsModal(false);
  const handleShowDetailsModal = () => setShowDetailsModal(true);

  const [showEditModal, setShowEditModal] = useState(false);
  const handleCloseEditModal = () => setShowEditModal(false);
  const handleShowEditModal = () => setShowEditModal(true);

  const [showCreateModal, setShowCreateModal] = useState(false);
  const handleCloseCreateModal = () => setShowCreateModal(false);
  const handleShowCreateModal = () => setShowCreateModal(true);

  // const userId = authContext.userId

  const [data, setData] = useState([])
  const [selectedWorkOrder, setSelectedWorkOrder] = useState(null);

  const today = new Date();
  const last30Days = new Date(today);
  last30Days.setDate(last30Days.getDate() - 30);

  const [startDate, setStartDate] = useState(last30Days);
  const [endDate, setEndDate] = useState(today);

  useEffect(() => refreshWorkOrders(), [startDate, endDate])

  function refreshWorkOrders() {

    const after = startDate ? startDate.toISOString().substring(0, 10) : last30Days.toISOString().substring(0, 10);
    const before = endDate ? endDate.toISOString().substring(0, 10) : today.toISOString().substring(0, 10);

    getFilteredWorkOrdersApi(undefined, after, before)
      .then(response => {
        setData(response.data);
      })
      .catch(error => console.log(error));
  }

  function showWorkOrderDetails(workOrder) {
    return () => {
      setSelectedWorkOrder(workOrder);
      handleShowDetailsModal();
    }
  }

  function editWorkOrderDetails(workOrder) {
    return () => {
      setSelectedWorkOrder(workOrder);
      handleShowEditModal();
    }
  }

  function showCreateWorkOrder() {
    return () => {
      handleShowCreateModal();
    }
  }

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
        Cell: ({ cell }) => cell.value ? <div title={cell.value}>{cell.value.substring(0, 10)}...</div> : <div></div>, // textoverflowelipsis itp
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
        Header: "Assigned to",
        accessor: cell => `${cell.userFirstName} ${cell.userLastName}`,
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
          <button type="button" className="btn btn-primary" onClick={editWorkOrderDetails(cell.row.original)}><i className="bi bi-pencil"></i></button>
        ),
        disableSortBy: true,
        width: 30,
      },

    ],
    [editWorkOrderDetails]
  )



  return (
    <>
      <div style={{ width: "100%", display: "flex", alignItems: "center", justifyContent: "center", margin: "20px" }}>
        <div style={{ position: "absolute", left: "0", marginLeft: "15px" }}>
          <DatePickerComponent
            startDate={startDate}
            setStartDate={setStartDate}
            endDate={endDate}
            setEndDate={setEndDate} />
        </div>
        <h2 style={{ marginLeft: "10px", flex: "1", textAlign: "center" }}>Work Orders</h2>
        <button type="button" className="btn btn-primary" style={{ marginRight: "43px" }} onClick={handleShowCreateModal}>Add new</button>
      </div>
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
      />
      <EditWorkOrderDetailsModal
        show={showEditModal}
        handleClose={handleCloseEditModal}
        selectedWorkOrder={selectedWorkOrder}
        refreshWorkOrders={refreshWorkOrders}
      />
      <CreateWorkOrderModal
        show={showCreateModal}
        handleClose={handleCloseCreateModal}
        refreshWorkOrders={refreshWorkOrders}
      />
    </>
  );
}

export default ListAllWorkOrdersComponent