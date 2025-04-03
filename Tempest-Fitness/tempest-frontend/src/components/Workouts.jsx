import { useEffect, useState, useContext } from "react";
import workoutService from "../services/workoutService";
import { AuthContext } from "../contexts/AuthContext";
import Swal from "sweetalert2";
import { Accordion } from "react-bootstrap";
import "../styles/index.css";
import "../styles/workouts.css";
import { useNavigate } from "react-router-dom";

const Workouts = () => {
  const [workouts, setWorkouts] = useState([]);
  const [sortOrder, setSortOrder] = useState("descend");
  const { auth } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    const loadWorkouts = async (auth) => {
      try {
        workoutService.getWorkouts(auth).then((data) => {
          sortWorkouts(data, sortOrder);
        });
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

    loadWorkouts(auth);
  }, [auth, sortOrder]);

  // Sort the workouts to be in newest first order
  const sortWorkouts = (data, order) => {
    const sorted = [...data].sort((a, b) => {
      return order === "descend"
        ? new Date(b.date) - new Date(a.date)
        : new Date(a.date) - new Date(b.date);
    });
    setWorkouts(sorted);
  };

  // Toggle the sorting order
  const toggleSortOrder = () => {
    const newOrder = sortOrder === "descend" ? "ascend" : "descend";
    setSortOrder(newOrder);
    sortWorkouts(workouts, newOrder);
  };

  // Send to a different view to add a new workout
  const handleAddWorkout = () => {
    navigate("/workouts/new");
  };

  // Send to a different view to update an existing workout
  const handleEditWorkout = (workout) => {
    navigate(`/workouts/edit/${workout.date}`);
  };

  // Delete a workout and then filter it out of the list to remove from view
  const handleDeleteWorkout = async (date) => {
    try {
      const response = await workoutService.deleteWorkout(date, auth);
      setWorkouts(workouts.filter((w) => w.date !== date));

      if (response && response.status == "success") {
        Swal.fire({
          title: "Deleted!",
          text: "Workout deleted successfully!",
          icon: "success",
          confirmButtonColor: "#6b51ab",
          background: "#242526",
          color: "#fff",
        });
      } else {
        Swal.fire({
          title: "Error",
          text: "Workout could not be deleted.",
          icon: "error",
          confirmButtonColor: "#6b51ab",
          background: "#242526",
          color: "#fff",
        });
      }
    } catch (err) {
      console.error("Error: ", err);
      Swal.fire({
        title: "Error",
        text: "Workout could not be deleted here.",
        icon: "error",
        confirmButtonColor: "#6b51ab",
        background: "#242526",
        color: "#fff",
      });
    }
  };

  return (
    <div className="container mt-4">
      <div className="card shadow bg-dark text-light border-0 workout-card">
        <div className="card-body">
          <div className="d-flex justify-content-between align-items-center mb-3">
            <h2 className="mb-0">Workout Log</h2>
            <div className="d-flex align-items-center gap-1">
              <button
                className="btn btn-sm btn-outline-light btn-sort"
                onClick={toggleSortOrder}
              >
                <i
                  className={
                    sortOrder === "descend"
                      ? "bi bi-sort-down"
                      : "bi bi-sort-up-alt"
                  }
                ></i>
              </button>
              <button
                className="btn btn-sm btn-outline-light btn-add-workout"
                onClick={handleAddWorkout}
              >
                <i className="bi bi-plus-circle"></i>
              </button>
            </div>
          </div>

          <Accordion className="workout-accordion border-0">
            {workouts.map((workout, date) => (
              <Accordion.Item
                key={date}
                eventKey={date}
                className="bg-dark text-light"
              >
                <div className="d-flex justify-content-between align-items-center p-3">
                  <Accordion.Header className="text-light flex-grow-1">
                    <div className="w-100 d-flex align-items-center">
                      <span className="workout-name">{workout.date} </span>
                    </div>
                  </Accordion.Header>
                  <div className="workout-actions ms-3">
                    <button
                      className="btn btn-sm btn-outline-primary me-1"
                      onClick={() => handleEditWorkout(workout)}
                    >
                      <i className="bi bi-pencil"></i>
                    </button>
                    <button
                      className="btn btn-sm btn-outline-danger me-1"
                      onClick={() => handleDeleteWorkout(workout.date)}
                    >
                      <i className="bi bi-trash"></i>
                    </button>
                  </div>
                </div>
                <Accordion.Body className="bg-dark text-light">
                  <div className="workout-details table-responsive">
                    <ul className="list-group list-group-flush mb-0">
                      {workout.userExercises.map((userExercise, index) => (
                        <li
                          key={index}
                          className="list-group-item text-light border-secondary workout-entry"
                        >
                          <div className="d-flex flex-grow-1 justify-content-between me-3">
                            <span className="entry-col exercise-name fw-bold">
                              {userExercise.exercise.name}
                            </span>
                            <span className="entry-col fw-bold">
                              Sets: {userExercise.sets}
                            </span>
                            <span className="entry-col fw-bold">
                              Reps: {userExercise.reps}
                            </span>
                            <span className="entry-col fw-bold">
                              Weight: {userExercise.weight} lbs
                            </span>
                          </div>
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
    </div>
  );
};

export default Workouts;
