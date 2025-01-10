import axios from "axios";

const API_URL = "http://localhost/api/v1/workouts";

const getWorkouts = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export default { getWorkouts };
