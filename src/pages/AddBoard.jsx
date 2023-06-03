import React, { useEffect, useState } from 'react';
import http from "../http-common";
import { Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import './AddBoard.css';

const AddBoard = () => {

  const [name, setName] = useState("");
  const [submitted, setSubmitted] = useState(false);
  const { user } = useSelector(state => state.auth);

  useEffect(() => {
    document.body.classList.add('dark-theme');
    return () => {
      document.body.classList.remove('dark-theme');
    };
  }, []);

  const handleChange = (event) => {
    setName(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = {
      name: name,
      autoUsers: [        
      ]      
    };
    http.post(`/addBoard/${user.id}`, data)
      .then(() => {
        setSubmitted(true);
      })
      .catch(e => {
        console.log(e);
      });
  };

  if (submitted) {
    return <Navigate to="/myBoards" />;
  }

  return (
    <div className="col-sm-5">
      {user ? (
      <form onSubmit={handleSubmit}>
        <div className="form-group mb-3">
          <input
            type="text"
            name="name"
            value={name}
            placeholder="Имя доски"
            onChange={handleChange}
            className="form-control"
          />
        </div>
        <input type="submit" value="Добавить" className="btn btn-success" />
      </form>
      ) : (
        <p>Контент доступен только авторизованным пользователям.</p>
      )}
    </div>
  );
};

export default AddBoard;
