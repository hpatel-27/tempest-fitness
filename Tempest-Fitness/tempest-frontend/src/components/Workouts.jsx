import { useEffect, useState, useContext } from "react";
import workoutService from "../services/workoutService";
import { AuthContext } from "../contexts/AuthContext";
import Swal from "sweetalert2";
import { Accordion } from "react-bootstrap";
import "../styles/index.css";
import "../styles/workouts.css";

const Workouts = () => {
  const [workouts, setWorkouts] = useState([]);
  const { auth } = useContext(AuthContext);

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

  const handleAddWorkout = () => {
    console.log("Clicked add workout");
  };

  return (
    <div className="container mt-4">
      <div className="card shadow bg-dark text-light border-0 workout-card">
        <div className="card-body">
          <div className="d-flex justify-content-between align-items-center mb-3">
            <h2 className="mb-0">Workout Log</h2>
            <button
              className="btn btn-sm btn-outline-light btn-add-workout"
              onClick={handleAddWorkout}
            >
              <i className="bi bi-plus-circle"></i>
            </button>
          </div>

          <Accordion className="workout-accordion border-0">
            {workouts.map((workout, index) => (
              <Accordion.Item
                key={workout.id}
                eventKey={index.toString()}
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
                  <div className="workout-details"></div>
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
