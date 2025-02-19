const API_URL = "http://localhost/api/v1/workouts";

const getWorkouts = async () => {
  const response = await fetch(API_URL);
  if (!response.ok) {
    throw new Error(`Error: ${response.status} ${response.statusText}`);
  }
  return response.data;
};

export default { getWorkouts };
