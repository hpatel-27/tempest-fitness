const BASE_API_URL = import.meta.env.VITE_API_URL;
const EXERCISE_API_URL = BASE_API_URL + "/exercises";
const REQUEST_TIMEOUT = 10000; // 10 seconds

// Valid exercise types
const VALID_EXERCISE_TYPES = [
  "Olympic",
  "Strength",
  "Plyometric",
  "Cardio",
  "Flexibility",
  "Balance",
  "Powerlifting",
  "Bodyweight",
  "Other",
];

// Valid difficulty levels
const VALID_DIFFICULTY_LEVELS = ["Beginner", "Intermediate", "Advanced"];

// Helper function to validate exercise data
const validateExercise = (exercise) => {
  if (!exercise) {
    throw new Error("Exercise data is required");
  }

  // Required fields validation
  if (!exercise.name || typeof exercise.name !== "string") {
    throw new Error("Exercise name is required and must be a string");
  }

  if (!exercise.type || typeof exercise.type !== "string") {
    throw new Error("Exercise type is required and must be a string");
  }

  if (!exercise.muscle || typeof exercise.muscle !== "string") {
    throw new Error("Exercise muscle is required and must be a string");
  }

  if (!exercise.equipment || typeof exercise.equipment !== "string") {
    throw new Error("Exercise equipment is required and must be a string");
  }

  if (!exercise.difficulty || typeof exercise.difficulty !== "string") {
    throw new Error("Exercise difficulty is required and must be a string");
  }

  // Validate exercise type
  if (!VALID_EXERCISE_TYPES.includes(exercise.type)) {
    throw new Error(
      `Invalid exercise type. Must be one of: ${VALID_EXERCISE_TYPES.join(
        ", "
      )}`
    );
  }

  // Validate difficulty level
  if (!VALID_DIFFICULTY_LEVELS.includes(exercise.difficulty)) {
    throw new Error(
      `Invalid difficulty level. Must be one of: ${VALID_DIFFICULTY_LEVELS.join(
        ", "
      )}`
    );
  }

  // Validate string lengths
  if (exercise.name.length > 100) {
    throw new Error("Exercise name must be less than 100 characters");
  }

  if (exercise.type.length > 50) {
    throw new Error("Exercise type must be less than 50 characters");
  }

  if (exercise.muscle.length > 100) {
    throw new Error("Exercise muscle must be less than 100 characters");
  }

  if (exercise.equipment.length > 100) {
    throw new Error("Exercise equipment must be less than 100 characters");
  }

  if (exercise.difficulty.length > 20) {
    throw new Error("Exercise difficulty must be less than 20 characters");
  }
};

// Helper function to sanitize exercise data
const sanitizeExercise = (exercise) => {
  return {
    ...exercise,
    name: exercise.name.trim(),
    type: exercise.type.trim(),
    muscle: exercise.muscle.trim(),
    equipment: exercise.equipment.trim(),
    difficulty: exercise.difficulty.trim(),
  };
};

const getExercises = async (auth) => {
  if (!auth || !auth?.token) {
    throw new Error("User is not authenticated");
  }

  const controller = new AbortController();
  const timeoutId = setTimeout(() => controller.abort(), REQUEST_TIMEOUT);

  try {
    const response = await fetch(EXERCISE_API_URL, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${auth.token}`,
        "Content-Type": "application/json",
      },
      signal: controller.signal,
    });
    clearTimeout(timeoutId);

    if (!response.ok) {
      const errorData = await response.json().catch(() => null);
      throw new Error(
        errorData?.message || `Error: ${response.status} ${response.statusText}`
      );
    }
    return await response.json();
  } catch (error) {
    if (error.name === "AbortError") {
      throw new Error("Request timed out");
    }
    throw error;
  }
};

const addExercise = async (auth, exercise) => {
  if (!auth || !auth?.token) {
    throw new Error("User is not authenticated");
  }

  validateExercise(exercise);
  const sanitizedExercise = sanitizeExercise(exercise);

  const controller = new AbortController();
  const timeoutId = setTimeout(() => controller.abort(), REQUEST_TIMEOUT);

  try {
    const response = await fetch(EXERCISE_API_URL, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${auth.token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(sanitizedExercise),
      signal: controller.signal,
    });
    clearTimeout(timeoutId);

    if (!response.ok) {
      const errorData = await response.json().catch(() => null);
      throw new Error(
        errorData?.message || `Error: ${response.status} ${response.statusText}`
      );
    }
    return await response.json();
  } catch (error) {
    if (error.name === "AbortError") {
      throw new Error("Request timed out");
    }
    throw error;
  }
};

const deleteExercise = async (auth, exerciseId) => {
  if (!auth || !auth?.token) {
    throw new Error("User is not authenticated.");
  }

  if (!exerciseId) {
    throw new Error("Exercise ID is required");
  }

  const controller = new AbortController();
  const timeoutId = setTimeout(() => controller.abort(), REQUEST_TIMEOUT);

  try {
    const response = await fetch(`${EXERCISE_API_URL}/${exerciseId}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${auth.token}`,
      },
      signal: controller.signal,
    });
    clearTimeout(timeoutId);

    if (!response.ok) {
      const errorData = await response.json().catch(() => null);
      throw new Error(
        errorData?.message || `Error: ${response.status} ${response.statusText}`
      );
    }
  } catch (error) {
    if (error.name === "AbortError") {
      throw new Error("Request timed out");
    }
    throw error;
  }
};

const editExercise = async (auth, exerciseId, exercise) => {
  if (!auth || !auth?.token) {
    throw new Error("User is not authenticated.");
  }

  if (!exerciseId) {
    throw new Error("Exercise ID is required");
  }

  validateExercise(exercise);
  const sanitizedExercise = sanitizeExercise(exercise);

  const controller = new AbortController();
  const timeoutId = setTimeout(() => controller.abort(), REQUEST_TIMEOUT);

  try {
    const response = await fetch(`${EXERCISE_API_URL}/${exerciseId}`, {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${auth.token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(sanitizedExercise),
      signal: controller.signal,
    });
    clearTimeout(timeoutId);

    if (!response.ok) {
      const errorData = await response.json().catch(() => null);
      throw new Error(
        errorData?.message || `Error: ${response.status} ${response.statusText}`
      );
    }
    return await response.json();
  } catch (error) {
    if (error.name === "AbortError") {
      throw new Error("Request timed out");
    }
    throw error;
  }
};

export default { getExercises, addExercise, deleteExercise, editExercise };
