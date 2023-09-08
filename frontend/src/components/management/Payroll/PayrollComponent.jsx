import { useEffect, useState, useMemo } from "react";
import TableContainer from '../table/TableContainer';
import { useAuth } from "../security/AuthContext";
import { getFilteredPayrolls } from '../api/PayrollApiSerivce';

// import WorkOrderDetailsModal from './WorkOrderDetailsModal';

function PayrollComponent() {

    const authContext = useAuth();

    const [showWorkOrders, setShowWorkOrders] = useState(false);
    const handleCloseWorkOrders = () => setShowWorkOrders(false);
    const handleShowWorkOrders = month => {
        setSelectedMonth(month);
        setShowWorkOrders(true);
    };

    const userId = authContext.userId;

    const [data, setData] = useState([]);
    const [selectedMonth, setSelectedMonth] = useState(null);

    useEffect(() => {
        getFilteredPayrolls(userId)
            .then(response => {
                // Filter payrolls up to current month
                const currentDate = new Date();
                const filteredData = response.data.filter(payroll => {
                    const payrollDate = new Date(payroll.payrollMonth);
                    return payrollDate <= currentDate;
                });
                const sortedData = filteredData.sort((a, b) => new Date(b.payrollMonth) - new Date(a.payrollMonth));
                setData(sortedData);
            })
            .catch(error => console.error("Error fetching payroll data:", error));
    }, [userId]);

    const columns = useMemo(
        () => [
            {
                Header: "Name",
                accessor: data => `${data.userFirstName} ${data.userLastName}`,
                id: "fullName",
                width: 150
            },
            {
                Header: "Email",
                accessor: "userEmail",
                width: 200
            },
            {
                Header: "Role",
                accessor: "userRole",
                width: 100
            },
            {
                Header: "Month",
                accessor: "payrollMonth",
                width: 100
            },
            {
                Header: "Time Worked [h]",
                accessor: "timeWorked",
                width: 100
            },
            {
                Header: "Money Generated",
                accessor: "moneyGenerated",
                width: 150
            },
            {
                Header: "Details",
                accessor: "workOrderIds",
                Cell: ({ cell }) => (
                    <button type="button" className="btn btn-primary" onClick={() => handleShowWorkOrders(cell.row.original.payrollMonth)}>
                        <i className="bi bi-search"></i>
                    </button>
                ),
                disableSortBy: true,
                width: 150
            }
        ],
        []
    );

    return (
        <>      <div>
            <h2>Payroll history </h2>
        </div>
            <TableContainer
                columns={columns}
                data={data}
            />
            {/* <WorkOrderDetailsModal
                show={showWorkOrders}
                handleClose={handleCloseWorkOrders}
                selectedMonth={selectedMonth}
            // Add more props if needed for the modal to fetch work order details
            /> */}
        </>
    );
}

export default PayrollComponent;