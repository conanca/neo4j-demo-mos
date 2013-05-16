package com.dolplay.demo.mos.service;

import java.util.ArrayList;
import java.util.List;

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
import com.dolplay.demo.mos.util.Neo4jDB;
import com.dolplay.demo.mos.util.Rel;

@IocBean
public class UserService {

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

	private static Traverser getFriends(final Node person) {
		TraversalDescription td = Traversal.description().breadthFirst().relationships(Rel.KNOWS, Direction.OUTGOING)
				.evaluator(Evaluators.excludeStartPosition());
		return td.traverse(person);
	}
}
