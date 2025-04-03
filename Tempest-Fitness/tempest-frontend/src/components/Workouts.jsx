import { useEffect, useState, useContext } from "react";
import workoutService from "../services/workoutService";
import { AuthContext } from "../contexts/AuthContext";
import Swal from "sweetalert2";
import Modal from "react-bootstrap/Modal";
import { Form } from "react-bootstrap";
import { Accordion } from "react-bootstrap";
import Button from "react-bootstrap/Button";
import "../styles/index.css";
import "../styles/workouts.css";

const Workouts = () => {
  const [workouts, setWorkouts] = useState([]);
  const [sortOrder, setSortOrder] = useState("descent");
  const { auth } = useContext(AuthContext);
  const [showModal, setShowModal] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [newWorkout, setNewWorkout] = useState("");
  const [newDate, setNewDate] = useState("");

  useEffect(() => {
    loadWorkouts(auth);
  }, [auth]);

  const loadWorkouts = async (auth) => {
    try {
      const data = await workoutService.getWorkouts(auth);
      console.log("Data: ", data);
      setWorkouts(data);
    } catch (error) {
      console.error(
        "Failed to fetch the Workouts for the authenticated user. ",
        error
      );
      Swal.fire({
        title: "Error",
        text: "Failed to fetch workouts.",
        icon: "error",
        confirmButtonColor: "#6b51ab",
        background: "#242526",
        color: "#fff",
      });
    }
  };

  const handleSave = () => {
    console.log("Clicked add workout");
  };

  const formatDate = (dateString) => {
    if (!dateString) return "Invalid Date";

    const date = new Date(dateString + "T00:00:00");
    if (isNaN(date.getTime())) {
      console.error("Invalid date format: ", dateString);
      return "Invalid Date";
    }

    return new Intl.DateTimeFormat("en-US", {
      month: "long",
      day: "numeric",
      year: "numeric",
    }).format(date);
  };

  return (
    <div className="container mt-4">
      <div className="card shadow bg-dark text-light border-0 workout-card">
        <div className="card-body">
          <div className="d-flex justify-content-between align-items-center mb-3">
            <h2 className="mb-0">Workout Log</h2>
            <button
              className="btn btn-sm btn-outline-light btn-add-workout"
              onClick={handleSave}
            >
              <i className="bi bi-plus-circle"></i>
            </button>
          </div>

          <Accordion className="workout-accordion border-0">
            {workouts.map((workout, date) => (
              <Accordion.Item
                key={date}
                eventKey={formatDate(date)}
                className="bg-dark"
              >
                <div className="d-flex justify-content-between align-items-center p-3">
                  <Accordion.Header>
                    <div className="w-100 d-flex align-items-center">
                      <span className="workout-name">{workout.date}</span>
                    </div>
                  </Accordion.Header>
                </div>
                <Accordion.Body className="bg-dark text-light">
                  <div className="workout-details">
                    <ul className="list-group list-group-flush mb-0">
                      {workout.userExercises.map((userExercise, index) => (
                        <li
                          key={index}
                          className="list-group-item d-flex justify-content-between align-items-center bg-transparent text-light border-secondary workout-entry"
                        >
                          <span className="fw-bold">
                            Exercise: {userExercise.exercise.name}
                          </span>
                          <span className="fw-bold">
                            Sets: {userExercise.sets}
                          </span>
                          <span className="fw-bold">
                            Reps: {userExercise.reps}
                          </span>
                          <span className="fw-bold">
                            Weight: {userExercise.weight} lbs
                          </span>
                          <button className="btn btn-sm btn-outline-primary me-1">
                            <i className="bi bi-pencil"></i>
                          </button>
                          <button className="btn btn-sm btn-outline-primary me-1">
                            <i className="bi bi-trash"></i>
                          </button>
                        </li>
                      ))}
                    </ul>
                  </div>
                </Accordion.Body>
              </Accordion.Item>
            ))}
          </Accordion>
        </div>
      </div>

      <Modal
        className="custom-modal"
        show={showModal}
        onHide={() => {
          setShowModal(false);
          setIsEditing(false);
          // setErrors({});
        }}
        centered
      >
        <Modal.Header closeButton>
          <Modal.Title>
            {isEditing ? "Edit Workout" : "Add New Workout"}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button
            className="custom-save-btn"
            onClick={handleSave}
            disabled={!newWorkout || !newDate}
          >
            {isEditing ? "Update" : "Save"}
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default Workouts;
