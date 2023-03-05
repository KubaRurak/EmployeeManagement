import { useTable, useFilters, useSortBy, usePagination} from 'react-table'
import { useMemo} from "react"
import DefaultColumnFilter from './DefaultColumnFilter'
import './TableContainer.css';

function TableContainer({ columns, data }) {

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
        prepareRow,
        page,
        canPreviousPage,
        canNextPage,
        pageOptions,
        pageCount,
        gotoPage,
        nextPage,
        previousPage,
        setPageSize,
        state: { pageIndex, pageSize },
    } = useTable(
        {
            columns,
            data,
            defaultColumn,
            initialState: { pageIndex: 0, pageSize: 10 },
        },
        useFilters,
        useSortBy,
        usePagination,
    )

    const onChangeInSelect = (event) => {
      setPageSize(Number(event.target.value));
    };
  
    const onChangeInInput = (event) => {
      const page = event.target.value ? Number(event.target.value) - 1 : 0;
      gotoPage(page);
    };

    return (
        <div className="card">
          <div className="card-body">
            <table className="table"
                     {...getTableProps()}
                     defaultPageSize={10}
                     pageSizeOptions={[10, 20, 30, 40, 50]}
                     showPaginationBottom={true}>
              <thead>
                {headerGroups.map((headerGroup) => (
                  <tr {...headerGroup.getHeaderGroupProps()}>
                    {headerGroup.headers.map((column) => (
                      <th
                        {...column.getHeaderProps(column.getSortByToggleProps(), {
                            style: {minWidth: column.minWidth, width: column.width, maxWidth: column.maxWidth},
                        })}
                      >
                        {column.render("Header")}
                        {((headerGroup.headers.indexOf(column) !== headerGroup.headers.length - 1)
                        && (headerGroup.headers.indexOf(column) !== headerGroup.headers.length - 2)) && (
                        <span className="sortable-column">
                            {column.isSorted ? (column.isSortedDesc ?
                             <i className="bi bi-arrow-down"></i> :
                             <i className="bi bi-arrow-up"></i>
                             ) 
                              : <i className="bi bi-arrow-down-up"></i>}
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
                {page.map((row, i) => {
                  prepareRow(row);
                  return (
                    <tr {...row.getRowProps()}>
                      {row.cells.map((cell) => {
                        return <td {...cell.getCellProps()}
                        style={{
                            minWidth: cell.column.minWidth,
                            width: cell.column.width}}>{cell.render("Cell")}</td>;
                      })}
                    </tr>
                  );
                })}
              </tbody>
            </table>
                <br />
                <div style={{ maxWidth: 1000, margin: '0 auto', textAlign: 'center', display: 'flex', justifyContent: 'center' }}>
                <div style={{ flex: 1 }}>
                  <button
                    className="btn btn-primary"
                    onClick={() => gotoPage(0)}
                    disabled={!canPreviousPage}
                  >
                    {'<<'}
                  </button>
                  <button
                    className="btn btn-primary"
                    onClick={previousPage}
                    disabled={!canPreviousPage}
                  >
                    {'<'}
                  </button>
                </div>
                <div style={{ flex: 1 }}>
                  Page{' '}
                  <strong>
                    {pageIndex+1} of {pageOptions.length}
                  </strong>
                </div>
                <div style={{ flex: 1 }}>
                  <input
                    type='number'
                    min={1}
                    style={{ width: 70, borderRadius: '5px', marginRight: '5px', border: '1px solid #ccc', padding: '5px' }}
                    max={pageOptions.length}
                    defaultValue={pageIndex+1}
                    onChange={onChangeInInput}
                  />
                </div>
                <div style={{ flex: 1 }}>
                  <select
                    style={{ borderRadius: '5px', marginRight: '5px', padding: '5px' }}
                    value={pageSize}
                    onChange={onChangeInSelect}
                  >
                    {[10, 20, 30, 40, 50].map((pageSize) => (
                      <option key={pageSize} value={pageSize}>
                        Show {pageSize}
                      </option>
                    ))}
                  </select>
                </div>
                <div style={{ flex: 1 }}>
                  <button
                    className="btn btn-primary"
                    onClick={nextPage}
                    disabled={!canNextPage}
                  >
                    {'>'}
                  </button>
                  <button
                    className="btn btn-primary"
                    onClick={() => gotoPage(pageCount - 1)}
                    disabled={!canNextPage}
                  >
                    {'>>'}
                  </button>
                </div>
              </div>
            </div>
        </div>
    )
}



export default TableContainer