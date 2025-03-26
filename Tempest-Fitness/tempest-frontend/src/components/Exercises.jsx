import { useEffect, useState, useContext } from "react";
import exerciseService from "../services/exerciseService";
import { AuthContext } from "../contexts/AuthContext";

const Exercises = () => {
  const [exercises, setExercises] = useState([]);
  const { auth } = useContext(AuthContext);

  useEffect(() => {
    exerciseService.getExercises(auth).then((data) => {
      console.log("Fetched exercises: ", data);
      setExercises(data);
    });
  }, [auth]);

  return (
    <div>
      <h2>Exercises</h2>
      <ul>
        {exercises.map((exercise, index) => (
          <li key={index}>{exercise.name}</li>
        ))}
      </ul>
    </div>
  );
};

export default Exercises;
