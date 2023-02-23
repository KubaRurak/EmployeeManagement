import { useTable, useFilters, useSortBy} from 'react-table'
import { useEffect, useState, useMemo} from "react"
import { getFilteredWorkOrdersApi } from "./api/WorkOrderService"
import { useAuth } from "./security/AuthContext"
import './FilterTableComponent.css';


function DefaultColumnFilter({
    column: { filterValue, preFilteredRows, setFilter },
}) {
    // const count = preFilteredRows.length

    return (
        <div style={{ display: 'flex', justifyContent: 'center' }}>
            <input
                className="form-control"
                value={filterValue || ''}
                onChange={e => {
                    setFilter(e.target.value || undefined)
                }}
                placeholder={`Filter`}
                // style={{ width: '100px', height: '35px'}}
            />
        </div>
    )
}

function Table({ columns, data }) {

    const defaultColumn = useMemo(
        () => ({
            // Default Filter UI
            Filter: DefaultColumnFilter,
            initialState: { pageIndex: 0, pageSize: 10 },
            style: {minwidth:'100px', maxwidth:'200px'}
        }),
        []
    )

    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        rows,
        prepareRow,
        state,
    } = useTable(
        {
            columns,
            data,
            defaultColumn
        },
        useFilters,
        useSortBy,
    )

    return (
        <div className="card">
            <div className='card-body'>
                <table className="table" {...getTableProps()}>
                    <thead>
                        {headerGroups.map(headerGroup => (
                            <tr {...headerGroup.getHeaderGroupProps()}>
                                {headerGroup.headers.map(column => (
                                    <th {...column.getHeaderProps(column.getSortByToggleProps(),{
                                        style: { minWidth: column.minWidth, width: column.width },
                                      })}>
                                        {column.render('Header')}
                                        {/* <span className='sortable-column'>
                                        {column.isSorted
                                            ? column.isSortedDesc
                                                ? ' 🔽'
                                                : ' 🔼'
                                            : ' ⚡️'}
                                        </span> */}
                                        {/* Render the columns filter UI */}
                                        <div>{column.canFilter ? column.render('Filter') : null}</div>
                                    </th>
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
                <br />
                <div>Showing the first 10 results of {rows.length} rows</div>
                <div>
                    <pre>
                        <code>{JSON.stringify(state.filters, null, 2)}</code>
                    </pre>
                </div>
            </div>
        </div>
    )
}



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
        <Table columns={columns} data={data} />
    )
}

export default FilterTableComponent;