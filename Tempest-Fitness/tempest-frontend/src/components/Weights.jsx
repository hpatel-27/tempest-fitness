import { useEffect, useState, useContext } from "react";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Alert from "react-bootstrap/Alert";
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

  useEffect(() => {
    weightService.getWeights(auth).then((data) => {
      console.log("Fetched weights: ", data);
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

  // Add the user's new submitted weight
  const addNewWeight = () => {
    console.log("Button clicked for add");
    setShowModal(true);
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

  // Handle saving a new weight
  const handleSave = async () => {
    if (!validateInputs()) return;

    try {
      // send weight to the backend
      const savedWeight = await weightService.addWeight(auth, {
        weight: newWeight,
        date: newDate,
      });

      // make sure the weight was saved successfully
      if (savedWeight.status != "success") {
        alert("Failed to add the new weight. Please try again.");
        return;
      }
      // update frontend state with the new weight
      const updatedWeights = [...weights, { weight: newWeight, date: newDate }];
      sortWeights(updatedWeights, sortOrder);

      // close modal and reset form
      setShowModal(false);
      setNewWeight("");
      setNewDate("");
      setErrors({ weight: "", date: "" });
    } catch (error) {
      // Some issue with adding a new weight
      console.error("Failed to add the new weight: ", error);
      alert("Failed to add the new weight. Please try again.");
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
                onClick={addNewWeight}
              >
                <i className="bi bi-plus-circle"></i>
              </button>
            </div>
          </div>
          <ul className="list-group list-group-flush">
            {weights.map((weight, index) => (
              <li
                key={index}
                className="list-group-item d-flex justify-content-between align-items-center bg-transparent text-light border-secondary"
              >
                <span className="fw-bold">{formatDate(weight.date)}</span>
                <span
                  className="badge rounded-pill weight-badge"
                  id="weight-badge"
                >
                  {weight.weight} lbs
                </span>
              </li>
            ))}
          </ul>
        </div>
      </div>

      <Modal show={showModal} onHide={() => setShowModal(false)} centered>
        <Modal.Header closeButton>
          <Modal.Title>Add New Weight</Modal.Title>
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
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowModal(false)}>
            Cancel
          </Button>
          <Button
            className="btn-save"
            onClick={handleSave}
            disabled={!newWeight || !newDate}
          >
            Save
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default Weights;
