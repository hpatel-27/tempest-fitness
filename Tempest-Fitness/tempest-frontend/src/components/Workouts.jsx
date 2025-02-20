import { useEffect, useState } from "react";
import workoutService from "../services/workoutService";

const Workouts = () => {
  const [workouts, setWorkouts] = useState([]);

  useEffect(() => {
    workoutService.getWorkouts().then((data) => setWorkouts(data));
  }, []);

  return (
    <div>
      <h2>Completed Workouts</h2>
      <ul>
        {workouts.map((workout, index) => (
          <li key={index}>
            <strong>{workout.name}</strong> - {workout.date}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Workouts;
