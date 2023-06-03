import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import http from '../http-common';
import './AddPillar.css';
import { useSelector } from 'react-redux';


const AddPillar = () => {
  const { boardId } = useParams();
  const navigate = useNavigate();
  const [name, setName] = useState('');
  const { user } = useSelector(state => state.auth);

  useEffect(() => {
    document.body.classList.add('dark-theme');
    return () => {
      document.body.classList.remove('dark-theme');
    };
  }, []);

  const handleNameChange = (event) => {
    setName(event.target.value);
  };

  const handleAddPillar = () => {
    const newPillar = {
      name: name,
      board: {}      
    };
        http.post(`/addPillar/${boardId}`, newPillar)
          .then((response) => {
            
            navigate(`/listPillars/${boardId}`);
          })
          .catch((error) => {
            console.log('Error adding pillar:', error);
          });
  };

  return (
    <div className="add-card-container">
      {user ? (
        <>
      <h1>Новая колонка</h1>
      <div className="form-field"> 
        <label htmlFor="name">Имя:</label>
        <input type="text" id="name" value={name} onChange={handleNameChange} />
      </div>
      <button className="add-card-button" onClick={handleAddPillar}>Добавить колонку</button> {/* Apply the CSS class to the button */}
      </>
      ) : (
      <p>Контент доступен только авторизованным пользователям.</p>
    )}
    </div>
  );
};

export default AddPillar;
