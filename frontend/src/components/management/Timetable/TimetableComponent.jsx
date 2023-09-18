import { useEffect, useMemo, useState, useCallback } from "react";
import DatePickerComponent from "../DatePickerComponent";
import { getFilteredTimeTableApi } from "../api/TimeTableApiService";
import TableContainer from '../table/TableContainer';
import EditTimetableModal from './EditTimetableModal';
import { useAuth } from '../security/AuthContext'


function TimetableComponent() {


  const authContext = useAuth()
  const userRole = authContext.role;

  const [showEditModal, setShowEditModal] = useState(false);
  const handleCloseEditModal = () => setShowEditModal(false);
  const handleShowEditModal = useCallback(() => setShowEditModal(true), []);

  const [data, setData] = useState([])
  const [selectedRecord, setSelectedRecord] = useState(null);
  const [message, setMessage] = useState("");


  const today = new Date();

  const [startDate, setStartDate] = useState(today);
  const [endDate, setEndDate] = useState(today);

  const editTimeTableDetails = useCallback((record) => {
    return () => {
      setSelectedRecord(record);
      handleShowEditModal();
    }
  }, [handleShowEditModal]);

  useEffect(() => refreshTimetable(), [startDate, endDate])

  function refreshTimetable() {

    const after = startDate ? startDate.toISOString().substring(0, 10) : today.toISOString().substring(0, 10);
    const before = endDate ? endDate.toISOString().substring(0, 10) : today.toISOString().substring(0, 10);

    getFilteredTimeTableApi(undefined, after, before)
      .then(response => {
        setData(response.data);
      })
      .catch(error => console.log(error));
  }


  const columns = useMemo(
    (props) => [
      {
        Header: "Office Code",
        accessor: "officeCode",
        width: 50,
      },
      {
        Header: "Employee",
        accessor: cell => `${cell.userFirstName} ${cell.userLastName}`,
        width: 130,
      },
      {
        Header: "Email",
        accessor: "userEmail",
        width: 150
      },
      {
        Header: "Date",
        accessor: "date",
        width: 130
      },
      {
        Header: "Check In",
        accessor: "checkIn",
        width: 100
      },
      {
        Header: "Check Out",
        accessor: "checkOut",
        width: 100
      },
      {
        Header: "Time Worked [h]",
        accessor: "hoursWorked",
        width: 100,
      },
      {
        Header: "  ",
        Cell: ({ cell }) => (
          <button
            type="button"
            className="btn btn-primary"
            onClick={editTimeTableDetails(cell.row.original)}
            disabled={userRole === "Engineer"}
          >
            <i className="bi bi-pencil"></i>
          </button>
        ),
        disableSortBy: true,
        width: 30,
      },

    ],
    []
  )



  return (
    <>
      <div style={{ width: "100%", display: "flex", alignItems: "center", justifyContent: "center", margin: "20px" }}>
        <div style={{ position: "absolute", left: "0", marginLeft: "15px" }}>
          <DatePickerComponent
            startDate={startDate}
            setStartDate={setStartDate}
            endDate={endDate}
            setEndDate={setEndDate} />
        </div>
        <h2 style={{ marginLeft: "10px", flex: "1", textAlign: "center" }}>Time Table</h2>
      </div>
      {message && <div className="alert alert-success">{message}</div>}
      <TableContainer
        columns={columns}
        data={data}
        defaultPageSize={10}
        pageSizeOptions={[10, 20, 30, 40, 50]}
        showPaginationBottom={true} />
      <EditTimetableModal
        show={showEditModal}
        handleClose={handleCloseEditModal}
        selectedRecord={selectedRecord}
        refreshTimetable={refreshTimetable}
        setMessage={setMessage}
      />
    </>
  );
}

export default TimetableComponent