import React, { useEffect, useState, useCallback, useMemo } from 'react';
import PropTypes from 'prop-types';
import { Modal, Button, Row, Col } from 'react-bootstrap';
import { Formik, Form, Field } from 'formik';
import { editCheckTimesForDateApi } from "../api/TimeTableApiService";
import './TimetableModal.css'

function EditTimetableModal({ show, handleClose, selectedRecord, refreshTimetable, setMessage }) {


  const [errorMessage, setErrorMessage] = useState(null);

  const initialValues = useMemo(() => ({
    timetableId: selectedRecord?.timetableId || "",
    date: selectedRecord?.date || "",
    checkIn: selectedRecord?.checkIn || "",  // ensure a value even if null or undefined
    checkOut: selectedRecord?.checkOut || "",  // ensure a value even if null or undefined
    hoursWorked: selectedRecord?.hoursWorked || "",
    trainingTime: selectedRecord?.trainingTime || "",
    onLeave: selectedRecord?.onLeave || false,
    userId: selectedRecord?.userId || "",
    userEmail: selectedRecord?.userEmail || "",
    userFirstName: selectedRecord?.userFirstName || "",
    userLastName: selectedRecord?.userLastName || "",
    officeCode: selectedRecord?.officeCode || "",
    userName: `${selectedRecord?.userFirstName || ""} ${selectedRecord?.userLastName || ""}`
  }), [selectedRecord]);


  const onSubmit = useCallback((values) => {
    const { userId, date, checkIn, checkOut } = values;

    if (checkIn && checkOut && checkIn > checkOut) {
      setErrorMessage("Check-in time must be before check-out time.");
      return;  // Don't proceed with the API call.
    }

    editCheckTimesForDateApi(userId, date, checkIn, checkOut)
      .then(response => {
        handleClose();
        refreshTimetable();
        setMessage("Timetable record edited successfully!");
      })
      .catch(error => {
        console.error(error);
      });
  }, [handleClose, refreshTimetable, setMessage]);

  useEffect(() => {
    setErrorMessage(null);
  }, [refreshTimetable]);


  return (
    <div>

      <Modal show={show} onHide={handleClose} animation={false} size="xl">
        <Modal.Header closeButton>
          <Modal.Title>Edit Check in / Check out time</Modal.Title>

        </Modal.Header>
        <Modal.Body>
          <Formik initialValues={initialValues}
            onSubmit={onSubmit}
            enableReinitialize={true}
            validateOnChange={false}
            validateOnBlur={false}
          >
            {
              (props) => (
                <Form>
                  {
                    errorMessage && (
                      <div className="alert alert-danger" role="alert">
                        {errorMessage}
                      </div>
                    )
                  }
                  <Row>
                    <Col>
                      <h5>Timetable Details</h5>
                      <table className="table">
                        <tbody>
                          <tr>
                            <td><label>Date</label></td>
                            <td>{props.values.date}</td>
                          </tr>
                          <tr>
                            <td><label>Check In</label></td>
                            <td><Field type="time" className="form-control" name="checkIn" /></td>
                          </tr>
                          <tr>
                            <td><label>Check Out</label></td>
                            <td><Field type="time" className="form-control" name="checkOut" /></td>
                          </tr>
                          <tr>
                            <td><label>Hours Worked</label></td>
                            <td>{props.values.hoursWorked}</td>
                          </tr>
                        </tbody>
                      </table>
                    </Col>
                    <Col>
                      <h5>User details</h5>
                      <table className="table">
                        <tbody>
                          <tr>
                            <td><label>Email</label></td>
                            <td>{props.values.userEmail}</td>
                          </tr>
                          <tr>
                            <td><label>Name</label></td>
                            <td>{props.values.userName}</td>
                          </tr>
                          <tr>
                            <td><label>Office</label></td>
                            <td>{props.values.officeCode}</td>
                          </tr>
                        </tbody>
                      </table>
                    </Col>
                  </Row>
                  <div className="form-group d-flex">
                    <Button variant="success" type="submit">Save</Button>
                    <Button variant="secondary" onClick={handleClose} style={{ marginLeft: "auto" }}>
                      Close<i className="bi-x"></i>
                    </Button>
                  </div>
                </Form>
              )
            }
          </Formik>
        </Modal.Body>
      </Modal>
    </div>
  );
}

EditTimetableModal.propTypes = {
  show: PropTypes.bool.isRequired,
  handleClose: PropTypes.func.isRequired,
  selectedRecord: PropTypes.object.isRequired,
  refreshTimetable: PropTypes.func.isRequired,
};

export default EditTimetableModal;