import { useEffect, useState, useContext } from "react";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Accordion from "react-bootstrap/Accordion";
import Swal from "sweetalert2";
import exerciseService from "../services/exerciseService";
import { AuthContext } from "../contexts/AuthContext";
import "../styles/index.css";
import "../styles/exercise.css";

const Exercises = () => {
  const [exercises, setExercises] = useState([]);
  const { auth } = useContext(AuthContext);

  // Modal state
  const [showModal, setShowModal] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [currentExercise, setCurrentExercise] = useState({
    name: "",
    type: "",
    muscle: "",
    equipment: "",
    difficulty: "",
  });

  // Fetch exercises on component mount
  useEffect(() => {
    loadExercises(auth);
  }, [auth]);

  const loadExercises = async (auth) => {
    try {
      const data = await exerciseService.getExercises(auth);
      setExercises(data);
    } catch (error) {
      console.error("Failed to fetch exercises:", error);
      Swal.fire({
        title: "Error",
        text: "Failed to load exercises. Please try again.",
        icon: "error",
        confirmButtonColor: "#6b51ab",
        background: "#242526",
        color: "#fff",
      });
    }
  };

  const handleAddExercise = () => {
    setIsEditing(false);
    setCurrentExercise({
      name: "",
      type: "",
      muscle: "",
      equipment: "",
      difficulty: "",
    });
    setShowModal(true);
  };

  const handleEditExercise = (exercise) => {
    setIsEditing(true);
    setCurrentExercise(exercise);
    setShowModal(true);
  };

  const handleDeleteExercise = async (exerciseId) => {
    const confirmed = await Swal.fire({
      title: "Delete Exercise",
      text: "Are you sure you want to delete this exercise?",
      icon: "warning",
      background: "#242526",
      color: "#fff",
      showCancelButton: true,
      confirmButtonColor: "#6b51ab",
      cancelButtonColor: "#3085d6",
      confirmButtonText: "Yes, delete it!",
    });

    if (confirmed.isConfirmed) {
      try {
        await exerciseService.deleteExercise(auth, exerciseId);
        setExercises(exercises.filter((e) => e.id !== exerciseId));
        Swal.fire({
          title: "Deleted!",
          text: "Exercise has been deleted.",
          icon: "success",
          confirmButtonColor: "#6b51ab",
          background: "#242526",
          color: "#fff",
        });
      } catch (error) {
        console.error("Failed to delete exercise:", error);
        Swal.fire({
          title: "Error",
          text: "Failed to delete the exercise. Please try again.",
          icon: "error",
          confirmButtonColor: "#6b51ab",
          background: "#242526",
          color: "#fff",
        });
      }
    }
  };

  const handleSaveExercise = async () => {
    try {
      if (isEditing) {
        await exerciseService.editExercise(
          auth,
          currentExercise.id,
          currentExercise
        );
        setExercises(
          exercises.map((e) =>
            e.id === currentExercise.id ? currentExercise : e
          )
        );
      } else {
        const response = await exerciseService.addExercise(
          auth,
          currentExercise
        );
        if (!response || response.status !== "success") {
          throw new Error("Failed to add the new exercise. Please try again.");
        }
        // Reload the exercises list after adding a new one
        await loadExercises(auth);
      }
      setShowModal(false);
      setCurrentExercise({
        name: "",
        type: "",
        muscle: "",
        equipment: "",
        difficulty: "",
      });
    } catch (error) {
      console.error("Failed to save exercise:", error);
      Swal.fire({
        title: "Error",
        text: "Failed to save the exercise. Please try again.",
        icon: "error",
        confirmButtonColor: "#6b51ab",
        background: "#242526",
        color: "#fff",
      });
    }
  };

  return (
    <div className="container mt-4">
      <div className="card shadow-sm bg-dark text-light border-0 exercise-card">
        <div className="card-body">
          <div className="d-flex justify-content-between mb-3">
            <h5 className="mb-0">Exercise Library</h5>
            <button
              className="btn btn-sm btn-outline-light btn-add-exercise"
              onClick={handleAddExercise}
            >
              <i className="bi bi-plus-circle"></i>
            </button>
          </div>

          <Accordion className="exercise-accordion border-0">
            {exercises.map((exercise, index) => (
              <Accordion.Item
                key={exercise.id}
                eventKey={index.toString()}
                className="bg-dark"
              >
                <div className="d-flex justify-content-between align-items-center p-3">
                  <Accordion.Header className="text-light flex-grow-1">
                    <div className="w-100 d-flex align-items-center">
                      <span className="exercise-name">{exercise.name}</span>
                    </div>
                  </Accordion.Header>
                  <div className="exercise-actions ms-3">
                    <button
                      className="btn btn-sm btn-outline-primary me-1"
                      onClick={() => handleEditExercise(exercise)}
                    >
                      <i className="bi bi-pencil"></i>
                    </button>
                    <button
                      className="btn btn-sm btn-outline-danger"
                      onClick={() => handleDeleteExercise(exercise.id)}
                    >
                      <i className="bi bi-trash"></i>
                    </button>
                  </div>
                </div>
                <Accordion.Body className="bg-dark text-light">
                  <div className="exercise-details">
                    <p>
                      <strong>Type:</strong> {exercise.type}
                    </p>
                    <p>
                      <strong>Muscle:</strong> {exercise.muscle}
                    </p>
                    <p>
                      <strong>Equipment:</strong> {exercise.equipment}
                    </p>
                    <p>
                      <strong>Difficulty:</strong> {exercise.difficulty}
                    </p>
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
        onHide={() => setShowModal(false)}
        centered
      >
        <Modal.Header closeButton>
          <Modal.Title>
            {isEditing ? "Edit Exercise" : "Add New Exercise"}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Name</Form.Label>
              <Form.Control
                type="text"
                value={currentExercise.name}
                onChange={(e) =>
                  setCurrentExercise({
                    ...currentExercise,
                    name: e.target.value,
                  })
                }
                placeholder="Enter exercise name"
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Type</Form.Label>
              <Form.Select
                value={currentExercise.type}
                onChange={(e) =>
                  setCurrentExercise({
                    ...currentExercise,
                    type: e.target.value,
                  })
                }
              >
                <option value="">Select type</option>
                <option value="Olympic">Olympic</option>
                <option value="Strength">Strength</option>
                <option value="Plyometric">Plyometric</option>
                <option value="Cardio">Cardio</option>
                <option value="Flexibility">Flexibility</option>
                <option value="Bodyweight">Bodyweight</option>
                <option value="Other">Other</option>
              </Form.Select>
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Muscle</Form.Label>
              <Form.Control
                type="text"
                value={currentExercise.muscle}
                onChange={(e) =>
                  setCurrentExercise({
                    ...currentExercise,
                    muscle: e.target.value,
                  })
                }
                placeholder="Enter target muscle"
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Equipment</Form.Label>
              <Form.Control
                type="text"
                value={currentExercise.equipment}
                onChange={(e) =>
                  setCurrentExercise({
                    ...currentExercise,
                    equipment: e.target.value,
                  })
                }
                placeholder="Enter required equipment"
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Difficulty</Form.Label>
              <Form.Select
                value={currentExercise.difficulty}
                onChange={(e) =>
                  setCurrentExercise({
                    ...currentExercise,
                    difficulty: e.target.value,
                  })
                }
              >
                <option value="">Select difficulty</option>
                <option value="Beginner">Beginner</option>
                <option value="Intermediate">Intermediate</option>
                <option value="Advanced">Advanced</option>
              </Form.Select>
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="danger" onClick={() => setShowModal(false)}>
            Cancel
          </Button>
          <Button
            className="custom-save-btn"
            onClick={handleSaveExercise}
            disabled={
              !currentExercise.name ||
              !currentExercise.type ||
              !currentExercise.muscle ||
              !currentExercise.equipment ||
              !currentExercise.difficulty
            }
          >
            {isEditing ? "Update" : "Save"}
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default Exercises;
