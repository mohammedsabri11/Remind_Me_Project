-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 04 مايو 2024 الساعة 20:06
-- إصدار الخادم: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `remindmedb`
--

-- --------------------------------------------------------

--
-- بنية الجدول `certificate`
--

CREATE TABLE `certificate` (
  `consultantId` int(11) NOT NULL,
  `certificateImage` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- إرجاع أو استيراد بيانات الجدول `certificate`
--

INSERT INTO `certificate` (`consultantId`, `certificateImage`) VALUES
(16, '1708885682348.jpg'),
(20, '1710272546125.jpg');

-- --------------------------------------------------------

--
-- بنية الجدول `consultant`
--

CREATE TABLE `consultant` (
  `consultantId` int(11) NOT NULL,
  `shortDescription` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `speciality` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `experience` int(11) NOT NULL,
  `profile` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'default'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- إرجاع أو استيراد بيانات الجدول `consultant`
--

INSERT INTO `consultant` (`consultantId`, `shortDescription`, `speciality`, `experience`, `profile`) VALUES
(16, 'Specializes in diagnosing and treating nervous system disorders like Alzheimer\'s disease.', 'Neurology', 2, 'null'),
(18, 'Focuses on brain function and behavior, especially in Alzheimer\'s patients.', 'Neuropsychology', 3, 'null'),
(19, 'Expert in caring for older adults, particularly those with Alzheimer\'s disease', 'Geriatric Medicine', 4, 'null'),
(20, 'Specializes in treating mental health issues, including those related to Alzheimer\'s disease.', ' Psychiatry', 3, 'null');

-- --------------------------------------------------------

--
-- بنية الجدول `evaluation`
--

CREATE TABLE `evaluation` (
  `evaluationId` int(11) NOT NULL,
  `consultant_Id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `Rating` int(11) NOT NULL,
  `Review` varchar(255) NOT NULL,
  `Date` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- إرجاع أو استيراد بيانات الجدول `evaluation`
--

INSERT INTO `evaluation` (`evaluationId`, `consultant_Id`, `user_id`, `Rating`, `Review`, `Date`) VALUES
(5, 18, 17, 3, 'this doctor help me more', '18/03/2024');

-- --------------------------------------------------------

--
-- بنية الجدول `game`
--

CREATE TABLE `game` (
  `gameid` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(255) NOT NULL,
  `link` varchar(255) NOT NULL,
  `consultantId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- إرجاع أو استيراد بيانات الجدول `game`
--

INSERT INTO `game` (`gameid`, `name`, `description`, `link`, `consultantId`) VALUES
(2, 'Sudoku game', 'Sudoku game', 'https://www.websudoku.com/', 16),
(3, 'Lumosity', 'this Game can improve your memory', 'https://www.lumosity.com/en/', 18),
(5, 'Happy Neuron', 'Happy Neuron', 'https://www.happy-neuron.com/', 18),
(6, 'Braingle', 'this game can help you toimprove memory work', 'https://www.braingle.com/', 18),
(7, 'Memory lane game', 'Memory lane game', 'https://play.google.com/store/apps/details?id=com.memorylanegames.memorylanegameslite&pcampaignid=web_share\r\n', 20),
(8, 'Cognifit', '', 'https://play.google.com/store/apps/details?id=com.cognifit.app&pcampaignid=web_share\r\n', 20),
(9, 'Let’s create! pottery', 'Manufacturing pottery has never been easier or more enjoyable than Let’s Create! Pottery. Individuals may develop into genuine artists by creating “one-of-a-kind” clay things and sharing them with their friends\r\n\r\n', 'https://play.google.com/store/apps/details?id=pl.idreams.pottery&pcampaignid=web_share', 19),
(10, 'Word search colorful', 'Word search colorful\r\n', 'https://play.google.com/store/apps/details?id=com.wixot.wordsearch&pcampaignid=web_share', 19),
(11, 'puzzle', 'Solve puzzle and match the colors to win ', 'https://play.google.com/store/apps/details?id=com.playrix.homescapes', 16);

-- --------------------------------------------------------

--
-- بنية الجدول `mainuser`
--

CREATE TABLE `mainuser` (
  `muserid` int(11) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `gender` varchar(15) NOT NULL,
  `dob` varchar(15) NOT NULL,
  `mobile` varchar(15) NOT NULL,
  `password` varchar(15) NOT NULL,
  `email` varchar(50) NOT NULL,
  `usertype` varchar(15) NOT NULL,
  `isactive` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- إرجاع أو استيراد بيانات الجدول `mainuser`
--

INSERT INTO `mainuser` (`muserid`, `fullname`, `gender`, `dob`, `mobile`, `password`, `email`, `usertype`, `isactive`) VALUES
(12, '() ', 'admin ', '2024-03-12', '', 'admin', 'admin@admin.com', 'Admin', 1),
(16, 'Raghad Al Hudayb\n', 'Female', '2024-03-12', '0531932233', 'r', 'raghad0@gmail.com', 'Consultant', 1),
(17, 'Hanan Alqahtani ', 'Female', '2024-03-12', '0531932243', 'r', 'hanan@gmail.com', 'User', 1),
(18, 'Abdullah Alasmari \n', 'Male', '2024-03-12', '0531932221', '123', 'abdullah@gmail.com', 'Consultant', 1),
(19, 'faisal Abduallah', 'Male', '2024-03-12', '0534332221', '234', 'faisal@gmail.com', 'Consultant', 1),
(20, 'Saeed Alkhatni', 'Male', '2024-03-12', '0556452221', '123', 'saeed@gmail.com', 'Consultant', 0),
(21, 'Abdualrahaman Aldosary', 'Male', '2024-03-15', '0534332656', '123', 'abdualrahaman@gmail.com', 'User', 1),
(22, 'abduallah alnjrani', '', '2024-04-04', '0542424244', 'UserUser123@', 'moh@gma.com', 'User', 0),
(23, 'Ahammed Abdualrhamman', 'Female', '٢٠٢٤-٠٤-١٩', '0505050505', 'AhammedA11@', 'ad@gmail.com', 'User', 0),
(24, 'رغد عبدالله ', 'Female', '١٩٩٥-٠٤-٢٥', '0531932221', 'RaghadAbha2090@', 'raghad2@gmail.com', 'User', 1);

-- --------------------------------------------------------

--
-- بنية الجدول `query`
--

CREATE TABLE `query` (
  `queryid` int(11) NOT NULL,
  `querydate` varchar(55) NOT NULL,
  `querytext` varchar(255) NOT NULL,
  `queryreply` varchar(255) DEFAULT NULL,
  `replydate` varchar(55) DEFAULT NULL,
  `consultantId` int(11) NOT NULL,
  `userid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- إرجاع أو استيراد بيانات الجدول `query`
--

INSERT INTO `query` (`queryid`, `querydate`, `querytext`, `queryreply`, `replydate`, `consultantId`, `userid`) VALUES
(6, '2024-03-02 02:34:46', 'hi can you help me', 'hello ragah yes i pleaser to help you ,can you provide me with more information about your problem', '2024-03-17 03:03:17', 18, 17),
(15, '2024-03-18 03:51:42', 'Hello', 'hello raghad ,i am please to help you,can you provide me with more details about your problem', '2024-03-18 05:42:47', 16, 17),
(16, '2024-04-25 19:29:33', 'Hi ', NULL, NULL, 16, 24),
(17, '2024-04-25 19:29:37', 'Hi', 'Hello ', '2024-04-25 19:31:25', 16, 24);

-- --------------------------------------------------------

--
-- بنية الجدول `reminder`
--

CREATE TABLE `reminder` (
  `reminderid` int(11) NOT NULL,
  `datetime` datetime NOT NULL,
  `appointmentdetails` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `userid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- إرجاع أو استيراد بيانات الجدول `reminder`
--

INSERT INTO `reminder` (`reminderid`, `datetime`, `appointmentdetails`, `userid`) VALUES
(2, '2024-02-21 23:12:00', 'hi', 17),
(4, '2024-02-28 03:37:00', 'appointment at dela hospital', 17),
(8, '2024-05-30 00:44:00', 'drug ', 17),
(9, '2024-03-23 05:49:00', 'clinic', 17);

-- --------------------------------------------------------

--
-- بنية الجدول `tinfo`
--

CREATE TABLE `tinfo` (
  `infoid` int(11) NOT NULL,
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `instructions` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `target` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- إرجاع أو استيراد بيانات الجدول `tinfo`
--

INSERT INTO `tinfo` (`infoid`, `title`, `instructions`, `target`) VALUES
(5, 'Stay Active', 'Engage in activities that stimulate the mind, such as puzzles, reading, or social interactions.', 1),
(6, 'Follow a Healthy Diet', 'Eat a balanced diet rich in fruits, vegetables, whole grains, and lean proteins to support brain health.', 1),
(7, 'Create a Safe Environment', 'Remove hazards, ensure proper lighting, and use locks or alarms to prevent wandering.', 2),
(8, 'Establish Routine', 'Maintain consistency in daily activities, mealtimes, and sleep schedules to reduce confusion and anxiety.', 2),
(9, 'Provide Support', 'Offer assistance with daily tasks as needed, such as bathing, dressing, and medication management.', 2),
(10, 'Communicate Effectively', 'Use clear, simple language, and provide visual cues to aid understanding.', 2),
(11, 'Practice Self-Care', 'Take breaks, seek support from other caregivers or support groups, and prioritize your own physical and emotional well-being.', 2),
(12, 'Get Sufficient Sleep', 'Ensure you get an adequate amount of sleep each night to support brain function and overall well-being.', 1),
(13, 'Stay Socially Engaged', 'Maintain relationships with family and friends to prevent isolation and maintain mental well-being.', 1),
(14, 'Seek Medical Help', 'Regularly visit healthcare providers for check-ups and discuss any changes in symptoms or concerns.', 1),
(15, 'Educate Yourself', 'Learn as much as you can about Alzheimer\'s disease, its symptoms, progression, and available treatments. Understanding the condition can help you better cope with its challenges.', 1),
(16, 'Seek Support', 'Don\'t hesitate to seek support from family, friends, support groups, or healthcare professionals. Talking to others who understand what you\'re going through can provide comfort and valuable advice.', 1),
(17, 'Plan for the Future', 'As Alzheimer\'s progresses, it\'s essential to plan for the future and make decisions about long-term care.', 1),
(18, 'Stay Positive', 'While Alzheimer\'s disease presents many challenges, focusing on the present moment and cherishing meaningful interactions can help maintain a positive outlook.', 1),
(19, 'Focus on Safety', 'Ensure the home environment is safe and secure by removing tripping hazards, installing handrails, and using locks or alarms on doors and windows. Consider enrolling in a medical alert system for added safety.', 1),
(20, 'Respect Personal Space', 'Keep well-loved objects and photographs around the house to help the person feel more secure. Remind the person who you are if he or she doesn\'t remember, but try not to say, \"Don\'t you remember?\"', 2),
(21, 'Encourage Two-Way Conversation', 'Encourage a two-way conversation for as long as possible. Try distracting the person with an activity, such as a familiar book or photo album, if you are having trouble communicating with words.', 2),
(22, 'Reassure and Listen', 'Reassure the person. Speak calmly. Listen to his or her concerns and frustrations. Try to show that you understand if the person is angry or fearful.', 2),
(23, 'Allow Independence', 'Allow the person to keep as much control in his or her life as possible. Plan activities that the person enjoys and try to do them at the same time each day.', 2),
(24, 'Medication Reminders', 'Consider a system of reminders for helping those who must take medications regularly.', 2),
(25, 'Assistance with Dressing and Bathing', 'When dressing or bathing, allow the person to do as much as possible. Buy loose-fitting, comfortable, easy-to-use clothing. Use a sturdy shower chair to support a person who is unsteady.', 2),
(26, 'Gentle and Respectful Care', 'Be gentle and respectful. Tell the person what you are going to do, step by step, while you help them bathe or get dressed.', 2),
(27, 'Mealtime Support', 'Serve meals in a consistent, familiar place and give the person enough time to eat.', 2),
(28, 'Alzheimer\'s Disease Overview', 'Alzheimer\'s disease (AD) is the most common form of dementia among older people. Dementia is a brain disorder that seriously affects a person\'s ability to carry out daily activities.', 0),
(29, 'Phases of Alzheimer\'s Disease', 'In Alzheimer\'s disease, toxic changes to the brain likely start a decade or more before problems appear. The phases include:\r\n- Early: The first symptoms of Alzheimer\'s can be different in each person. They may include memory problems and nonmemory proble', 0);

-- --------------------------------------------------------

--
-- بنية الجدول `tuser`
--

CREATE TABLE `tuser` (
  `userid` int(11) NOT NULL,
  `careprovidername` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `healtstatusdetails` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- إرجاع أو استيراد بيانات الجدول `tuser`
--

INSERT INTO `tuser` (`userid`, `careprovidername`, `healtstatusdetails`) VALUES
(17, 'Raghad ', 'Mid-stage Alzheimer\'s disease'),
(21, 'kj', 'Early-stage Alzheimer\'s disease'),
(22, 'khaled  ahammed', 'i am in the third phase '),
(23, 'ahammed Ali', 'alzehiemera'),
(24, 'رغد عبدالله', 'Patient');

-- --------------------------------------------------------

--
-- بنية الجدول `user_game`
--

CREATE TABLE `user_game` (
  `id` int(11) NOT NULL,
  `game_id` int(11) NOT NULL,
  `userId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- إرجاع أو استيراد بيانات الجدول `user_game`
--

INSERT INTO `user_game` (`id`, `game_id`, `userId`) VALUES
(2, 2, 17),
(3, 3, 17),
(9, 2, 21);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `certificate`
--
ALTER TABLE `certificate`
  ADD PRIMARY KEY (`consultantId`);

--
-- Indexes for table `consultant`
--
ALTER TABLE `consultant`
  ADD PRIMARY KEY (`consultantId`);

--
-- Indexes for table `evaluation`
--
ALTER TABLE `evaluation`
  ADD PRIMARY KEY (`evaluationId`),
  ADD KEY `cons` (`consultant_Id`),
  ADD KEY `user` (`user_id`);

--
-- Indexes for table `game`
--
ALTER TABLE `game`
  ADD PRIMARY KEY (`gameid`),
  ADD KEY `rel5` (`consultantId`);

--
-- Indexes for table `mainuser`
--
ALTER TABLE `mainuser`
  ADD PRIMARY KEY (`muserid`);

--
-- Indexes for table `query`
--
ALTER TABLE `query`
  ADD PRIMARY KEY (`queryid`),
  ADD KEY `rrt1` (`consultantId`),
  ADD KEY `rrt2` (`userid`);

--
-- Indexes for table `reminder`
--
ALTER TABLE `reminder`
  ADD PRIMARY KEY (`reminderid`),
  ADD KEY `rel6` (`userid`);

--
-- Indexes for table `tinfo`
--
ALTER TABLE `tinfo`
  ADD PRIMARY KEY (`infoid`);

--
-- Indexes for table `tuser`
--
ALTER TABLE `tuser`
  ADD PRIMARY KEY (`userid`);

--
-- Indexes for table `user_game`
--
ALTER TABLE `user_game`
  ADD PRIMARY KEY (`id`),
  ADD KEY `gameid` (`game_id`),
  ADD KEY `userId` (`userId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `certificate`
--
ALTER TABLE `certificate`
  MODIFY `consultantId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `evaluation`
--
ALTER TABLE `evaluation`
  MODIFY `evaluationId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `game`
--
ALTER TABLE `game`
  MODIFY `gameid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `mainuser`
--
ALTER TABLE `mainuser`
  MODIFY `muserid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `query`
--
ALTER TABLE `query`
  MODIFY `queryid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `reminder`
--
ALTER TABLE `reminder`
  MODIFY `reminderid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `tinfo`
--
ALTER TABLE `tinfo`
  MODIFY `infoid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `user_game`
--
ALTER TABLE `user_game`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- قيود الجداول المُلقاة.
--

--
-- قيود الجداول `certificate`
--
ALTER TABLE `certificate`
  ADD CONSTRAINT `forieg` FOREIGN KEY (`consultantId`) REFERENCES `consultant` (`consultantId`);

--
-- قيود الجداول `consultant`
--
ALTER TABLE `consultant`
  ADD CONSTRAINT `rel2` FOREIGN KEY (`consultantId`) REFERENCES `mainuser` (`muserid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- قيود الجداول `evaluation`
--
ALTER TABLE `evaluation`
  ADD CONSTRAINT `cons` FOREIGN KEY (`consultant_Id`) REFERENCES `consultant` (`consultantId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `tuser` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- قيود الجداول `game`
--
ALTER TABLE `game`
  ADD CONSTRAINT `rel5` FOREIGN KEY (`consultantId`) REFERENCES `consultant` (`consultantId`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- قيود الجداول `query`
--
ALTER TABLE `query`
  ADD CONSTRAINT `rrt1` FOREIGN KEY (`consultantId`) REFERENCES `consultant` (`consultantId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `rrt2` FOREIGN KEY (`userid`) REFERENCES `tuser` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- قيود الجداول `reminder`
--
ALTER TABLE `reminder`
  ADD CONSTRAINT `rel6` FOREIGN KEY (`userid`) REFERENCES `tuser` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- قيود الجداول `tuser`
--
ALTER TABLE `tuser`
  ADD CONSTRAINT `rel3` FOREIGN KEY (`userid`) REFERENCES `mainuser` (`muserid`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- قيود الجداول `user_game`
--
ALTER TABLE `user_game`
  ADD CONSTRAINT `gameid` FOREIGN KEY (`game_id`) REFERENCES `game` (`gameid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `tuser` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
