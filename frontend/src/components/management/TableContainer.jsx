import { useTable, useFilters, useSortBy} from 'react-table'
import { useMemo} from "react"
import DefaultColumnFilter from './DefaultColumnFilter'
// import './FilterTableComponent.css';


function TableContainer({ columns, data }) {

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



export default TableContainer