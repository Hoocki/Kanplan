import React, { useEffect,useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import http from '../http-common';
import './AddCard.css'; // Import the CSS file for styling
import { useSelector } from 'react-redux';

const AddCard = () => {
  const { pillarId, boardId } = useParams();
  const navigate = useNavigate();
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [cardDate, setCardDate] = useState('');
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

  const handleDescriptionChange = (event) => {
    setDescription(event.target.value);
  };

  const handleDateChange = (event) => {
    setCardDate(event.target.value);
  };

  const handleAddCard = () => {
    const newCard = {
      name: name,
      description: description,
      card_date: cardDate,
      pillar: {},
    };
        http.post(`/addCard/${pillarId}`, newCard)
          .then((response) => {
            console.log('Card added successfully:', response.data);
            navigate(`/listPillars/${boardId}`);
          })
          .catch((error) => {
            console.log('Error adding card:', error);
          });

  };

  return (
    <div className="add-card-container">
      {user ? (
        <> 
      <h1>Новая карточка</h1>
      <div className="form-field"> 
        <label htmlFor="name">Имя:</label>
        <input type="text" id="name" value={name} onChange={handleNameChange} />
      </div>
      <div className="form-field"> 
        <label htmlFor="description">Описание:</label>
        <input type="text" id="description" value={description} onChange={handleDescriptionChange} />
      </div>
      <div className="form-field"> 
        <label htmlFor="cardDate">Дата:</label>
        <input type="date" id="cardDate" value={cardDate} onChange={handleDateChange} />
      </div>
      <button className="add-card-button" onClick={handleAddCard}>Добавить карточку</button> 
      </>
      ) : (
      <p>Контент доступен только авторизованным пользователям.</p>
    )}
    </div>
  );
};

export default AddCard;
