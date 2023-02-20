import { useEffect, useState, useMemo} from "react"
import { getFilteredWorkOrdersApi } from "./api/WorkOrderService"
import { useTable } from 'react-table'
import { useAuth } from "./security/AuthContext"
import moment from 'moment';

function ListWorkOrdersComponent() {

    const authContext = useAuth()

    const userId = authContext.userId
  
    const [workOrders,setworkOrders] = useState([])
    
    useEffect ( () => refreshWorkOrders(), [])

    function refreshWorkOrders() {
        
        getFilteredWorkOrdersApi(userId)
        .then(response => {
            setworkOrders(response.data)
        }
            
        )
        .catch(error => console.log(error))
    
    }

    const columns = useMemo(
        () => [
          {
            Header: "Order Name",
            accessor: "workOrder.orderName",
          },
          {
            Header: "Order Type",
            accessor: "workOrder.orderName",
          },
          {
            Header: "Order Price",
            accessor: "workOrder.price",
          },
          {
            Header: "Status",
            accessor: "workorder.isActive",
          },
          {
            Header: "Start Time",
            accessor: "workorder.startTimeStamp",
          },
          {
            Header: "End Time",
            accessor: "workorder.endTimeStamp",
          },
          {
            Header: "Last Mod",
            accessor: "workorder.lastModificationTimeStamp",
          },
          {
            Header: "Comments",
            accessor: "workorder.comments",
          },
          {
            Header: "Assigned to",
            accessor: "workorder.assigneeEmail",
          },
        ],
        []
      )

      const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        rows,
        prepareRow,
    } = useTable({
        columns,
        workOrders,
    })

    return (
  <div className="container">
    <table className="table" {...getTableProps()}>
      <thead>
        {headerGroups && headerGroups.map(headerGroup => (
          <tr {...headerGroup.getHeaderGroupProps()}>
            {headerGroup.headers.map(column => (
              <th {...column.getHeaderProps()}>{column.render('Header')}</th>
            ))}
          </tr>
        ))}
      </thead>
      <tbody {...getTableBodyProps()}>
        {rows.map((row, i) => {
          prepareRow(row)
          return (
            <tr {...row.getRowProps()}>
              {row.cells.map(cell => {
                return <td {...cell.getCellProps()}>{cell.render('Cell')}</td>
              })}
            </tr>
          )
        })}
      </tbody>
    </table>
  </div>
    )
}

export default ListWorkOrdersComponent