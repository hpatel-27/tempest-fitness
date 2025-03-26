const BASE_API_URL = import.meta.env.VITE_API_URL;
const EXERCISE_API_URL = BASE_API_URL + "/exercises";

const getExercises = async (auth) => {
  if (!auth || !auth.basicAuth) {
    throw new Error("User is not authenticated");
  }

  const response = await fetch(EXERCISE_API_URL, {
    method: "GET",
    headers: {
      Authorization: auth.basicAuth,
      "Content-Type": "application/json",
    },
  });
  if (!response.ok) {
    throw new Error(`Error: ${response.status} ${response.statusText}`);
  }
  return await response.json();
};

const addExercise = async (auth, exercise) => {
  if (!auth || !auth.basicAuth) {
    throw new Error("User is not authenticated");
  }
  const response = await fetch(EXERCISE_API_URL, {
    method: "POST",
    headers: {
      Authorization: auth.basicAuth,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(exercise),
  });

  // Throw the error that the API returns
  if (!response.ok) {
    throw new Error(`Error: ${response.status} ${response.statusText}`);
  }
  return await response.json();
};

const deleteExercise = async (auth, exerciseId) => {
  if (!auth || !auth.basicAuth) {
    throw new Error("User is not authenticated.");
  }

  const response = await fetch(`${EXERCISE_API_URL}/${exerciseId}`, {
    method: "DELETE",
    headers: {
      Authorization: auth.basicAuth,
    },
  });

  // Throw the error received from the API
  if (!response.ok) {
    throw new Error(`Error: ${response.status} ${response.statusText}`);
  }
};

const editExercise = async (auth, exerciseId) => {
  if (!auth || !auth.basicAuth) {
    throw new Error("User is not authenticated.");
  }

  const response = await fetch(`${EXERCISE_API_URL}/${exerciseId}`, {
    method: "PUT",
    headers: {
      Authorization: auth.basicAuth,
    },
  });

  // Throw the error received from the API
  if (!response.ok) {
    throw new Error(`Error: ${response.status} ${response.statusText}`);
  }
};

export default { getExercises, addExercise, deleteExercise, editExercise };
