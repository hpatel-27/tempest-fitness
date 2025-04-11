const WORKOUT_API_URL = import.meta.env.VITE_WORKOUT_API_URL;

const getWorkouts = async (auth) => {
  // Check if the user is authenticated
  if (!auth || !auth?.token) {
    throw new Error("User is not authenticated.");
  }

  // Encode credentials in Base64 for Basic Authentication and get from the workout API
  const response = await fetch(WORKOUT_API_URL, {
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

const getWorkoutByDate = async (date, auth) => {
  // Check if the user is authenticated
  if (!auth || !auth?.token) {
    throw new Error("User is not authenticated.");
  }

  // Encode credentials in Base64 for Basic Authentication and get from the workout API
  const response = await fetch(`${WORKOUT_API_URL}/${date}`, {
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

const createWorkout = async (workout, auth) => {
  // Check if the user is authenticated
  if (!auth || !auth?.token) {
    throw new Error("User is not authenticated.");
  }

  // Encode credentials in Base64 for Basic Authentication and get from the workout api
  const response = await fetch(WORKOUT_API_URL, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${auth.token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(workout),
  });

  // Throw an error if the response is not OK with the status message
  if (!response.ok) {
    throw new Error(`Error: ${response.status} ${response.statusText}`);
  }

  return await response.json();
};

const deleteWorkout = async (workoutDate, auth) => {
  // Check if the user is authenticated
  if (!auth || !auth?.token) {
    throw new Error("User is not authenticated");
  }

  // fetch from the weight weight api with the date as a path variable
  const response = await fetch(`${WORKOUT_API_URL}/${workoutDate}`, {
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

const updateWorkout = async (workoutDate, workout, auth) => {
  // Check if the user is authenticated
  if (!auth || !auth?.token) {
    throw new Error("User is not authenticated");
  }

  // fetch from the weight weight api with the date as a path variable
  const response = await fetch(`${WORKOUT_API_URL}/${workoutDate}`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${auth.token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(workout),
  });

  if (!response.ok) {
    throw new Error(`Error: ${response.status} ${response.statusText}`);
  }

  return await response.json();
};

export default {
  getWorkouts,
  getWorkoutByDate,
  createWorkout,
  deleteWorkout,
  updateWorkout,
};
