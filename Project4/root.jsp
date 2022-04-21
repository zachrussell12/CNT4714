<%@ page language="java" contentType="text/html"%>
<%@ page import = "java.util.ResourceBundle" %>

<!DOCTYPE html>

<%
   String firstName = (String) session.getAttribute("firstName");
   String message = (String) session.getAttribute("message");

   String user = (String) session.getAttribute("user");
   String pass = (String) session.getAttribute("pass");

   if (message == null) message = " ";

   ResourceBundle resource = ResourceBundle.getBundle("root");
   String prop1u = resource.getString("username");
   String prop1p = resource.getString("password");

   ResourceBundle resource2 = ResourceBundle.getBundle("client");
   String prop2u  = resource2.getString("username");
   String prop2p  = resource2.getString("password");

   if(user == null){
       if(request.getParameter("username") != null){
           if( ( !request.getParameter("username").equals(prop1u) && !request.getParameter("password").equals(prop1p) ) && ( !request.getParameter("username").equals(prop2u) && !request.getParameter("password").equals(prop2p) ) ){
                response.sendRedirect("./rootHome.jsp");
           }
           user = request.getParameter("username");
       }
       else{
            response.sendRedirect("./rootHome.jsp");
       }
   }
   else{
        if( ( !user.equals(prop1u) && !pass.equals(prop1p) ) && ( !user.equals(prop2u) && !pass.equals(prop2p) ) ){
             response.sendRedirect("./rootHome.jsp");
        }
   }

%>

<html>
    <meta charset=utf-8/>
        <head>
            <title>Root Test</title>
            <style>
                body {background-color: #202124; font-family: monospace; color: white;} .topHeader{color: #f2943d;} .topSection{display: flex; flex-direction: column; align-content: center; align-items: center; color: white;} hr {display: block; margin-before: 0.5em; margin-after: 0.5em; margin-start: auto; margin-end: auto; overflow: hidden; border-style: inset; border-width: 1px;} .inLine{color: red} .queryEntry{display: block; position: relative; align-content: center; align-items: center; width: 40%; margin-left: auto; margin-right: auto; height: 40%;} #buttonContainer{display: flex; flex-direction: row; justify-content: center; width: 100%} .queryButton{margin-inline: 15px; background-color: aquamarine; border-width: 0; font-family: monospace; margin-top: 25px;} .databaseResSection{display: block; margin-top: 75px; text-align: center;}
            </style>
        </head>
        <body>
            <section class="topSection">
                <h1 class="topHeader">Welcome to the Spring 2022 Project 4 Enterprise Database System</h1>
                <h2>A Servlet/JSP-based Multi-tiered Enterprise Application Using a Tomcat Container</h2>
            </section>
            <hr></hr>
            <section class="topSection">
                <h4>You are connected to the Project 4 Enterprise System as a <span class="inLine">${user}-level</span> user.</h4>
                <h4>Please enter any valid SQL query or update below:</h4>
            </section>
            <section class="queryEntry">
                <form action= "/Project4/rootLevel" method="post">
                    <textarea name="sqlStatement" type="text" style="width: 500px; height: 250px; display: flex; margin-left: auto; margin-right: auto;" value= "select * from test;"></textarea>
                    <section id="buttonContainer">
                        <input class="queryButton" name="exec" type="submit" value="Execute Command" style="background-color: #4ac77c"/>
                        <input class="queryButton" name="reset" type="reset" value="Reset" style="background-color: #eb4d4d"/>
                        <input class="queryButton" name="clear" type="submit" value="Clear Results" style="background-color: #ebd04d"/>
                    </section>
                </form>
            </section>
            <section class="databaseResSection">
                <h4>All execution results will appear below this line: </h4>
                <hr></hr>
                <h3>Database Results:</h3>
                <%=message%>
            </section>
        </body>
</html>