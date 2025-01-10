import React, { useEffect, useState } from "react";
import weightService from "../services/weightService";

const Weights = () => {
  const [weights, setWeights] = useState([]);

  useEffect(() => {
    weightService.getWeights().then((data) => setWeights(data));
  }, []);

  return (
    <div>
      <h2>Weights</h2>
      <ul>
        {weights.map((weight, index) => (
          <li key={index}>
            {weight.date}: {weight.value} lbs
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Weights;
