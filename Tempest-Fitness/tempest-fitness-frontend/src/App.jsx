import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./components/Home";
import Weights from "./components/Weights";
import Exercises from "./components/Exercises";
import Workouts from "./components/Workouts";
import Navbar from "./components/NavBar";

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
