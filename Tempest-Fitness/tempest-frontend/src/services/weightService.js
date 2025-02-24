const BASE_API_URL = import.meta.env.VITE_API_URL;
const WEIGHT_API_URL = BASE_API_URL + "/weights";

// Gets all the weights that have been logged
const getWeights = async (auth) => {
  // Check if the user is authenticated
  if (!auth || !auth.basicAuth) {
    throw new Error("User is not authenticated");
  }

  // Encode credentials in Base64 for Basic Authentication
  const response = await fetch(WEIGHT_API_URL, {
    method: "GET",
    headers: {
      Authorization: auth.basicAuth,
      "Content-Type": "application/json",
    },
  });

  // Throw an error if the response is not OK with the status message
  if (!response.ok) {
    throw new Error(`Error: ${response.status} ${response.statusText}`);
  }

  return await response.json();
};

// Don't really need this if we list out the weights
// // Gets a specific weight for a given date
// const getWeight = async (date) => {
//   const response = await fetch(`${API_URL}/${date}`);
//   if (!response.ok) {
//     throw new Error(`Error: ${response.status} ${response.statusText}`);
//   }
//   return await response.json();
// };

export default { getWeights };
