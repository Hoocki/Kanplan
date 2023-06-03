import React, { useState, useEffect } from 'react';
import http from '../http-common';
import './ListBoards.css';
import { Link, useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';

const ListBoards = () => {
  const [boards, setBoards] = useState([]);
  const [inputValues, setInputValues] = useState({});
  const inputRefs = {};

  const navigate = useNavigate();
  const { user } = useSelector(state => state.auth);

  useEffect(() => {
    document.body.classList.add('dark-theme');
    return () => {
      document.body.classList.remove('dark-theme');
    };
  }, []);
  

  useEffect(() => {
    if (user) {
    fetchBoards();
    }
  }, [user]);

  const fetchBoards = () => {
    http
      .get('/boards/userId=' + user.id)
      .then((response) => {
        setBoards(response.data);
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const updateBoard = (boardId, newName) => {
    http
      .put(`/updateBoard/${boardId}`, { name: newName })
      .then((response) => {
        console.log('Board name updated:', response.data);
        fetchBoards();
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const deleteBoard = (boardId) => {
    http
      .post(`/deleteBoard/${boardId}`)
      .then((response) => {
        console.log('Board deleted: ' + boardId);
        fetchBoards();
      })
      .catch((error) => {
        console.log(error);
      });
  };

  // const getRandomWarmColor = () => {
  //   const hue = Math.floor(Math.random() * 40) + 180; // Тёмный оттенок: 180-220
  //   const saturation = Math.floor(Math.random() * 50) + 50;
  //   const lightness = Math.floor(Math.random() * 20) + 40;
  //   return `hsl(${hue}, ${saturation}%, ${lightness}%)`;
  // };

  const handleBoardChange = (event, boardId) => {
    const newName = event.target.value;
    setInputValues((prevInputValues) => ({
      ...prevInputValues,
      [boardId]: newName,
    }));
  };

  const handleBoardFocus = (boardId) => {
    const inputRef = inputRefs[boardId];
    if (inputRef) {
      const currentValue = inputValues[boardId] || '';
      const cursorPosition = currentValue.length;
      inputRef.setSelectionRange(cursorPosition, cursorPosition);
    }
  };

  const handleBoardBlur = (boardId) => {
    const inputRef = inputRefs[boardId];
    if (inputRef) {
      const newName = inputRef.value;
      setInputValues((prevInputValues) => ({
        ...prevInputValues,
        [boardId]: newName,
      }));
      updateBoard(boardId, newName);
    }
  };

  const handleBoardClick = (boardId) => {
    navigate(`/listPillars/${boardId}`);
  };

  return (
    <div className="dark-theme">
      {user ? (
        <>
      <h2>Список досок</h2>
      <div className="text-center">
        <Link to="/addBoard" className="btn btn-primary mb-2">
          Добавление новой доски
        </Link>
      </div>
      <div className="board-container">
        {boards.map((board) => (
          <div
            key={board.id}
            className="board-card"
            style={{
              backgroundColor: board.color
            }}
          >
            <h3 className="board-name">
              <input
                type="text"
                value={inputValues[board.id] || board.name}
                onChange={(event) => handleBoardChange(event, board.id)}
                onFocus={() => handleBoardFocus(board.id)}
                onBlur={() => handleBoardBlur(board.id)}
                ref={(input) => (inputRefs[board.id] = input)}
                className="board-name-input"
              />
            </h3>
            <button
              className="redirect-button"
              onClick={() => handleBoardClick(board.id)}
            >
              Перейти на доску
            </button>
            <button
              className="delete-button"
              onClick={() => deleteBoard(board.id)}
            >
              Удалить доску
            </button>
          </div>
        ))}
      </div>
      </>
      ) : (
      <p>Контент доступен только авторизованным пользователям.</p>
    )}
    </div>
  );
};


export default ListBoards;

  