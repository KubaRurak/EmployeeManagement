import { useTable, useFilters, useSortBy} from 'react-table'
import { useMemo} from "react"
import DefaultColumnFilter from './DefaultColumnFilter'
import './TableContainer.css';


function TableContainer({ columns, data }) {

    const defaultColumn = useMemo(
        () => ({
            // Default Filter UI
            Filter: DefaultColumnFilter,
            initialState: { pageIndex: 0, pageSize: 2 },
            style: {minWidth:'50px', maxWidth:'200px'}
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
          <div className="card-body">
            <table className="table" {...getTableProps()}>
              <thead>
                {headerGroups.map((headerGroup) => (
                  <tr {...headerGroup.getHeaderGroupProps()}>
                    {headerGroup.headers.map((column) => (
                      <th
                        {...column.getHeaderProps(column.getSortByToggleProps(), {
                            style: { minWidth: column.minWidth, width: `${column.width}px`},
                        })}
                      >
                        {column.render("Header")}
                        {((headerGroup.headers.indexOf(column) !== headerGroup.headers.length - 1)
                        && (headerGroup.headers.indexOf(column) !== headerGroup.headers.length - 2)) && (
                        <span className="sortable-column">
                            {column.isSorted ? (column.isSortedDesc ?
                             <i class="bi bi-arrow-down"></i> :
                             <i class="bi bi-arrow-up"></i>
                             ) 
                              : <i class="bi bi-arrow-down-up"></i>}
                        </span>)}
                        {/* Render the columns filter UI */}
                        <div>
                          {column.canFilter ? column.render("Filter") : null}
                        </div>
                      </th>
                    ))}
                  </tr>
                ))}
              </thead>
              <tbody {...getTableBodyProps()}>
                {rows.map((row, i) => {
                  prepareRow(row);
                  return (
                    <tr {...row.getRowProps()}>
                      {row.cells.map((cell) => {
                        return <td {...cell.getCellProps()}
                        style={{
                            minWidth: cell.column.minWidth,
                            width: `${cell.column.width}px`}}>{cell.render("Cell")}</td>;
                      })}
                    </tr>
                  );
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