import { useContext } from "react";
import { AuthContext } from "../contexts/AuthContext";

const Profile = () => {
  const { auth } = useContext(AuthContext);

  return (
    <div className="container mt-4">
      <h2 className="text-center text-light mb-4">Profile</h2>
      <div className="card shadow-sm bg-dark text-light border-0">
        <div className="card-body">
          <div className="d-flex justify-content-between align-items-center mb-3">
            <h5 className="mb-0">Profile</h5>
          </div>
          <ul>
            <li>
              <strong>Username:</strong> {auth.username}
            </li>
          </ul>
        </div>
      </div>
      <h1>Profile</h1>
    </div>
  );
};

export default Profile;
