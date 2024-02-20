# Task Management System

Manage your tasks efficiently with this simple task management system.

## Introduction

This project is a task management system developed using Spring Boot. It allows users to organize their tasks based on different categories and provides features for task completion and overdue task reminders.

## Features

- Create, update, and delete tasks
- Categorize tasks into different types (e.g., default, personal, shopping, work)
- Mark tasks as completed
- Receive email reminders for overdue tasks
  

## Demo

![Screenshot](https://ibb.co/YLyfKn1)
![Screenshot](https://ibb.co/6XFRfS1)
![Screenshot](https://ibb.co/rc2ZCtZ)
![Screenshot](https://ibb.co/4JL0vhK)
![Screenshot](https://ibb.co/y4GQLqV)
![Screenshot](https://ibb.co/9q3DFdz)


## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/your-repository.git
2. Navigate to project directory:
   cd your-repository

## Usage
1. Ensure you have java and maven installed in your system.
2. Email Configuration:Email Configuration
  spring.mail.host=smtp.example.com
  spring.mail.port=587
  spring.mail.username=your-email@example.com
  spring.mail.password=your-email-password
  Replace the placeholder values with your SMTP server host, port, email address, and password. This configuration is required to send email reminders for overdue tasks.
3. Add your mysql details in application.properties file
4.  Build the project using maven:
   mvn clean install
5. To run type 'mvnw spring-boot:run' in terminal.

## Technologies Used
Spring Boot
Thymeleaf
JavaMailSender
