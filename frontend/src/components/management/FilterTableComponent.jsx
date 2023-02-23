import { useEffect, useState, useMemo} from "react"
import { getFilteredWorkOrdersApi } from "./api/WorkOrderService"
import TableContainer from './TableContainer'
import { useAuth } from "./security/AuthContext"
import './FilterTableComponent.css';


function FilterTableComponent() {

    const authContext = useAuth()

    const userId = authContext.userId
  
    const [data,setData] = useState([])
    
    useEffect ( () => refreshWorkOrders(), [])

    function refreshWorkOrders() {
        const today = new Date();
        const last30Days = new Date(today);
        last30Days.setDate(last30Days.getDate() - 30);
      
        getFilteredWorkOrdersApi(undefined, last30Days.toISOString().substring(0,10), today.toISOString().substring(0,10))
          .then(response => {
            setData(response.data);
          })
          .catch(error => console.log(error));
      }

    const columns = useMemo(
        () => [
            {
                Header: "Order Name",
                accessor: "orderName",
              },
              {
                Header: "Order Type",
                accessor: "orderType",
                width: 100,
              },
              {
                Header: "Order Price",
                accessor: "price",
                width: 50,
              },
              {
                Header: "Status",
                accessor: "isActive",
              },
              {
                Header: "Start Time",
                accessor: "startTimeStamp",
              },
              {
                Header: "End Time",
                accessor: "endTimeStamp",
              },
              {
                Header: "Last Mod",
                accessor: "lastModificationTimeStamp",
              },
              {
                Header: "Comments",
                accessor: "comments",
              },
              {
                Header: "Assigned to",
                accessor: "assigneeEmail",
              },
              
              {
                Header: () => null,
                id: "orderId",
              },
              
        ],
        []
    )

    return (
        <TableContainer columns={columns} data={data} />
    )
}

export default FilterTableComponent