// src/AuthContext.js
import { useState } from "react";
import { AuthContext } from "../contexts/AuthContext";
import PropTypes from "prop-types";

const BASE_API_URL = import.meta.env.VITE_API_URL;
const AUTH_API_URL = BASE_API_URL + "/auth/login";

export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState(null); // null means not authenticated

  const login = async (username, password) => {
    // Create Basic Auth header
    const basicAuth = `Basic ${btoa(`${username}:${password}`)}`;

    // Verify credentials with backend (adjust endpoint as needed)
    const response = await fetch(AUTH_API_URL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    });
    if (response.ok) {
      // Store user info; in a real app, you might store a token instead
      setAuth({ username, basicAuth });
      return true;
    } else {
      throw new Error("Invalid credentials");
    }
  };

  const logout = () => {
    setAuth(null);
  };

  return (
    <AuthContext.Provider value={{ auth, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

AuthProvider.propTypes = {
  children: PropTypes.node.isRequired, // Ensures children is a valid React node
};

export default AuthProvider;
