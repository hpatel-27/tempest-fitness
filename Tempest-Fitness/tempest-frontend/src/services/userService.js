const BASE_API_URL = import.meta.env.VITE_API_URL;
const USER_API_URL = BASE_API_URL + "/currentUser";
const UPDATE_API_URL = BASE_API_URL + "/users";
// Get the current user, which should be the logged in user
const getCurrentUser = async (auth) => {
  // Check that the auth is set since the user is supposed to be authenticated
  if (!auth || !auth.basicAuth) {
    throw new Error("User is not authenticated.");
  }

  // Hit the API for the current user
  const response = await fetch(USER_API_URL, {
    headers: {
      Authorization: auth?.basicAuth,
      "Content-type": "application/json",
    },
    method: "GET",
  });

  // If the request was not okay, then provide the status and message
  if (!response.ok) {
    throw new Error(`Error: ${response.status} ${response.statusText}`);
  }

  return await response.json();
};

const updateUser = async (auth, updatedUser, username) => {
  // Check that the auth is set since the user is supposed to be authenticated
  if (!auth || !auth.basicAuth) {
    throw new Error("User is not authenticated.");
  }

  // Hit the API for the current user
  const response = await fetch(`${UPDATE_API_URL}/${username}`, {
    headers: {
      Authorization: auth?.basicAuth,
      "Content-type": "application/json",
    },
    method: "PUT",
    body: JSON.stringify(updatedUser),
  });

  console.log("Response from the api: ", response);
  // If the request was not okay, then provide the status and message
  if (!response.ok) {
    throw new Error(`Error: ${response.status} ${response.statusText}`);
  }

  return await response.json();
};
export default { getCurrentUser, updateUser };
