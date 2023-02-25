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
        (props) => [
            {
                Header: "Order Name",
                accessor: "orderName",
                minWidth: 60,
              },
              {
                Header: "Order Type",
                accessor: "orderType",
                minWidth: 50,
              },
              {
                Header: "Order Price",
                accessor: "price",
                minWidth: 50,
              },
              {
                Header: "Status",
                accessor: "isActive",
                width: "col col-lg-1",
              },
              {
                Header: "Start Time",
                accessor: "startTimeStamp",
                width: "col col-lg-2",
              },
              {
                Header: "End Time",
                accessor: "endTimeStamp",
                width: "col col-lg-2",
              },
              {
                Header: "Last Mod",
                accessor: "lastModificationTimeStamp",
                width: "col col-lg-2",
              },
              {
                Header: "Comments",
                accessor: "comments",
                width: "col col-lg-2",
              },
              {
                Header: "Assigned to",
                accessor: "assigneeEmail",
                width: "col col-lg-2",
              },
              {
                Header: " ",
                Cell: ({ cell }) => (
                  <button>
                  </button>
                ),
                disableSortBy: true,
                width: "col col-lg-1",
              },
              
        ],
        []
    )

    return (
        <TableContainer columns={columns} data={data} />
    )
}

export default FilterTableComponent