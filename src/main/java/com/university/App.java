package com.university;

/**
 * University Management System
 *
 * This is a Spring MVC web application packaged as a WAR.
 * It is bootstrapped via {@link com.university.config.WebAppInitializer}
 * and deployed to a Servlet container (e.g. Apache Tomcat 10+).
 *
 * Architecture:
 *   Controller Layer  → com.university.controller
 *   Service Layer     → com.university.service
 *   DAO/Repository    → com.university.dao
 *   Model/Entities    → com.university.model
 *   Configuration     → com.university.config
 *
 * Database: MySQL (university_db) — see src/main/resources/schema.sql
 * Views: Thymeleaf templates in src/main/resources/templates/
 */
public class App {
    // Entry point not required for WAR deployment.
    // Application is started by the Servlet container via WebAppInitializer.
}
