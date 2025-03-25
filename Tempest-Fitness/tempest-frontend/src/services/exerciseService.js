const BASE_API_URL = import.meta.env.VITE_API_URL;
const EXERCISE_API_URL = BASE_API_URL + "/exercises";

const getExercises = async () => {
  const response = await fetch(EXERCISE_API_URL);
  if (!response.ok) {
    throw new Error(`Error: ${response.status} ${response.statusText}`);
  }
  return await response.json();
};

export default { getExercises };
