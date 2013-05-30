package com.dolplay.demo.mos.service;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Traverser;
import org.nutz.ioc.loader.annotation.IocBean;

import com.dolplay.demo.mos.domain.Friend;
import com.dolplay.demo.mos.domain.User;
import com.dolplay.demo.mos.util.Name;
import com.dolplay.demo.mos.util.TraverserHelper;

@IocBean
public class UserService {

	public User view(long id) {
		User user = new User();
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(Name.DB_PATH);
		if (id != 0) {
			Node node = graphDb.getNodeById(id);
			user.setId(id);
			user.setName((String) node.getProperty(Name.NAME_KEY));
			user.setGender((String) node.getProperty(Name.GENDER_KEY));
			user.setProfession((String) node.getProperty(Name.PROFESSION_KEY));
			user.setAge((Integer) node.getProperty(Name.AGE_KEY));
		}
		graphDb.shutdown();
		return user;
	}

	public List<Friend> listFriends(int id) {
		List<Friend> list = new ArrayList<Friend>();
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(Name.DB_PATH);
		if (id != 0) {
			Node foundUser = graphDb.getNodeById(id);
			Traverser friendsTraverser = TraverserHelper.getFriends(foundUser);
			for (Path friendPath : friendsTraverser) {
				int depth = friendPath.length();
				Node foundfriend = friendPath.endNode();
				Friend friend = new Friend();
				friend.setId(foundfriend.getId());
				friend.setName((String) foundfriend.getProperty(Name.NAME_KEY));
				friend.setGender((String) foundfriend.getProperty(Name.GENDER_KEY));
				friend.setProfession((String) foundfriend.getProperty(Name.PROFESSION_KEY));
				friend.setAge((Integer) foundfriend.getProperty(Name.AGE_KEY));
				friend.setDepth(depth);
				list.add(friend);
			}
		}
		graphDb.shutdown();
		return list;
	}

	public List<User> findShortestFriendPath(long startId, long endId) {
		List<User> friends = new ArrayList<User>();
		if (startId == 0 || endId == 0) {
			return friends;
		}
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(Name.DB_PATH);
		Node startNode = graphDb.getNodeById(startId);
		Node endNode = graphDb.getNodeById(endId);
		Path shortestPath = TraverserHelper.findShortestFriendPath(startNode, endNode);
		for (Node node : shortestPath.nodes()) {
			User user = new User();
			user.setId(node.getId());
			user.setName((String) node.getProperty(Name.NAME_KEY));
			user.setGender((String) node.getProperty(Name.GENDER_KEY));
			user.setProfession((String) node.getProperty(Name.PROFESSION_KEY));
			user.setAge((Integer) node.getProperty(Name.AGE_KEY));
			friends.add(user);
		}
		graphDb.shutdown();
		return friends;
	}

}
