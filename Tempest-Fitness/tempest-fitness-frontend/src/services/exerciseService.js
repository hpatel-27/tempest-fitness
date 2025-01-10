import axios from "axios";

const API_URL = "http://localhost/api/v1/exercises";

const getExercises = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export default { getExercises };
