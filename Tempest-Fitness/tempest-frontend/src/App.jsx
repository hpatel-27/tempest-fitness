import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "../../tempest-frontend/src/components/Home";
import Weights from "../../tempest-frontend/src/components/Weights";
import Exercises from "../../tempest-frontend/src/components/Exercises";
import Workouts from "../../tempest-frontend/src/components/Workouts";
import Navbar from "../../tempest-frontend/src/components/NavBar";

const App = () => (
  <Router>
    <div>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/weights" element={<Weights />} />
        <Route path="/exercises" element={<Exercises />} />
        <Route path="/workouts" element={<Workouts />} />
      </Routes>
    </div>
  </Router>
);

export default App;
