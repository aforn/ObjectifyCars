<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="guestbook.Building" %>
<%@ page import="guestbook.PMF" %>

<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>


<html>
  <head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>
  <body>

<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
%>
<p>Hello, <%= user.getNickname() %>! (You can
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%
    } else {
%>
<p>Hello!
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
to include your name with buildings you post.</p>
<%
    }
%>

<%
    PersistenceManager pm = PMF.get().getPersistenceManager();
    String query = "select from " + Building.class.getName();
    List<Building> buildings = (List<Building>) pm.newQuery(query).execute();
    if (buildings.isEmpty()) {
%>
<p>There are no building.</p>
<%
    } else {
        for (Building b : buildings) {
            if (b.getAuthor() == null) {
%>
<p>An anonymous person wrote:</p>
<%
            } else {
%>
<p><b><%= b.getAuthor().getNickname() %></b> wrote:</p>
<%
            }
%>
<blockquote>Building Name: <%= b.getBuildingName() %></blockquote>
<blockquote>Building Number: <%= b.getBuildingNumber() %></blockquote>
<blockquote>Building Directions: <%= b.getBuildingDirections() %></blockquote>
<blockquote>Building Text: <%= b.getBuildingText() %></blockquote>
<blockquote>Date: <%= b.getDate() %></blockquote>
<%
        }
    }
    pm.close();
%>
   <form action="/sign" method="post">
	  <p>Enter the building name:</p>
      <div><textarea name="buildingName" rows="3" cols="10"></textarea></div>
      <p>Enter the building number:</p>
      <div><textarea name="buildingNumber" rows="1" cols="2"></textarea></div>
      <p>Enter the directions to the building:</p>
      <div><textarea name="buildingDirections" rows="3" cols="60"></textarea></div>
      <p>Enter the building text:</p>
      <div><textarea name="buildingText" rows="3" cols="60"></textarea></div>
      <div><input type="submit" value="Post Building" /></div>
    </form>
    <form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
            <p>Enter file for picture 1 associated with this page:</p>
            <input type="text" name="foo">
            <input type="file" name="myFile">
            <input type="submit" value="Submit">
    </form>
    <form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
            <p>Enter file for picture 2 associated with this page:</p>
            <input type="text" name="foo">
            <input type="file" name="myFile">
            <input type="submit" value="Submit">
    </form>
  </body>
</html>