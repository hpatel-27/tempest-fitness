import { useEffect, useState, useContext } from "react";
import exerciseService from "../services/exerciseService";
import { AuthContext } from "../contexts/AuthContext";

const Exercises = () => {
  const [exercises, setExercises] = useState([]);
  const { auth } = useContext(AuthContext);

  useEffect(() => {
    exerciseService.getExercises(auth).then((data) => {
      console.log("Fetched exercises: ", data);
      setExercises(data);
    });
  }, [auth]);

  const addExercise = () => {
    console.log("Add exercise");
  };

  return (
    <div className="container mt-4">
      <h2 className="text-center text-light mb-4">Exercises</h2>
      <div className="card shadow-sm bg-dark text-light border-0 exercise-card">
        <div className="card-body">
          <div className="d-flex float-end mb-3">
            <button
              className="btn btn-sm btn-outline-light btn-add-exercise"
              onClick={addExercise}
            >
              <i className="bi bi-plus-circle"></i>
            </button>
          </div>
          <ul className="list-group list-group-flush">
            {exercises.map((exercise, index) => (
              <li
                key={index}
                className="list-group-item d-flex justify-content-between align-items-center bg-transparent text-light border-secondary exercise-entry"
              >
                <span></span>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default Exercises;
