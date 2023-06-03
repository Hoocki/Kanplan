import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import http from '../http-common';
import './Card.css';
import { useSelector } from 'react-redux';

const fetchLabels = (card, setModalLabels) => {
  http.get(`/labels/boardId=${card.pillar.board.id}`)
    .then((response) => {
      console.log('Labels:', response.data);
      setModalLabels(response.data);      
    })
    .catch((error) => {      
      console.log(error);
    });
};

const Card = () => {
  const { cardId } = useParams();
  const navigate = useNavigate();
  const [card, setCard] = useState(null);
  const [showLabelModal, setShowLabelModal] = useState(false);
  const [modalLabels, setModalLabels] = useState([]);
  const [addedLabels, setAddedLabels] = useState([]);
  const [selectedLabels, setSelectedLabels] = useState([]); // Состояние для выбранных меток
  const [newLabel, setNewLabel] = useState({ name: ''});  
  const { user } = useSelector(state => state.auth);
  const cardDate = card?.card_date ? new Date(card.card_date) : null;
  const formattedDate = cardDate ? `${cardDate.getFullYear()}-${(cardDate.getMonth() + 1).toString().padStart(2, '0')}-${cardDate.getDate().toString().padStart(2, '0')}` : '';

  useEffect(() => {
    document.body.classList.add('dark-theme');
    return () => {
      document.body.classList.remove('dark-theme');
    };
  }, []);

  useEffect(() => {
    const fetchCard = () => {
      http.get(`/card/${cardId}`)
        .then((response) => {
          setCard(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    };
    
    if (user) {
      fetchCard();   
    }
  }, [user, cardId]);

  useEffect(() => {
    const fetchAddedLabels = () => {
      http.get(`/labels/cardId=${cardId}`)
        .then((response) => {
          console.log('Labels:', response.data);
          setAddedLabels(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    };
   
      fetchAddedLabels();    
  }, [cardId, showLabelModal]);

  useEffect(() => {
    if (card && card.pillar && card.pillar.board && card.pillar.board.id) {
      fetchLabels(card, setModalLabels);
    }
  }, [card, setModalLabels]);

  const handleChangeName = (event) => {
    setCard({
      ...card,
      name: event.target.value
    });
  };

  const handleChangeDescription = (event) => {
    setCard({
      ...card,
      description: event.target.value
    });
  };

  const handleChangeDate = (event) => {
    setCard({
      ...card,
      card_date: event.target.value
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const updatedCard = {
      name: card.name,
      description: card.description,
      card_date: card.card_date,
      pillar: {}
    };
    http.put(`/updateCard/${card.id}`, updatedCard)
      .then(() => {
        console.log('Update Card correct: ' + updatedCard.card_date);
        navigate(`/listPillars/${card.pillar.board.id}`);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const deleteCard = () => {
    http.post(`/deleteCard/${card.id}`)
      .then(() => {
        console.log('Delete Card correct');
        navigate(`/listPillars/${card.pillar.board.id}`);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleClick = () => {
    navigate(`/listPillars/${card.pillar.board.id}`);
  };

  const handleSubmitNewLabel = (event) => {
    event.preventDefault();
    const addLabel = {
      name: newLabel.name,
      board: {}
    } 
    http.post(`/addLabel/${card.pillar.board.id}`, addLabel)
      .then(() => {
        console.log('New Label added');
        fetchLabels(card, setModalLabels);
      })
      .catch((error) => {
        console.log(error);
      });
  
    setNewLabel({ name: '' });
  };

  const handleChangeNewLabel = (event) => {
    setNewLabel({
      ...newLabel,
      [event.target.name]: event.target.value
    });
  }; 

  const handleLabelModalOpen = (event) => {
    event.preventDefault();
    fetchLabels(card, setModalLabels);
    setShowLabelModal(true);
  };

  const handleLabelModalClose = () => {
    setShowLabelModal(false);
    setNewLabel({ name: '' });
  };

  const handleLabelCheckboxChange = (labelId, isChecked) => {
    if (isChecked) {
      setSelectedLabels([...selectedLabels, labelId]);
    } else {
      setSelectedLabels(selectedLabels.filter(id => id !== labelId));
    }
  };

  const handleSaveLabels = () => {
    console.log('Added labels : ',selectedLabels)
    http.post(`/addLabelsOnCard/${cardId}`, selectedLabels)
      .then(() => {
        console.log('Labels added to card');
        fetchLabels(card, setModalLabels); // Обновление списка меток после сохранения
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleDeleteLabels = () => {
    console.log('Deleted labels: ',selectedLabels)
    http.post(`/deleteLabels`, selectedLabels)
      .then(() => {
        console.log('Labels deleted from board');
        fetchLabels(card, setModalLabels); // Обновление списка меток после сохранения

      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div className="card-container">
      {user ? (
        <>
          <form onSubmit={handleSubmit}>
            <h3 className="card-title">Карта</h3>
            <div className="form-group">
              <label htmlFor="name">Имя карточки</label>
              <input type="text" id="name" name="Name" value={card?.name || ''} onChange={handleChangeName} className="form-control" />
            </div>
            <div className="form-group">
              <label htmlFor="description">Описание карточки</label>
              <input type="text" id="description" name="Description" value={card?.description || ''} onChange={handleChangeDescription} className="form-control" />
            </div>
            <div className="form-group">
              <label htmlFor="text">Дата</label>
              <input type="date" id="date" name="Date" value={formattedDate} onChange={handleChangeDate} className="form-control" />         
            </div>
            <div className="form-group labels-container">
              <label htmlFor="text">Добавленные метки</label>
              <div className="labels-list">
                {addedLabels.map((label) => (
                  <div key={label.id}>{label.name}</div>
                ))}
              </div>
            </div>
            <div className="row g-2 mt-1">
              <div className="col-auto">
                <input type="submit" value="Обновить" className="btn btn-success" />
              </div>
              <div className="col-auto">
                <button className="btn btn-danger" onClick={deleteCard}>Удалить</button>
              </div>
              <div className="col-auto">
                <button className="btn btn-primary" onClick={handleClick}>Назад на колонкам</button>
              </div>
              <div className="col-auto">                
                <button className="btn btn-info text-light" onClick={handleLabelModalOpen}>Метки</button>
              </div>
            </div>
          </form>
          {showLabelModal && (
            <div className="modal" tabIndex="-1" role="dialog" style={{ display: 'block' }}>
              <div className="modal-dialog" role="document">
                <div className="modal-content">
                  <div className="modal-header text-dark">
                    <h5 className="modal-title">Метки</h5>
                    {/* <button type="button" className="close" onClick={handleLabelModalClose}>
                      <span aria-hidden="true">&times;</span>
                    </button> */}
                  </div>
                  <div className="modal-body">
                    {modalLabels.map((label) => (
                      <div key={label.id} className="form-check">
                        <input
                          type="checkbox"
                          id={label.id}
                          name={label.name}
                          checked={selectedLabels.includes(label.id)}
                          onChange={(e) => handleLabelCheckboxChange(label.id, e.target.checked)}
                          className="form-check-input"
                        />
                        <label htmlFor={label.id} className="form-check-label text-dark">{label.name}</label>
                      </div>
                    ))}
                      <form onSubmit={handleSubmitNewLabel}>
                       <div className="form-group text-dark">                        
                         <label htmlFor="newLabelName">Название метки:</label>
                         <input
                          type="text"
                          id="newLabelName"
                          name="name"
                          value={newLabel.name}
                          onChange={handleChangeNewLabel}
                          className="form-control"
                          required
                        />
                      </div>
                      <button type="submit" className="btn btn-primary">Добавить метку</button>
                    </form>
                  </div>
                  <div className="modal-footer">
                    <button type="button" className="btn btn-primary" onClick={handleSaveLabels}>Сохранить</button>
                    <button type="button" className="btn btn-danger" onClick={handleDeleteLabels}>Удалить</button>
                    <button type="button" className="btn btn-secondary" onClick={handleLabelModalClose}>Закрыть</button>                    
                  </div>
                </div>
              </div>
            </div>
          )}
        </>
      ) : (
        <p>Контент доступен только авторизованным пользователям.</p>
      )}
    </div>
  );
};

export default Card;