import React from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const DatePickerComponent = ({ startDate, setStartDate, endDate, setEndDate }) => {
  return (
    <div style={{ width: 300 }} className="mb-3 p-2">
    <div className="row row-cols-2">
      <div className="col">
        After
      </div>
      <div className="col">
        Before
      </div>
      <div className="col">
        <DatePicker
          className="form-control"
          showIcon
          selected={startDate}
          onChange={(date) => setStartDate(date)}
        />
      </div>
      <div className="col">
        <DatePicker
          className="form-control"
          showIcon
          selected={endDate}
          onChange={(date) => setEndDate(date)}
        />
        </div>
    </div>
  </div>
  );
};

export default DatePickerComponent;