import React, { useEffect, useState, useCallback } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import http from '../http-common';
import './ListPillars.css';

const ListPillars = () => {
  const { boardId } = useParams();
  const navigate = useNavigate();
  const [pillars, setPillars] = useState([]);
  const [isDragging, setIsDragging] = useState(false);
  const [isDragOver, setIsDragOver] = useState(false);
  const [currentPillar, setCurrentPillar] = useState(null);
  const [currentCard, setCurrentCard] = useState(null);
  const [inputValues, setInputValues] = useState({});
  const inputRefs = {};
  const { user } = useSelector(state => state.auth);

  useEffect(() => {
    document.body.classList.add('dark-theme');
    return () => {
      document.body.classList.remove('dark-theme');
    };
  }, []);

  const fetchPillarsAndCards = useCallback(() => {
    http.get(`/pillars/boardId=${boardId}`)
      .then((pillarsResponse) => {
        const fetchedPillars = pillarsResponse.data;
  
        const updatedPillars = fetchedPillars.map((pillar) => {
          return http.get(`/cards/pillarId=${pillar.id}`)
            .then((cardsResponse) => {
              const cards = cardsResponse.data;
              return { ...pillar, cards };
            });
        });
  
        Promise.all(updatedPillars)
          .then((resolvedPillars) => {
            setPillars(resolvedPillars);
          });
      })
      .catch((error) => {
        console.log(error);
      });
  }, [boardId]);
  
  useEffect(() => {
    document.body.classList.add('dark-theme');
    return () => {
      document.body.classList.remove('dark-theme');
    };
  }, []);
  
  useEffect(() => {
    if (user) {
    fetchPillarsAndCards();
  }
  }, [user,boardId, fetchPillarsAndCards]);
  

      const updatePillar = (pillarId, newName) => {
        http
          .put(`/updatePillar/${pillarId}`, { name: newName })
          .then((response) => {
            console.log('Pillar name updated:', response.data);
            fetchPillarsAndCards();
          })
          .catch((error) => {
            console.log(error);
          });
      };
      

  const handleDeletePillar = (pillarId) => {
    http.post(`/deletePillar/${pillarId}`)
      .then(() => {
        console.log('Delete Pillar correct');
        const updatedPillars = pillars.filter((pillar) => pillar.id !== pillarId);
        setPillars(updatedPillars);
        })
      .catch((error) => {
        console.log(error);
        });
    };

  const handlePillarChange = (event, pillarId) => {
    const newName = event.target.value;
    setInputValues((prevInputValues) => ({
      ...prevInputValues,
      [pillarId]: newName,
    }));
  };

   const handlePillarFocus = (pillarId) => {
    const inputRef = inputRefs[pillarId];
    if (inputRef) {
      const currentValue = inputValues[pillarId] || '';
      const cursorPosition = currentValue.length;
      inputRef.setSelectionRange(cursorPosition, cursorPosition);
    }
  };

  const handlePillarBlur = (pillarId) => {
    const inputRef = inputRefs[pillarId];
    if (inputRef) {
      const newName = inputRef.value;
      setInputValues((prevInputValues) => ({
        ...prevInputValues,
        [pillarId]: newName,
      }));
      updatePillar(pillarId, newName);
    }
  };
  

  const handleCardClick = (card) => {
    navigate(`/card/${card.id}`);
  };

  const handleAddCardClick = (pillarId) => {
    navigate(`/addcard/${pillarId}/boardId/${boardId}`);
  };

  const handleAddColumnClick = (boardId) => {
    navigate(`/addpillar/${boardId}`);
  };

  const handleAddPillarClick = (boardId) => {
    navigate(`/addUserOnBoard/${boardId}`);
  };
 
  const handleCardDragStart = (e, pillar, card) => {
    setIsDragging(true);
    setCurrentPillar(pillar);
    setCurrentCard(card);
  };

  const handleCardDragOver = (e, pillar) => {
    e.preventDefault();
    setIsDragOver(pillar.id); 
  };

  const handleCardDragLeave = (e) => {
    e.preventDefault();
    setIsDragOver(null); 
  };

  const handleCardDrop = (e, targetPillar) => {
    e.preventDefault();
    setIsDragOver(null); 

    if (isDragging && currentPillar.id !== targetPillar.id) {
      const droppedCard = currentPillar.cards.find((c) => c === currentCard);
      const currentPillarCards = currentPillar.cards.filter((c) => c !== currentCard);

      const updatedPillars = pillars.map((pillar) => {
        if (pillar.id === currentPillar.id) {
          return { ...pillar, cards: currentPillarCards };
        } else if (pillar.id === targetPillar.id) {
          const dropIndex = targetPillar.cards.indexOf(currentCard);
          const updatedCards = [
            ...targetPillar.cards.slice(0, dropIndex),
            droppedCard,
            ...targetPillar.cards.slice(dropIndex),
          ];
          return { ...pillar, cards: updatedCards };
        } else {
          return pillar;
        }
      });

      setPillars(updatedPillars);
      const newPillar = {
        id: targetPillar.id
      }
      http
        .put(`/moveCard/${currentCard.id}`, newPillar)
        .then((response) => {
          console.log('Card moved successfully:', response.data);
        })
        .catch((error) => {
          console.log('Error updating card:', error);
        });
    }
  };

  return (
    <div className="ListPillars">
      {user ? (
        <>
      {pillars.map((pillar) => (
        <div
          key={pillar.id}
          className={`pillar ${isDragOver === pillar.id ? 'dragging-over' : ''}`}
          onDragOver={(e) => handleCardDragOver(e, pillar)}
          onDragLeave={handleCardDragLeave}
          onDrop={(e) => handleCardDrop(e, pillar)}
        >
          <div className="pillar_header">
          <input
            type="text"
            className="pillar_name"
            value={inputValues[pillar.id] || pillar.name}
            onChange={(event) => handlePillarChange(event, pillar.id)}
            onFocus={() => handlePillarFocus(pillar.id)}
            onBlur={() => handlePillarBlur(pillar.id)}
            ref={(input) => (inputRefs[pillar.id] = input)}
          />
          </div>
          <div className="pillar_content">
            {pillar.cards.map((card) => (
              <div
                key={card.id}
                onDragStart={(e) => handleCardDragStart(e, pillar, card)}
                draggable
                className={`card ${isDragging ? 'dragging' : ''}`}
                onClick={() => handleCardClick(card)}
              >
                {card.name}
              </div>
            ))}
          </div>
          {isDragOver === pillar.id && (
            <div
              className="placeholder"
              onDragOver={handleCardDragOver}
              onDragLeave={handleCardDragLeave}
              onDrop={(e) => handleCardDrop(e, pillar)}
            />
          )}
          <div className="button-group">
            <button className="btn btn-primary" onClick={() => handleAddCardClick(pillar.id)}>
              Добавить карточку
            </button>
              <div className="delete-card-button">
                <button className="btn btn-danger" onClick={() => handleDeletePillar(pillar.id)}>Удалить</button>
              </div>
            </div>
        </div>
      ))}
        <div className="add-column-button">
          <button className="btn btn-primary" onClick={() => handleAddColumnClick(boardId)}>
            Добавить колонку
          </button>
        </div>
        <div className="add-user-section">
          <h3>Добавить пользователя на доску</h3>
          <button className="btn btn-primary" onClick={() => handleAddPillarClick(boardId)}>
            Добавить
          </button>
        </div>
          </>
      ) : (
      <p>Контент доступен только авторизованным пользователям.</p>
    )}
    </div>
    
  );
}  

export default ListPillars;