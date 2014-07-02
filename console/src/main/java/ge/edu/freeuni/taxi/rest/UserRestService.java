package ge.edu.freeuni.taxi.rest;

import java.util.List;

import ge.edu.freeuni.taxi.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class UserRestService {
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addUser(User user) {
		System.out.println("add User");
	}
	
	@DELETE
	@Path("/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteUser( @PathParam("userId") String userId) {
		System.out.println("delete user");
	}
	
	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser( @PathParam("userId") String userId) {
		System.out.println("get User");
		return null;
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		System.out.println("get all Users");
		return null;
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(User user) {
		System.out.println("update user");
	}
}
