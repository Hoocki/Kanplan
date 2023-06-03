import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import http from '../http-common';
import './AddUser.css'; // Import the CSS file for styling
import { useSelector } from 'react-redux';

const AddUser = () => {
  const { boardId } = useParams();
  const navigate = useNavigate();
  const [mail, setMailName] = useState('');
  const [error, setError] = useState('');
  const { user } = useSelector(state => state.auth);

  useEffect(() => {
    document.body.classList.add('dark-theme');
    return () => {
      document.body.classList.remove('dark-theme');
    };
  }, []);

  const handleNameChange = (event) => {
    setMailName(event.target.value);
  };

  const handleAddUserOnBoard = () => {
    const newUser = {
      mail: mail
    }    
        http.post(`/addUserOnBoard/${boardId}`, newUser)
          .then((response) => {
            console.log('Board added user successfully:', response.data);
            navigate(`/listPillars/${boardId}`);
          })
          .catch((error) => {            
            console.log('Error adding user:', error);
            setError('Данный пользователь не найден');
          });

  };

  return (
    <div className="add-user-container">
        {user ? (
        <>
      <h1>Новый пользователь</h1>
      <div className="form-field"> 
        <label htmlFor="name">Почта:</label>
        <input type="text" id="mail" value={mail} onChange={handleNameChange} />
      </div>
      {error && <p className="error-message">{error}</p>}
      <button className="add-user-button" onClick={handleAddUserOnBoard}>Добавить пользователя</button> 
      </>
      ) : (
      <p>Контент доступен только авторизованным пользователям.</p>
    )}
    </div>
  );
};

export default AddUser;
