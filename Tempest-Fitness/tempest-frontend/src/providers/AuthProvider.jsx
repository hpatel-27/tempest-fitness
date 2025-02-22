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

    // send a POST request to the server matching a LoginRequest DTO
    const response = await fetch(AUTH_API_URL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    });
    // After getting the all-clear from the server, set the auth state
    if (response.ok) {
      setAuth({ username, basicAuth });
      return true;
    } else {
      throw new Error("Invalid credentials");
    }
  };

  // Clear the auth state
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
  children: PropTypes.node.isRequired,
};

export default AuthProvider;
