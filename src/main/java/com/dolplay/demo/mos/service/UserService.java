package com.dolplay.demo.mos.service;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.kernel.Traversal;
import org.nutz.ioc.loader.annotation.IocBean;

import com.dolplay.demo.mos.domain.Friend;
import com.dolplay.demo.mos.domain.User;
import com.dolplay.demo.mos.util.Neo4jDB;
import com.dolplay.demo.mos.util.Rel;

@IocBean
public class UserService {

	public User view(long id) {
		User user = new User();
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(Neo4jDB.DB_PATH);
		if (id != 0) {
			Node node = graphDb.getNodeById(id);
			user.setId(id);
			user.setName((String) node.getProperty(Neo4jDB.NAME_KEY));
			user.setGender((String) node.getProperty(Neo4jDB.GENDER_KEY));
			user.setProfession((String) node.getProperty(Neo4jDB.PROFESSION_KEY));
			user.setAge((Integer) node.getProperty(Neo4jDB.AGE_KEY));
		}
		graphDb.shutdown();
		return user;
	}

	public List<Friend> listFriends(int id) {
		List<Friend> list = new ArrayList<Friend>();
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(Neo4jDB.DB_PATH);
		if (id != 0) {
			Node foundUser = graphDb.getNodeById(id);
			Traverser friendsTraverser = getFriends(foundUser);
			for (Path friendPath : friendsTraverser) {
				int depth = friendPath.length();
				Node foundfriend = friendPath.endNode();
				Friend friend = new Friend();
				friend.setId(foundfriend.getId());
				friend.setName((String) foundfriend.getProperty(Neo4jDB.NAME_KEY));
				friend.setGender((String) foundfriend.getProperty(Neo4jDB.GENDER_KEY));
				friend.setProfession((String) foundfriend.getProperty(Neo4jDB.PROFESSION_KEY));
				friend.setAge((Integer) foundfriend.getProperty(Neo4jDB.AGE_KEY));
				friend.setDepth(depth);
				list.add(friend);
			}
		}
		graphDb.shutdown();
		return list;
	}

	public List<User> findShortestFriendPath(long startId, long endId) {
		List<User> friends = new ArrayList<User>();
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(Neo4jDB.DB_PATH);
		if (startId == 0 || endId == 0) {
			return friends;
		}
		Node startNode = graphDb.getNodeById(startId);
		Node endNode = graphDb.getNodeById(endId);
		Path shortestPath = findShortestFriendPath(startNode, endNode).iterator().next();
		for (Node node : shortestPath.nodes()) {
			User user = new User();
			user.setId(node.getId());
			user.setName((String) node.getProperty(Neo4jDB.NAME_KEY));
			user.setGender((String) node.getProperty(Neo4jDB.GENDER_KEY));
			user.setProfession((String) node.getProperty(Neo4jDB.PROFESSION_KEY));
			user.setAge((Integer) node.getProperty(Neo4jDB.AGE_KEY));
			friends.add(user);
		}
		graphDb.shutdown();
		return friends;
	}

	private static Traverser getFriends(final Node person) {
		TraversalDescription td = Traversal.description().breadthFirst().relationships(Rel.KNOWS, Direction.OUTGOING)
				.evaluator(Evaluators.excludeStartPosition());
		return td.traverse(person);
	}

	public static Iterable<Path> findShortestFriendPath(Node node1, Node node2) {
		PathFinder<Path> finder = GraphAlgoFactory.shortestPath(
				Traversal.expanderForTypes(Rel.KNOWS, Direction.OUTGOING), 6);
		Iterable<Path> paths = finder.findAllPaths(node1, node2);
		return paths;
	}
}
