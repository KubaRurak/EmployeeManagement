import React from 'react';
import { Table } from 'react-bootstrap';
import { useTable, useSortBy } from 'react-table';
import './TableContainerModal.css';

function TableContainerModal({ columns, data }) {
  const tableInstance = useTable({
    columns,
    data
  }, useSortBy);


  return (
    <div style={{ height: '300px', overflowY: 'scroll' }}>
      <Table {...tableInstance.getTableProps()}>
        <thead>
          {tableInstance.headerGroups.map(headerGroup => (
            <tr {...headerGroup.getHeaderGroupProps()}>
              {headerGroup.headers.map(column => (
                <th {...column.getHeaderProps(column.getSortByToggleProps())}>
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
                </th>
              ))}
            </tr>
          ))}
        </thead>
        <tbody {...tableInstance.getTableBodyProps()}>
          {tableInstance.rows.map(row => {
            tableInstance.prepareRow(row);
            return (
              <tr {...row.getRowProps()}>
                {row.cells.map(cell => (
                  <td {...cell.getCellProps()}>{cell.render('Cell')}</td>
                ))}
              </tr>
            );
          })}
        </tbody>
      </Table>
    </div>
  );
}

export default TableContainerModal;