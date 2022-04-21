<%@ page language="java" contentType="text/html"%>
    
<!--
Name: Zachary Russell
Course: CNT 4714 Spring 2022
Assignment title: Project Four: Developing A Three-Tier Distributed Web-Based Application
Date: April 21, 2022
Class: Section: 0001
-->

<!DOCTYPE html>

<html lang="en">
    <head>
        <title>System Login</title>
        <meta charset="UTF-8">
        <style>
            body{background-color: #202124; font-family: monospace; color: white;}
            form{background-color: #303134; display: flex; flex-direction: column; align-content: center; align-items: center; width: 20%; margin-left: auto; margin-right: auto;}
            .submit{background-color: aquamarine; border-width: 0; font-family: monospace;}
        </style>
    </head>
    <body>
        
        <form action="./rootLevel" method="post">
            <p>
                <label>Please Login:</label><br>
                <label>Username: 
                    <input type="text" name="username"/>
                </label><br>
                <label>Password:
                    <input type="password" name="password"/>
                </label><br>
                <input class="submit" type="submit" name="Submit"/>
            </p>
        </form>
        
    </body>
</html>