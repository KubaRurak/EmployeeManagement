import { useTable, useFilters, useGlobalFilter, useAsyncDebounce, } from 'react-table'
import { useEffect, useState, useMemo} from "react"
import { getFilteredWorkOrdersApi } from "./api/WorkOrderService"
import { useAuth } from "./security/AuthContext"
import { all } from 'axios'

// Define a default UI for filtering
function GlobalFilter({
    preGlobalFilteredRows,
    globalFilter,
    setGlobalFilter,
}) {
    const count = preGlobalFilteredRows.length
    const [value, setValue] = useState(globalFilter)
    const onChange = useAsyncDebounce(value => {
        setGlobalFilter(value || undefined)
    }, 200)

    return (
        <span>
            Search:{' '}
            <input
                className="form-control"
                value={value || ""}
                onChange={e => {
                    setValue(e.target.value);
                    onChange(e.target.value);
                }}
                placeholder={`${count} records...`}
            />
        </span>
    )
}

function DefaultColumnFilter({
    column: { filterValue, preFilteredRows, setFilter },
}) {
    const count = preFilteredRows.length

    return (
        <input
            className="form-control"
            value={filterValue || ''}
            onChange={e => {
                setFilter(e.target.value || undefined)
            }}
            placeholder={`Search ${count} records...`}
        />
    )
}

function Table({ columns, data }) {

    const defaultColumn = useMemo(
        () => ({
            // Default Filter UI
            Filter: DefaultColumnFilter,
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
        preGlobalFilteredRows,
        setGlobalFilter,
    } = useTable(
        {
            columns,
            data,
            defaultColumn
        },
        useFilters,
        useGlobalFilter
    )

    return (
        <div>
            <GlobalFilter
                preGlobalFilteredRows={preGlobalFilteredRows}
                globalFilter={state.globalFilter}
                setGlobalFilter={setGlobalFilter}
            />
            <table className="table" {...getTableProps()}>
                <thead>
                    {headerGroups.map(headerGroup => (
                        <tr {...headerGroup.getHeaderGroupProps()}>
                            {headerGroup.headers.map(column => (
                                <th {...column.getHeaderProps()}>
                                    {column.render('Header')}
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
            <div>Showing the first 20 results of {rows.length} rows</div>
            <div>
                <pre>
                    <code>{JSON.stringify(state.filters, null, 2)}</code>
                </pre>
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
                Header: 'Work Order Name',
                accessor: 'orderName'
              }, {
                Header: 'Work Order Type',
                accessor: 'orderType'
              }
        ],
        []
    )

    return (
        <Table columns={columns} data={data} />
    )
}

export default FilterTableComponent;