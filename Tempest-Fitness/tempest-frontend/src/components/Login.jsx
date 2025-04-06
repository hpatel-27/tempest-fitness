import { useState, useContext } from "react";
import { AuthContext } from "../contexts/AuthContext";
import { useNavigate, Link } from "react-router-dom";
import "../styles/login.css";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();
  const [error, setError] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    login(username, password)
      .then(() => navigate("/"))
      .catch((err) => setError(err.message));
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h2 className="login-title">Login</h2>
        {error && <p className="error-message">{error}</p>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Username</label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="form-control border-0 text-light"
              placeholder="jsmith123"
              required
            />
          </div>
          <div className="form-group">
            <label>Password</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="form-control border-0 text-light"
              required
            />
          </div>
          <button type="submit" className="btn btn-primary w-100">
            <i className="bi bi-box-arrow-in-right"></i> Login
          </button>
        </form>
        <p className="register-link">
          Don&apos;t have an account? <Link to="/register">Register here</Link>.
        </p>
      </div>
    </div>
  );
};

export default Login;
