package com.collegetour.objectifycars;

import java.io.IOException;
import javax.servlet.http.*;

import com.googlecode.objectify.*;
//import com.googlecode.objectify.Key;
//import com.googlecode.objectify.Objectify;
//import com.googlecode.objectify.ObjectifyService;
//import com.googlecode.objectify.Query;

@SuppressWarnings("serial")
public class ObjectifyCarsServlet extends HttpServlet {

	static {
		ObjectifyService.register(Person.class);
		ObjectifyService.register(Car.class);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		resp.setContentType("text/html");

		String html = "<h1>attempt #1</h1><form>Owner id (blank for new)<input name=\"id\" type=\"text\"> "+
		"Car license <input name=\"license\" type=\"text\"><input type=\"submit\"></form>";
		resp.getWriter().println(html);

		if(req.getParameter("license") == null) {
			return;
		} //if

		if(req.getParameter("license").length() == 0) {
			resp.getWriter().println("License not set");
			return;
		}

		Objectify ofy = ObjectifyService.begin();

		Person owner = new Person();

		if(req.getParameter("id") != null && req.getParameter("id").length() > 0) {
			owner = ofy.find(Person.class, Long.parseLong(req.getParameter("id")));
		} else {
			ofy.put(owner);
		} //if-else

		Key<Person> ownerKey = new Key<Person>(Person.class, owner.getId());
		Car car = new Car(ownerKey);

		car.setLicense(req.getParameter("license"));
		ofy.put(car);

		Query<Car> q = ofy.query(Car.class).ancestor(ownerKey);
		resp.getWriter().println("<pre>Cars for owner id " + owner.getId() + ": ");

		for(Car c : q) {
			resp.getWriter().println(c.getLicense());
		} //for

		//		resp.sendRedirect("/guestbook.jsp");


	}
}
