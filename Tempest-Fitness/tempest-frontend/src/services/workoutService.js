const WORKOUT_API_URL = import.meta.env.VITE_WORKOUT_API_URL;

const getWorkouts = async (auth) => {
  // Check if the user is authenticated
  if (!auth || !auth.basicAuth) {
    throw new Error("User is not authenticated.");
  }

  // Encode credentials in Base64 for Basic Authentication and get from the weight api
  const response = await fetch(WORKOUT_API_URL, {
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

export default { getWorkouts };
