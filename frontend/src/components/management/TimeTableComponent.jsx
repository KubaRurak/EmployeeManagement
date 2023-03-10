import { useEffect, useState, useMemo} from "react"
import { getFilteredTimeTableApi } from "./api/TimeTableApiService"
import TableContainer from './table/TableContainer'
import { useAuth } from "./security/AuthContext"
import DatePickerComponent from "./DatePickerComponent";
import { auto } from "@popperjs/core";

function TimetableComponent() {


    const authContext = useAuth()

    // const userId = authContext.userId
  
    const [data,setData] = useState([])
    const [selectedRecord, setSelectedRecord] = useState(null);

    const today = new Date();

    const [startDate, setStartDate] = useState(today);
    const [endDate, setEndDate] = useState(today);

    function editTimeTableInput(){

    }

    useEffect ( () => refreshTimetable(), [startDate, endDate])
  
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
                  <button type="button" className="btn btn-primary" onClick={editTimeTableInput}><i className="bi bi-pencil"></i></button>
                ),
                disableSortBy: true,
                width: 30,
              },
              
        ],
        []
    )



    return (
      <>
          <div style={{ width: "100%", display: "flex", alignItems: "center", justifyContent: "center" , margin: "20px"}}>
            <div style={{ position: "absolute", left: "0" }}>
              <DatePickerComponent 
                startDate={startDate} 
                setStartDate={setStartDate}
                endDate={endDate}
                setEndDate={setEndDate}/>
            </div>
            <h2 style={{ marginLeft: "10px", flex: "1", textAlign: "center" }}>Time Table</h2>
          </div>
          <TableContainer 
          columns={columns} 
          data={data}
          defaultPageSize={10}
          pageSizeOptions={[10, 20, 30, 40, 50]}
          showPaginationBottom={true} />
      </>
    );
}

export default TimetableComponent