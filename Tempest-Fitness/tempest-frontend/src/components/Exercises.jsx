import React, { useEffect, useState } from "react";
import exerciseService from "../services/exerciseService";

const Exercises = () => {
  const [exercises, setExercises] = useState([]);

  useEffect(() => {
    exerciseService.getExercises().then((data) => setExercises(data));
  }, []);

  return (
    <div>
      <h2>Exercises</h2>
      <ul>
        {exercises.map((exercise, index) => (
          <li key={index}>
            {exercise.name} - {exercise.description}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Exercises;
