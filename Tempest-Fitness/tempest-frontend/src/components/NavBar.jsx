import { Link } from "react-router-dom";
import { useState, useRef, useEffect } from "react";
import "../styles/navbar.css";
import stormWeather from "../assets/storm_weather.png";

const NavBar = () => {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const dropdownRef = useRef(null);

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  const handleClickOutside = (e) => {
    if (dropdownRef.current && !dropdownRef.current.contains(e.target)) {
      setIsDropdownOpen(false);
    }
  };

  useEffect(() => {
    document.addEventListener("click", handleClickOutside);

    return () => {
      document.removeEventListener("click", handleClickOutside);
    };
  }, []);

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container d-flex align-items-center">
        <Link className="navbar-brand d-flex align-items-center brand" to="/">
          <span className="fw-bold brand-text">Tempest</span>
          <img
            src={stormWeather}
            className="img-logo ms-2"
            alt="Tempest logo - Black and white cloud with lightning bolt"
          />
        </Link>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <Link className="nav-link" to="/">
                Home
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/weights">
                Weights
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/exercises">
                Exercises
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/workouts">
                Workouts
              </Link>
            </li>

            <li
              className="nav-item dropdown"
              ref={dropdownRef}
              style={{ position: "relative" }}
            >
              <i
                className="bi bi-person nav-link"
                onClick={toggleDropdown}
                style={{ cursor: "pointer" }}
              ></i>

              {isDropdownOpen && (
                <div
                  className="dropdown-menu show"
                  style={{ position: "absolute", top: "100%", left: 0 }}
                >
                  <Link className="dropdown-item" to="/profile">
                    Profile
                  </Link>
                  <Link className="dropdown-item" to="/login">
                    Logout
                  </Link>
                </div>
              )}
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
