// src/ProtectedRoute.js
import { useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from "../contexts/AuthContext";
import PropTypes from "prop-types";

const ProtectedRoute = ({ children }) => {
  // Use the AuthContext to check if the user is authenticated
  const { auth } = useContext(AuthContext);
  // Send them to login if they are not authenticated
  if (!auth) {
    return <Navigate to="/login" />;
  }
  return children;
};

ProtectedRoute.propTypes = {
  children: PropTypes.node.isRequired,
};

export default ProtectedRoute;
