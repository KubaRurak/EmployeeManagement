import { useEffect, useState, useMemo} from "react"
import { getActiveWorkOrdersApi, completeWorkOrderApi} from "./api/WorkOrdersApiService"
import TableContainer from './table/TableContainer'
import { useAuth } from "./security/AuthContext"
import WorkOrderDetailsModal from './WorkOrderDetailsModal';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';

function ListActiveWorkOrdersComponent() {


    const authContext = useAuth()

    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [showConfirmation, setShowConfirmation] = useState(false);
    const handleShowConfirmation = () => setShowConfirmation(true);

    function handleCloseConfirmation (){
        setShowConfirmation(false);
        setSelectedWorkOrder(null);
        setMessage("Work Order completed successfully")
    }
    function handleEscapeConfirmation (){
        setShowConfirmation(false);
        setSelectedWorkOrder(null);
        setMessage(null)
    }
    const userId = authContext.userId
  
    const [data,setData] = useState([])
    const [selectedWorkOrder, setSelectedWorkOrder] = useState(null);

    useEffect ( () => refreshWorkOrders(), [])
  
    function refreshWorkOrders() {
    
      getActiveWorkOrdersApi(userId)
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

    function showConfirmationModal(workOrder){
        return () => {
            setSelectedWorkOrder(workOrder);
            handleShowConfirmation();
          }
    }

    const [message,setMessage] = useState(null)


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
        []
    )



    return (
      <>
      <div>
      <h2>Your active work orders </h2>
      {message && <div className="alert alert-success">{message}</div>}
      </div>
        <TableContainer 
        columns={columns} 
        data={data} />
        <WorkOrderDetailsModal
          show={show}
          handleClose={handleClose}
          selectedWorkOrder={selectedWorkOrder}
          completeWorkOrder={completeWorkOrder()}
          fromActive={true}
        />
        {/* do przeniesienia w oddzielny plik */}
            <Modal show={showConfirmation} onHide={handleEscapeConfirmation}>
                <Modal.Header closeButton>
                    <Modal.Title>Confirm Completion </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Are you sure you want to complete this work order?
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleEscapeConfirmation}>
                        Cancel <i className="bi-x"></i>
                    </Button>
                    <Button variant="primary" onClick={completeWorkOrder()}>
                        Complete Work Order <i className="bi-check-lg"></i>
                    </Button>
                </Modal.Footer>
            </Modal>
      </>
    );
}

export default ListActiveWorkOrdersComponent