import axios from "axios";

const API_URL = "http://localhost/api/v1/weights";

// Gets all the weights that have been logged
const getWeights = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

const getWeight = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export default { getWeights };
