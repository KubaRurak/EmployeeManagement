import { useEffect, useState } from "react"
import { getActiveWorkOrdersApi } from "./api/WorkOrderService"
import { useAuth } from "./security/AuthContext"
import moment from 'moment';

function ListActiveWorkOrdersComponent() {

    const authContext = useAuth()

    const userId = authContext.userId
    const [message,setMessage] = useState(null)
  
    const [workOrders,setworkOrders] = useState([])
    
    useEffect ( () => refreshWorkOrders(), [])

    function refreshWorkOrders() {
        
        getActiveWorkOrdersApi(userId)
        .then(response => {
            setworkOrders(response.data)
        }
            
        )
        .catch(error => console.log(error))
    
    }

    return (
        <div className="container">
            <h1>Things You Want To Do!</h1>
            {message && <div className="alert alert-warning">{message}</div>}
            <div>
                <table className="table">
                    <thead>
                            <tr>
                                <th>Name</th>
                                <th>Type</th>
                                <th>Price</th>
                                <th>Start time</th>
                                <th>Last modification time</th>
                            </tr>
                    </thead>
                    <tbody>
                    {
                        workOrders.map(
                            workOrder => (
                                <tr key={workOrder.orderId}>
                                    <td>{workOrder.orderName}</td>
                                    <td>{workOrder.orderType}</td>
                                    <td>{workOrder.price.toString()}</td>
                                    <td>{moment(workOrder.startTimeStamp).format("YYYY-DD-MM HH:mm:ss")}</td>
                                    <td>{moment(workOrder.lastModificationTimeStamp).format("YYYY-DD-MM HH:mm:ss")}</td>
                                </tr>
                            )
                        )
                    }
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default ListActiveWorkOrdersComponent