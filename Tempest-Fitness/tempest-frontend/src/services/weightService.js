const BASE_API_URL = import.meta.env.VITE_API_URL;
const WEIGHT_API_URL = BASE_API_URL + "/weights";

// Gets all the weights that have been logged
const getWeights = async (auth) => {
  // Check if the user is authenticated
  if (!auth || !auth?.token) {
    throw new Error("User is not authenticated.");
  }

  // Encode credentials in Base64 for Basic Authentication and get from the weight api
  const response = await fetch(WEIGHT_API_URL, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${auth.token}`,
      "Content-Type": "application/json",
    },
  });

  // Throw an error if the response is not OK with the status message
  if (!response.ok) {
    throw new Error(`Error: ${response.status} ${response.statusText}`);
  }

  return await response.json();
};

const addWeight = async (auth, weight) => {
  // Check if the user is authenticated
  if (!auth || !auth?.token) {
    throw new Error("User is not authenticated");
  }
  // Encode credentials in Base64 for Basic Authentication and post to the weight api
  const response = await fetch(WEIGHT_API_URL, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${auth.token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(weight),
  });

  // Throw an error if the response is not OK with the status message
  if (!response.ok) {
    throw new Error(`Error: ${response.status} ${response.statusText}`);
  }

  return await response.json();
};

const deleteWeight = async (auth, weightDate) => {
  // Check if the user is authenticated
  if (!auth || !auth?.token) {
    throw new Error("User is not authenticated");
  }

  // fetch from the weight weight api with the date as a path variable
  const response = await fetch(`${WEIGHT_API_URL}/${weightDate}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${auth.token}`,
    },
  });

  if (!response.ok) {
    throw new Error(`Error: ${response.status} ${response.statusText}`);
  }

  return await response.json();
};

const updateWeight = async (auth, weightDate, weight) => {
  // Check if the user is authenticated
  if (!auth || !auth?.token) {
    throw new Error("User is not authenticated");
  }

  // fetch from the weight weight api with the date as a path variable
  const response = await fetch(`${WEIGHT_API_URL}/${weightDate}`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${auth.token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(weight),
  });

  if (!response.ok) {
    throw new Error(`Error: ${response.status} ${response.statusText}`);
  }

  return await response.json();
};

export default { getWeights, addWeight, updateWeight, deleteWeight };
