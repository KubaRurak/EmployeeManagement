import React from "react";
import { useTable } from 'react-table'


function ListActiveWorkOrdersComponent2() {



    const data = [
        {
          name: 'Sara Turner',
          email: 'sara.turner@example.com',
          phone: '(603)-641-0238'
        },
        {
          name: 'Christian Kelley',
          email: 'christian.kelley@example.com',
          phone: '(153)-324-6597'
        },
        {
          name: 'Annie Dean',
          email: 'annie.dean@example.com',
          phone: '(373)-005-2708'
        },
        {
          name: 'Cameron Hunt',
          email: 'cameron.hunt@example.com',
          phone: '(912)-351-7665'
        },
        {
          name: 'Brandon Cook',
          email: 'brandon.cook@example.com',
          phone: '(554)-103-5975'
        },
        {
          name: 'Ashley Jones',
          email: 'ashley.jones@example.com',
          phone: '(604)-896-2642'
        },
        {
          name: 'Allan Fields',
          email: 'allan.fields@example.com',
          phone: '(197)-032-6069'
        },
        {
          name: 'Justin Barnes',
          email: 'justin.barnes@example.com',
          phone: '(002)-902-1774'
        },
        {
          name: 'Willie Jordan',
          email: 'willie.jordan@example.com',
          phone: '(535)-969-6902'
        },
        {
          name: 'Rhonda Cooper',
          email: 'rhonda.cooper@example.com',
          phone: '(746)-018-4462'
        },                           
      ]
    
      const columns = [
        {
          Header: 'Name',
          accessor: 'name'
        }, {
          Header: 'Email',
          accessor: 'email'
        }, {
          Header: 'Phone',
          accessor: 'phone'
        }
      ]
    
      const {
          rows,
          prepareRow,
          getTableProps,
          headerGroups,
          getTableBodyProps,
      } = useTable({
          columns,
          data,
      })
    
      return (
    <div className="card">
      {/* <div className="card-body">
            <table className="table table-light" {...getTableProps()}>
                <thead>
                    {headerGroups.map(headerGroup => (
                        <tr {...headerGroup.getHeaderGroupProps()}>
                            {headerGroup.headers.map(data => (
                                <th {...data.getHeaderProps()}>{data.render('Header')}</th>
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
                                    return <td {...cell.getCellProps()}> {cell.render('Cell')} </td>
                                })}
                            </tr>
                        )
                    })}
                </tbody>
            </table>
        </div> */}
      </div>
      )
    }

export default ListActiveWorkOrdersComponent2