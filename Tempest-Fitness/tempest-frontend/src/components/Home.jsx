import { Link } from "react-router-dom";
import "../styles/index.css";
import "../styles/home.css";

const Home = () => {
  return (
    <div className="home-container">
      <h1 className="title">Tempest Fitness</h1>
      <nav className="nav-links">
        <Link to="/weights" className="nav-link">
          Weight Log
        </Link>
        <Link to="/exercises" className="nav-link">
          Exercises
        </Link>
        <Link to="/workouts" className="nav-link">
          Workouts
        </Link>
      </nav>
    </div>
  );
};

export default Home;
