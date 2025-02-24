const Profile = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  return (
    <div className="container mt-4">
      <h2 className="text-center text-light mb-4">Profile</h2>
      <div className="card shadow-sm bg-dark text-light border-0">
        <div className="card-body">
          <div className="d-flex justify-content-between align-items-center mb-3">
            <h5 className="mb-0">Profile</h5>
          </div>
        </div>
      </div>
      <h1>Profile</h1>
    </div>
  );
};

export default Profile;
