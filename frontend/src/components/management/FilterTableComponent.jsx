import { useEffect, useState, useMemo} from "react"
import { getFilteredWorkOrdersApi } from "./api/WorkOrderService"
import TableContainer from './table/TableContainer'
import { useAuth } from "./security/AuthContext"
import WorkOrderDetailsModal from './WorkOrderDetailsModal';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

function FilterTableComponent() {


    const authContext = useAuth()

    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    // const userId = authContext.userId
  
    const [data,setData] = useState([])
    const [selectedWorkOrder, setSelectedWorkOrder] = useState(null);

    const today = new Date();
    const last30Days = new Date(today);
    last30Days.setDate(last30Days.getDate() - 30);

    const [startDate, setStartDate] = useState(last30Days);
    const [endDate, setEndDate] = useState(today);

    useEffect ( () => refreshWorkOrders(), [startDate, endDate])
  
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
        handleShow();
      }
  }

    function editWorkOrderDetails() {
    }




    const columns = useMemo(
        (props) => [
            {
                Header: "Order Name",
                accessor: "orderName",
                minWidth: 100,
                maxWidth: 120,
              },
              {
                Header: "Order Type",
                accessor: "orderType.orderTypeName",
                minWidth: 100,
                maxWidth: 120,
              },
              {
                Header: "Order Price",
                accessor: "orderType.price",
                minWidth: 100,
                maxWidth: 120,
              },
              {
                Header: "Status",
                accessor: "status",
                minWidth: 100,
                maxWidth: 120,
              },
              {
                Header: "Start Time",
                accessor: "startTimeStamp",
                minWidth: 60,
                maxWidth: 100,
                Cell: ({ cell }) => <div title={cell.value}>{cell.value.substring(0, 10)}...</div>,
              },
              {
                Header: "End Time",
                accessor: "endTimeStamp",
                minWidth: 60,
                maxWidth: 100,
                Cell: ({ cell }) => <div title={cell.value}>{cell.value.substring(0, 10)}...</div>,
              },
              {
                Header: "Last Mod",
                accessor: "lastModificationTimeStamp",
                minWidth: 60,
                maxWidth: 100,
                Cell: ({ cell }) => <div title={cell.value}>{cell.value.substring(0, 10)}...</div>,
              },
              {
                Header: "Comments",
                accessor: "comments",
                Cell: ({ cell }) => <div title={cell.value}>{cell.value.substring(0, 10)}...</div>,
                maxWidth: 120,
              },
              {
                Header: "Assigned to",
                accessor: row => `${row.userFirstName} ${row.userLastName}`,
                minWidth: 100,
                maxWidth: 150,
              },
              {
                Header: "Customer",
                accessor: row => `${row.customerFirstName} ${row.customerLastName}`,
                minWidth: 100,
                maxWidth: 150,
              },
              {
                Header: "Company",
                accessor: "customerCompanyName",
                minWidth: 100,
                maxWidth: 150,
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
                  <button type="button" class="btn btn-primary" onClick={editWorkOrderDetails}><i class="bi bi-pencil"></i></button>
                ),
                disableSortBy: true,
                width: 30,
              },
              
        ],
        []
    )

    return (
      <>
        <div style={{ width: 350 }}>
          <div className="row mb-3">
            <div className="col">
              Wybierz zakres dat
            </div>
            <div className="col">
              <DatePicker
                className="form-control"
                selected={startDate}
                onChange={(date) => setStartDate(date)}
              />
            </div>
            <div className="col">
              <DatePicker
                className="form-control"
                selected={endDate}
                onChange={(date) => setEndDate(date)}
              />
              </div>
          </div>
        </div>
        <TableContainer 
        columns={columns} 
        data={data}
        defaultPageSize={10}
        pageSizeOptions={[10, 20, 30, 40, 50]}
        showPaginationBottom={true} />
        <WorkOrderDetailsModal
          show={show}
          handleClose={handleClose}
          selectedWorkOrder={selectedWorkOrder}
          editWorkOrderDetails={editWorkOrderDetails}
        />
      </>
    );
}

export default FilterTableComponent