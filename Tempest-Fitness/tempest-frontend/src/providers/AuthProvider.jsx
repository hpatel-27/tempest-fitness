// src/AuthContext.js
import { useState } from "react";
import { AuthContext } from "../contexts/AuthContext";
import PropTypes from "prop-types";

const BASE_API_URL = import.meta.env.VITE_API_URL;
const AUTH_API_URL = BASE_API_URL + "/auth/login";
const LOGOUT_API_URL = BASE_API_URL + "/auth/logout";

export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState(null); // null means not authenticated

  const login = async (username, password) => {
    // send a POST request to the server matching a LoginRequest DTO
    const response = await fetch(AUTH_API_URL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    });

    const data = await response.json();
    const token = data.token;

    // After getting the all-clear from the server, set the auth state
    if (response.ok) {
      setAuth({ username, token });
      return true;
    } else {
      throw new Error("Invalid credentials");
    }
  };

  // Call the backend logout endpoint and clear the auth state
  const logout = async () => {
    try {
      if (auth?.token) {
        await fetch(LOGOUT_API_URL, {
          method: "POST",
          headers: {
            Authorization: auth.token,
          },
        });
      }
    } catch (error) {
      console.error("Error during logout:", error);
    } finally {
      // Always clear the auth state, even if the API call fails
      setAuth(null);
    }
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
