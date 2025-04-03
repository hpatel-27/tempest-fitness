import { useEffect, useState, useContext } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Form, Button, Card } from "react-bootstrap";
import exerciseService from "../services/exerciseService";
import workoutService from "../services/workoutService";
import { AuthContext } from "../contexts/AuthContext";
import Swal from "sweetalert2";
import PropTypes from "prop-types";

const WorkoutEditor = ({ editMode }) => {
  const { auth } = useContext(AuthContext);
  const [date, setDate] = useState("");
  const [userExercises, setUserExercises] = useState([]);
  const [exerciseOptions, setExerciseOptions] = useState([]);
  const { date: workoutDateParam } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    loadExercises();
    if (editMode && workoutDateParam) {
      loadWorkout(workoutDateParam);
    }
  });

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

  const handleAddExercise = () => {
    setUserExercises([
      ...userExercises,
      { exercise: { name: "" }, sets: "", reps: "", weight: "" },
    ]);
  };

  const handleRemoveExercise = (index) => {
    const updated = [...userExercises];
    updated.splice(index, 1);
    setUserExercises(updated);
  };

  const handleExerciseChange = (index, field, value) => {
    const updated = [...userExercises];

    if (field === "exerciseId") {
      // Find the selected exercise object from the list
      const selectedExercise = exerciseOptions.find(
        (ex) => ex.id === parseInt(value)
      );
      updated[index].exercise = { id: selectedExercise.id }; // Only include id!
    } else {
      updated[index][field] = value;
    }

    setUserExercises(updated);
  };

  const handleSubmit = async () => {
    const workout = {
      date,
      userExercises,
    };

    try {
      if (editMode) {
        await workoutService.updateWorkout(workoutDateParam, workout, auth);
        Swal.fire({
          title: "Updated!",
          text: "Workout updated successfully!",
          icon: "success",
          confirmButtonColor: "#6b51ab",
          background: "#242526",
          color: "#fff",
        });
      } else {
        const response = await workoutService.createWorkout(workout, auth);
        console.log("Response: ", response);
        Swal.fire({
          title: "Created!",
          text: "Workout created successfully!",
          icon: "success",
          confirmButtonColor: "#6b51ab",
          background: "#242526",
          color: "#fff",
        });
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
      <Card className="bg-dark text-light p-4">
        <h3>{editMode ? "Edit Workout" : "New Workout"}</h3>

        <Form className="mt-3">
          <Form.Group className="mb-3">
            <Form.Label>Date</Form.Label>
            <Form.Control
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
                className="flex-grow-1"
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
                type="number"
                placeholder="Sets"
                value={ue.sets}
                onChange={(e) =>
                  handleExerciseChange(index, "sets", e.target.value)
                }
              />
              <Form.Control
                type="number"
                placeholder="Reps"
                value={ue.reps}
                onChange={(e) =>
                  handleExerciseChange(index, "reps", e.target.value)
                }
              />
              <Form.Control
                type="number"
                placeholder="Weight (lbs)"
                value={ue.weight}
                onChange={(e) =>
                  handleExerciseChange(index, "weight", e.target.value)
                }
              />
              <Button
                variant="danger"
                onClick={() => handleRemoveExercise(index)}
              >
                <i className="bi bi-trash" />
              </Button>
            </div>
          ))}

          <Button variant="secondary" onClick={handleAddExercise}>
            <i className="bi bi-plus-circle" /> Add Exercise
          </Button>

          <div className="mt-4 d-flex justify-content-between">
            <Button variant="light" onClick={() => navigate("/workouts")}>
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
