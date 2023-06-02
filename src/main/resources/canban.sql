-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Май 24 2023 г., 15:54
-- Версия сервера: 10.4.25-MariaDB
-- Версия PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `canban`
--

-- --------------------------------------------------------

--
-- Структура таблицы `auto_user`
--

CREATE TABLE `auto_user` (
  `id` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `auto_user`
--

INSERT INTO `auto_user` (`id`, `username`, `mail`, `password`, `role`) VALUES
(11, 'admin10', 'admin10@mail.ru', '$2a$10$W9H.UiF4kEeWjapXtK5wrOyxcadJlu4Q.0nHFlToxIH/wV/C7Rw7e', 'ROLE_ADMIN'),
(12, 'admin11', 'admin11@mail.ru', '$2a$10$Oj6klQwYDpFT/iVp1kViSegz5F2n8wB0.n4acunpIfLlXWd24tUee', 'ROLE_ADMIN'),
(13, 'user1', 'user1@mail.ru', '$2a$10$8TUXOYlk4rCb78sxxMxsA.wigH9gt/HN2/28WwjD0asHbYJ5PqJza', 'ROLE_ADMIN'),
(14, 'user2', 'user2@mail.ru', '$2a$10$AMw4.8NR5Sk39bbGnuCGy.5VcHzr2IgILrK8JCeYzl.E69q/cajAO', 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Структура таблицы `auto_user_board`
--

CREATE TABLE `auto_user_board` (
  `id` int(11) NOT NULL,
  `auto_user_id` int(11) NOT NULL,
  `board_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `auto_user_board`
--

INSERT INTO `auto_user_board` (`id`, `auto_user_id`, `board_id`) VALUES
(97, 11, 106),
(98, 12, 106),
(100, 13, 108),
(102, 14, 108);

-- --------------------------------------------------------

--
-- Структура таблицы `board`
--

CREATE TABLE `board` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `board`
--

INSERT INTO `board` (`id`, `name`) VALUES
(106, 'board1d'),
(108, 'board2');

-- --------------------------------------------------------

--
-- Структура таблицы `card`
--

CREATE TABLE `card` (
  `id` int(11) NOT NULL,
  `pillar_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `description` varchar(100) NOT NULL,
  `card_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `card`
--

INSERT INTO `card` (`id`, `pillar_id`, `name`, `description`, `card_date`) VALUES
(70, 36, 'card2', 'desc1', '2023-05-13'),
(75, 37, 'asd', 'asd', '2023-05-23'),
(79, 47, 'card2', 'desc2', '2023-05-26'),
(80, 45, 'card5', 'desc5', '2023-05-25'),
(81, 47, 'card6', 'desc6', '2023-05-04');

-- --------------------------------------------------------

--
-- Структура таблицы `card_label`
--

CREATE TABLE `card_label` (
  `id` int(11) NOT NULL,
  `card_id` int(11) NOT NULL,
  `label_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `card_label`
--

INSERT INTO `card_label` (`id`, `card_id`, `label_id`) VALUES
(37, 70, 23),
(41, 75, 24),
(43, 75, 23),
(44, 75, 27),
(50, 70, 24),
(51, 70, 27),
(53, 80, 33);

-- --------------------------------------------------------

--
-- Структура таблицы `label`
--

CREATE TABLE `label` (
  `id` int(11) NOT NULL,
  `board_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `label`
--

INSERT INTO `label` (`id`, `board_id`, `name`) VALUES
(23, 106, 'label2'),
(24, 106, 'label3'),
(27, 106, 'label5'),
(32, 108, 'label2'),
(33, 108, 'label3');

-- --------------------------------------------------------

--
-- Структура таблицы `pillar`
--

CREATE TABLE `pillar` (
  `id` int(11) NOT NULL,
  `board_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `pillar`
--

INSERT INTO `pillar` (`id`, `board_id`, `name`) VALUES
(35, 106, 'ToDo'),
(36, 106, 'InProgress'),
(37, 106, 'Done'),
(45, 108, 'column2'),
(47, 108, 'column4');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `auto_user`
--
ALTER TABLE `auto_user`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `auto_user_board`
--
ALTER TABLE `auto_user_board`
  ADD PRIMARY KEY (`id`),
  ADD KEY `board_id` (`board_id`),
  ADD KEY `FKoip37os4gvf0r0n6wicq9h8n4` (`auto_user_id`);

--
-- Индексы таблицы `board`
--
ALTER TABLE `board`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `card`
--
ALTER TABLE `card`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pillar_id` (`pillar_id`);

--
-- Индексы таблицы `card_label`
--
ALTER TABLE `card_label`
  ADD PRIMARY KEY (`id`),
  ADD KEY `card_id` (`card_id`),
  ADD KEY `label_id` (`label_id`);

--
-- Индексы таблицы `label`
--
ALTER TABLE `label`
  ADD PRIMARY KEY (`id`),
  ADD KEY `board_id` (`board_id`);

--
-- Индексы таблицы `pillar`
--
ALTER TABLE `pillar`
  ADD PRIMARY KEY (`id`),
  ADD KEY `board_id` (`board_id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `auto_user`
--
ALTER TABLE `auto_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT для таблицы `auto_user_board`
--
ALTER TABLE `auto_user_board`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=103;

--
-- AUTO_INCREMENT для таблицы `board`
--
ALTER TABLE `board`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=110;

--
-- AUTO_INCREMENT для таблицы `card`
--
ALTER TABLE `card`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=82;

--
-- AUTO_INCREMENT для таблицы `card_label`
--
ALTER TABLE `card_label`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT для таблицы `label`
--
ALTER TABLE `label`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT для таблицы `pillar`
--
ALTER TABLE `pillar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `auto_user_board`
--
ALTER TABLE `auto_user_board`
  ADD CONSTRAINT `auto_user_board_ibfk_1` FOREIGN KEY (`auto_user_id`) REFERENCES `auto_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `auto_user_board_ibfk_2` FOREIGN KEY (`board_id`) REFERENCES `board` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `card`
--
ALTER TABLE `card`
  ADD CONSTRAINT `card_ibfk_1` FOREIGN KEY (`pillar_id`) REFERENCES `pillar` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `card_label`
--
ALTER TABLE `card_label`
  ADD CONSTRAINT `card_label_ibfk_1` FOREIGN KEY (`card_id`) REFERENCES `card` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `card_label_ibfk_2` FOREIGN KEY (`label_id`) REFERENCES `label` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `label`
--
ALTER TABLE `label`
  ADD CONSTRAINT `label_ibfk_1` FOREIGN KEY (`board_id`) REFERENCES `board` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `pillar`
--
ALTER TABLE `pillar`
  ADD CONSTRAINT `pillar_ibfk_1` FOREIGN KEY (`board_id`) REFERENCES `board` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
