import { useEffect, useState, useContext } from "react";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Alert from "react-bootstrap/Alert";
import Swal from "sweetalert2";
import weightService from "../services/weightService";
import { AuthContext } from "../contexts/AuthContext";
import "../styles/index.css";
import "../styles/weight.css";

const Weights = () => {
  const [weights, setWeights] = useState([]);
  const [sortOrder, setSortOrder] = useState("descend");
  const { auth } = useContext(AuthContext);

  const [showModal, setShowModal] = useState(false);
  const [newWeight, setNewWeight] = useState("");
  const [newDate, setNewDate] = useState("");
  const [errors, setErrors] = useState({ weight: "", date: "" });
  const [isEditing, setIsEditing] = useState(false);

  useEffect(() => {
    weightService.getWeights(auth).then((data) => {
      // Sort dates
      sortWeights(data, "descend"); // Initial sort, user can change it
    });
  }, [auth]);

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

  // Sort weights based on sortOrder
  const sortWeights = (data, order) => {
    const sorted = [...data].sort((a, b) => {
      return order === "descend"
        ? new Date(b.date) - new Date(a.date)
        : new Date(a.date) - new Date(b.date);
    });
    setWeights(sorted);
  };

  // Toggle the sorting order
  const toggleSortOrder = () => {
    const newOrder = sortOrder === "descend" ? "ascend" : "descend";
    setSortOrder(newOrder);
    sortWeights(weights, newOrder);
  };

  const validateInputs = () => {
    let errors = { weight: "", date: "" };
    let isValid = true;

    if (!newWeight || isNaN(newWeight) || Number(newWeight) <= 0) {
      errors.weight = "Please enter a valid weight (greater than 0).";
      isValid = false;
    }
    if (!newDate) {
      errors.date = "Please select a valid date.";
      isValid = false;
    }

    setErrors(errors);
    return isValid;
  };

  const handleEdit = (weight) => {
    setIsEditing(true);
    setNewWeight(weight.weight);
    setNewDate(weight.date);
    setShowModal(true);
  };

  const handleDelete = async (weightDate) => {
    const confirmed = await Swal.fire({
      title: "Delete Weight Entry",
      text: "Are you sure you want to delete this weight entry?",
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
        await weightService.deleteWeight(auth, weightDate);
        setWeights(weights.filter((w) => w.date !== weightDate));
        Swal.fire({
          title: "Deleted!",
          text: "Your weight entry has been deleted.",
          icon: "success",
          confirmButtonColor: "#6b51ab",
          background: "#242526",
          color: "#fff",
        });
      } catch (error) {
        console.error("Failed to delete weight: ", error);
        Swal.fire({
          title: "Error",
          text: "Failed to delete the weight. Please try again.",
          icon: "error",
          confirmButtonColor: "#6b51ab",
          background: "#242526",
          color: "#fff",
        });
      }
    }
  };

  const handleSave = async () => {
    if (!validateInputs()) return;

    try {
      if (isEditing) {
        // Update existing weight

        // Note the date may not be changed!
        const updatedWeight = await weightService.updateWeight(auth, newDate, {
          weight: newWeight,
          date: newDate,
        });

        if (updatedWeight.status !== "success") {
          alert("Failed to update the weight. Please try again.");
          return;
        }

        // Update the weights array with the edited weight
        const updatedWeights = weights.map((w) =>
          w.date === newDate ? { ...w, weight: newWeight, date: newDate } : w
        );
        sortWeights(updatedWeights, sortOrder);
      } else {
        // Add new weight
        const savedWeight = await weightService.addWeight(auth, {
          weight: newWeight,
          date: newDate,
        });

        if (savedWeight.status !== "success") {
          alert("Failed to add the new weight. Please try again.");
          return;
        }

        const updatedWeights = [
          ...weights,
          { weight: newWeight, date: newDate },
        ];
        sortWeights(updatedWeights, sortOrder);
      }

      // Reset form and close modal
      setShowModal(false);
      setNewWeight("");
      setNewDate("");
      setErrors({ weight: "", date: "" });
      setIsEditing(false);
    } catch (error) {
      console.error("Failed to save weight: ", error);
      alert("Failed to save the weight. Please try again.");
    }
  };

  return (
    <div className="container mt-4">
      <h2 className="text-center text-light mb-4">Welcome, {auth.username}</h2>
      <div className="card shadow-sm bg-dark text-light border-0 weight-card">
        <div className="card-body">
          <div className="d-flex justify-content-between mb-3">
            <h5 className="mb-0">Weight History</h5>
            <div className="d-flex align-items-center btns-div gap-2">
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
                className="btn btn-sm btn-outline-light btn-add-weight"
                onClick={() => {
                  setIsEditing(false);
                  setNewWeight("");
                  setNewDate("");
                  setShowModal(true);
                }}
              >
                <i className="bi bi-plus-circle"></i>
              </button>
            </div>
          </div>
          <ul className="list-group list-group-flush">
            {weights.map((weight, index) => (
              <li
                key={index}
                className="list-group-item d-flex justify-content-between align-items-center bg-transparent text-light border-secondary weight-entry"
              >
                <span className="fw-bold">{formatDate(weight.date)}</span>
                <div className="d-flex align-items-center">
                  <div className="weight-actions me-2">
                    <button
                      className="btn btn-sm btn-outline-primary me-1"
                      onClick={() => handleEdit(weight)}
                    >
                      <i className="bi bi-pencil"></i>
                    </button>
                    <button
                      className="btn btn-sm btn-outline-danger"
                      onClick={() => handleDelete(weight.date)}
                    >
                      <i className="bi bi-trash"></i>
                    </button>
                  </div>
                  <span className="badge rounded-pill weight-badge">
                    {weight.weight} lbs
                  </span>
                </div>
              </li>
            ))}
          </ul>
        </div>
      </div>

      <Modal
        className="custom-modal"
        show={showModal}
        onHide={() => {
          setShowModal(false);
          setIsEditing(false);
          setErrors({ weight: "", date: "" });
        }}
        centered
      >
        <Modal.Header closeButton>
          <Modal.Title>
            {isEditing ? "Edit Weight" : "Add New Weight"}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Weight (lbs)</Form.Label>
              <Form.Control
                type="number"
                value={newWeight}
                onChange={(e) => setNewWeight(e.target.value)}
                placeholder="Enter your weight"
                isInvalid={!!errors.weight}
              />
              {errors.weight && (
                <Alert variant="danger" className="mt-2">
                  {errors.weight}
                </Alert>
              )}
            </Form.Group>
            {!isEditing && (
              <Form.Group className="mb-3">
                <Form.Label>Date</Form.Label>
                <Form.Control
                  type="date"
                  value={newDate}
                  onChange={(e) => setNewDate(e.target.value)}
                  isInvalid={!!errors.date}
                />
                {errors.date && (
                  <Alert variant="danger" className="mt-2">
                    {errors.date}
                  </Alert>
                )}
              </Form.Group>
            )}
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button
            variant="danger"
            onClick={() => {
              setShowModal(false);
              setIsEditing(false);
              setErrors({ weight: "", date: "" });
            }}
          >
            Cancel
          </Button>
          <Button
            className="custom-save-btn"
            onClick={handleSave}
            disabled={!newWeight || !newDate}
          >
            {isEditing ? "Update" : "Save"}
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default Weights;
