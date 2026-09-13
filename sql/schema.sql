-- Cyber Crime Management System Database Schema
CREATE DATABASE IF NOT EXISTS cybercrime_db;
USE cybercrime_db;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'User',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crime cases table
CREATE TABLE IF NOT EXISTS crime_cases (
    id INT AUTO_INCREMENT PRIMARY KEY,
    case_id VARCHAR(50) NOT NULL UNIQUE,
    crime_type VARCHAR(100) NOT NULL,
    description TEXT,
    incident_date VARCHAR(50),
    status VARCHAR(50) DEFAULT 'Open',
    reported_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reported_by) REFERENCES users(id) ON DELETE SET NULL
);
