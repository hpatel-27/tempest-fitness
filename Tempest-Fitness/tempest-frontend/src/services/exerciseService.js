const API_URL = "http://localhost/api/v1/exercises";

const getExercises = async () => {
  const response = await fetch(API_URL);
  if (!response.ok) {
    throw new Error(`Error: ${response.status} ${response.statusText}`);
  }
  return await response.json();
};

export default { getExercises };
