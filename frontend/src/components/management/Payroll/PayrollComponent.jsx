import { useEffect, useState, useMemo } from "react";
import TableContainer from '../table/TableContainer';
import { useAuth } from "../security/AuthContext";
import { getFilteredPayrolls } from '../api/PayrollApiSerivce';
import PayrollDetailsModal from './PayrollDetailsModal';

function PayrollComponent() {

    const authContext = useAuth();

    const [workOrderIdsForMonth, setWorkOrderIdsForMonth] = useState([]);
    const [selectedMonth, setSelectedMonth] = useState([]);
    const [selectedPayrollData, setSelectedPayrollData] = useState([]);
    const [showWorkOrders, setShowWorkOrders] = useState(false);


    const handleShowWorkOrders = (payrollMonth, workOrderIds, payrollData) => {
        setSelectedMonth(payrollMonth);
        setWorkOrderIdsForMonth(workOrderIds);
        setSelectedPayrollData(payrollData);
        setShowWorkOrders(true);
    };

    const handleCloseWorkOrders = () => {
        setSelectedMonth([]);
        setWorkOrderIdsForMonth([]);
        setSelectedPayrollData([]);
        setShowWorkOrders(false);
    };

    const userId = authContext.userId;

    const [data, setData] = useState([]);

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
                console.log(sortedData);
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
                width: 100,
                Cell: ({ cell }) => cell.value.substring(0, 7),
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
                    <button
                        type="button"
                        className="btn btn-primary"
                        onClick={() => handleShowWorkOrders(cell.row.original.payrollMonth, cell.row.original.workOrderIds, cell.row.original)}>
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
            <h2>Your payroll history </h2>
        </div>
            <TableContainer
                columns={columns}
                data={data}
            />
            <PayrollDetailsModal
                show={showWorkOrders}
                handleClose={handleCloseWorkOrders}
                data={selectedPayrollData}
                workOrderIds={workOrderIdsForMonth}
            />
        </>
    );
}

export default PayrollComponent;