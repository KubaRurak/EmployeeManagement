import Dashboard from "./dashboard/views/dashboard/Dashboard"

function DashboardComponent() {
    return (
        <div className="DashboardComponent">
        <div className="card" style={{marginLeft:"50px", marginRight:"50px", marginTop:"20px", marginBottom:"20px"}}>
          <div className="card-body" style={{margin:"20px", background:"white"}}>
            <Dashboard></Dashboard>
        </div>
        </div>
        </div>
    )
}

export default DashboardComponent