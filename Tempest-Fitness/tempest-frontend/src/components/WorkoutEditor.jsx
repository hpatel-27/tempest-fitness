import { useEffect, useState, useContext } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Form, Button, Card } from "react-bootstrap";
import exerciseService from "../services/exerciseService";
import workoutService from "../services/workoutService";
import { AuthContext } from "../contexts/AuthContext";
import Swal from "sweetalert2";
import PropTypes from "prop-types";
import "../styles/workouts.css";

const WorkoutEditor = ({ editMode }) => {
  const { auth } = useContext(AuthContext);
  const [date, setDate] = useState("");
  const [userExercises, setUserExercises] = useState([]);
  const [exerciseOptions, setExerciseOptions] = useState([]);
  const { date: workoutDateParam } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    const loadExercises = async () => {
      try {
        const data = await exerciseService.getExercises(auth);
        setExerciseOptions(data);
      } catch (err) {
        console.error("Failed to fetch exercises:", err);
      }
    };

    const loadWorkout = async (date) => {
      try {
        const workout = await workoutService.getWorkoutByDate(date, auth);
        setDate(workout.date);
        setUserExercises(workout.userExercises);
      } catch (err) {
        console.error("Failed to load workout:", err);
      }
    };

    loadExercises();
    if (editMode && workoutDateParam) {
      loadWorkout(workoutDateParam);
    }
  }, [editMode, workoutDateParam, auth]);

  const handleAddExercise = () => {
    setUserExercises((prev) => [
      ...prev,
      {
        exercise: { id: null },
        sets: "",
        reps: "",
        weight: "",
      },
    ]);
  };

  const handleRemoveExercise = (index) => {
    const updated = [...userExercises];
    updated.splice(index, 1);
    setUserExercises(updated);
  };

  const handleExerciseChange = (index, field, value) => {
    setUserExercises((prev) => {
      const updated = [...prev];

      if (field === "exerciseId") {
        const selectedExercise = exerciseOptions.find(
          (ex) => ex.id === parseInt(value)
        );
        updated[index] = {
          ...updated[index],
          exercise: { id: selectedExercise.id },
        };
      } else {
        updated[index] = {
          ...updated[index],
          [field]: value,
        };
      }

      return updated;
    });
  };

  const handleSubmit = async () => {
    const workout = {
      date,
      userExercises,
    };

    try {
      if (editMode) {
        const response = await workoutService.updateWorkout(
          workoutDateParam,
          workout,
          auth
        );
        // Something went wrong
        if (response.status != "success") {
          Swal.fire({
            title: "Error",
            text: "There was a problem updating your workout.",
            icon: "error",
            confirmButtonColor: "#6b51ab",
            background: "#242526",
            color: "#fff",
          });
        } else {
          // Success
          Swal.fire({
            title: "Updated!",
            text: "Workout updated successfully!",
            icon: "success",
            confirmButtonColor: "#6b51ab",
            background: "#242526",
            color: "#fff",
          });
        }
      } else {
        const response = await workoutService.createWorkout(workout, auth);
        // Something went wrong
        if (response.status != "success") {
          Swal.fire({
            title: "Error",
            text: "There was a problem creating your workout.",
            icon: "error",
            confirmButtonColor: "#6b51ab",
            background: "#242526",
            color: "#fff",
          });
        } else {
          // Success
          Swal.fire({
            title: "Created!",
            text: "Workout created successfully!",
            icon: "success",
            confirmButtonColor: "#6b51ab",
            background: "#242526",
            color: "#fff",
          });
        }
      }
      navigate("/workouts");
    } catch (err) {
      console.error("Error saving workout:", err);
      Swal.fire({
        title: "Error",
        text: "There was a problem saving your workout.",
        icon: "error",
        confirmButtonColor: "#6b51ab",
        background: "#242526",
        color: "#fff",
      });
    }
  };

  return (
    <div className="container mt-4">
      <Card className="bg-dark text-light p-4 workout-add-edit-card">
        <h3>{editMode ? "Edit Workout" : "New Workout"}</h3>

        <Form className="mt-3 bg-dark text-light">
          <Form.Group className="mb-3">
            <Form.Label>Date</Form.Label>
            <Form.Control
              className="border-0 text-light"
              type="date"
              value={date}
              onChange={(e) => setDate(e.target.value)}
            />
          </Form.Group>

          {userExercises.map((ue, index) => (
            <div
              key={index}
              className="d-flex align-items-end mb-3 gap-2 flex-wrap"
            >
              <Form.Select
                className="flex-grow-1 border-0 text-light"
                value={ue.exercise?.id || ""}
                onChange={(e) =>
                  handleExerciseChange(index, "exerciseId", e.target.value)
                }
              >
                <option value="">Select Exercise</option>
                {exerciseOptions.map((ex) => (
                  <option key={ex.id} value={ex.id}>
                    {ex.name}
                  </option>
                ))}
              </Form.Select>
              <Form.Control
                className="border-0 text-light"
                type="number"
                placeholder="Sets"
                value={ue.sets}
                onChange={(e) =>
                  handleExerciseChange(index, "sets", e.target.value)
                }
              />
              <Form.Control
                className="border-0 text-light"
                type="number"
                placeholder="Reps"
                value={ue.reps}
                onChange={(e) =>
                  handleExerciseChange(index, "reps", e.target.value)
                }
              />
              <Form.Control
                className="border-0 text-light"
                type="number"
                placeholder="Weight (lbs)"
                value={ue.weight}
                onChange={(e) =>
                  handleExerciseChange(index, "weight", e.target.value)
                }
              />

              <Button
                className="remove-workout-exercise"
                variant="danger"
                onClick={() => handleRemoveExercise(index)}
              >
                <i className="bi bi-trash" />
              </Button>
            </div>
          ))}
          <div className="d-flex">
            <Button
              variant="secondary"
              className="workout-add-exercise"
              onClick={handleAddExercise}
            >
              <i className="bi bi-plus-circle" /> Add Exercise
            </Button>
          </div>

          <div className="mt-4 d-flex justify-content-between">
            <Button
              variant="danger"
              className="cancel-add-workout"
              onClick={() => navigate("/workouts")}
            >
              Cancel
            </Button>
            <Button
              className="custom-save-btn"
              onClick={handleSubmit}
              disabled={!date || userExercises.length === 0}
            >
              {editMode ? "Update Workout" : "Create Workout"}
            </Button>
          </div>
        </Form>
      </Card>
    </div>
  );
};

export default WorkoutEditor;

WorkoutEditor.propTypes = {
  editMode: PropTypes.bool,
};
