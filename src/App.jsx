import React from 'react';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.css';

import Header from './layout/Header'
import ListBoards from './pages/ListBoards';
import AddBoard from './pages/AddBoard';

import ListPillars from './pages/ListPillars';
import Card from './pages/Card';
import AddCard from './pages/AddCard';
import AddPillar from './pages/AddPillar';
import AddUser from './pages/AddUser';

import Login from "./components/authorization/Login";
import Register from "./components/authorization/Register";
import Profile from "./components/authorization/Profile";
import { connect } from "react-redux";

function App() {
  return (
    <div>
        <BrowserRouter>
          <Header />
            <Routes>              
              <Route path='/myBoards' element={<ListBoards/>} />
              <Route path='/addBoard' element={<AddBoard/>}/>
              <Route path="/listPillars/:boardId" element={<ListPillars/>} />
              <Route path="/card/:cardId" element={<Card/>} />
              <Route path="/addcard/:pillarId/boardId/:boardId" element={<AddCard/>} />
              <Route path="/addpillar/:boardId" element={<AddPillar/>} />
              <Route path="/addUserOnBoard/:boardId" element={<AddUser/>} />                 
              <Route path="/login" element={<Login/>} />
              <Route path="/register" element={<Register/>} />
              <Route path="/profile" element={<Profile/>} />
            </Routes>
        </BrowserRouter>
    </div>
  );
}

// функциональность Redux: позволяет передать на перенаправляемые страницы данные
function mapStateToProps(state) {
  const { user } = state.auth;
  return {
      user
  };
}

// передача данных к другим компонентам
export default connect(mapStateToProps)(App);
