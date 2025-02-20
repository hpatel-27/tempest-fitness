import { Link } from "react-router-dom";

const Home = () => (
  <div>
    <h1>Tempest Fitness</h1>
    <nav>
      <Link to="/weights">Weight Log</Link>
      <Link to="/exercises">Exercises</Link>
      <Link to="/workouts">Workouts</Link>
    </nav>
  </div>
);

export default Home;
