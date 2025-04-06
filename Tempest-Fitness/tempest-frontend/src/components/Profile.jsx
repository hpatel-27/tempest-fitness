import { useState, useContext, useEffect } from "react";
import { AuthContext } from "../contexts/AuthContext";
import userService from "../services/userService";
import Swal from "sweetalert2";

const Profile = () => {
  const { auth } = useContext(AuthContext);
  const [user, setUser] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    username: "",
    role: "",
    firstName: "",
    lastName: "",
    height: 0,
  });

  useEffect(() => {
    loadProfile(auth);
  }, [auth]);

  const loadProfile = async (auth) => {
    try {
      const data = await userService.getCurrentUser(auth);
      setUser(data);
      setFormData({
        username: data?.username || "",
        role: data?.role || "",
        firstName: data?.firstName || "",
        lastName: data?.lastName || "",
        height: data?.height || 0,
      });
    } catch (error) {
      console.error("Failed to fetch the currently authenticated user.", error);
      Swal.fire({
        title: "Error",
        text: "Failed to load user. Please try again.",
        icon: "error",
        confirmButtonColor: "#6b51ab",
        background: "#242526",
        color: "#fff",
      });
    }
  };

  const renderField = (label, key, type = "text") => (
    <div className="col-md-6 mb-2">
      <div className="text-light small">{label}</div>
      {isEditing ? (
        <input
          type={type}
          className="form-control text-light border-0"
          value={formData[key]}
          onChange={(e) => setFormData({ ...formData, [key]: e.target.value })}
        />
      ) : (
        <div className="fw-semibold">{formData[key] || "Not set."}</div>
      )}
    </div>
  );

  const handleSave = async () => {
    try {
      await userService.updateUser(auth, formData, user.username);
      setIsEditing(false);
      setUser({ ...user, ...formData });
      Swal.fire({
        title: "Updated!",
        text: "Your user profile was updated.",
        icon: "success",
        confirmButtonColor: "#6b51ab",
        background: "#242526",
        color: "#fff",
      });
    } catch (error) {
      console.error("Failed to update user.", error);
      Swal.fire({
        title: "Error",
        text: "Failed to load user. Please try again.",
        icon: "error",
        confirmButtonColor: "#6b51ab",
        background: "#242526",
        color: "#fff",
      });
    }
  };

  if (!user) return <div className="text-light">Loading...</div>;

  return (
    <div className="container mt-4">
      <div className="card shadow-sm bg-dark text-light border-0">
        <div className="card-body">
          <div className="d-flex justify-content-between align-items-center mb-3">
            <h5 className="mb-0">Profile</h5>
            <button
              className="btn btn-sm btn-outline-secondary me-1"
              onClick={() => setIsEditing((prev) => !prev)}
            >
              <i className="bi bi-pencil"></i>
              {isEditing ? " Cancel" : " Edit"}
            </button>
          </div>

          <div className="row">
            {renderField("Username", "username")}
            {renderField("Role", "role")}
            {renderField("First Name", "firstName")}
            {renderField("Last Name", "lastName")}
            {renderField("Height (cm)", "height", "number")}

            {isEditing && (
              <div className="mt-3 text-end">
                <button className="btn btn-primary" onClick={handleSave}>
                  Save Changes
                </button>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;
