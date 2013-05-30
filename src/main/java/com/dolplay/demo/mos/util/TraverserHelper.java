package com.dolplay.demo.mos.util;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TraverserHelper {
	private static Logger logger = LoggerFactory.getLogger(TraverserHelper.class);

	/**
	 * 遍历指定节点的所有认识的人(任意深度)
	 * @param person
	 * @return
	 */
	public static Traverser getFriends(Node person) {
		TraversalDescription td = Traversal.description().breadthFirst().relationships(Rel.KNOWS, Direction.OUTGOING)
				.evaluator(Evaluators.excludeStartPosition());
		return td.traverse(person);
	}

	/**
	 * 找到两个节点间的最短路径
	 * @param node1
	 * @param node2
	 * @return
	 */
	public static Path findShortestFriendPath(Node node1, Node node2) {
		PathFinder<Path> finder = GraphAlgoFactory.shortestPath(
				Traversal.expanderForTypes(Rel.KNOWS, Direction.OUTGOING), 6);
		Iterable<Path> paths = finder.findAllPaths(node1, node2);
		// 最短路径只有一个path，即取第一个
		return paths.iterator().next();
	}

	/**
	 * 查找所有的单位（用根节点的org关系查找）
	 * @param refeNode
	 * @return
	 */
	public static Traverser findAllOrg1(Node refeNode) {
		TraversalDescription td = Traversal.description().breadthFirst().relationships(RefeRel.ORG, Direction.INCOMING)
				.evaluator(Evaluators.excludeStartPosition());
		return td.traverse(refeNode);
	}

	/**
	 * 查找所有的单位（用根节点找到主角节点，再查找主角及主角认识的人其所属单位）
	 * @param refeNode
	 * @return
	 */
	public static Traverser findAllOrg2(Node refeNode) {
		TraversalDescription td = Traversal.description().depthFirst().relationships(RefeRel.LEAD, Direction.INCOMING)
				.relationships(Rel.KNOWS, Direction.OUTGOING).relationships(Rel.MEMBER_OF, Direction.OUTGOING)
				.evaluator(Evaluators.includeWhereLastRelationshipTypeIs(Rel.MEMBER_OF));
		return td.traverse(refeNode);
	}

	/**
	 *  查找所有有单位的人
	 * @param refeNode
	 * @return
	 */
	public static Traverser findAllOrgUser(Node refeNode) {
		TraversalDescription td = Traversal.description().breadthFirst().relationships(RefeRel.ORG, Direction.INCOMING)
				.relationships(Rel.MEMBER_OF, Direction.INCOMING)
				.evaluator(Evaluators.includeWhereLastRelationshipTypeIs(Rel.MEMBER_OF));
		return td.traverse(refeNode);
	}

	/**
	 * 查找所有单位的BOSS
	 * @param refeNode
	 * @return
	 */
	public static Traverser findAllBoss(Node refeNode) {
		TraversalDescription td = Traversal.description().breadthFirst().relationships(RefeRel.ORG, Direction.INCOMING)
				.relationships(Rel.ADMIN, Direction.INCOMING)
				.evaluator(Evaluators.includeWhereLastRelationshipTypeIs(Rel.ADMIN));
		return td.traverse(refeNode);
	}

	/**
	 * 查找某人同事
	 * @param person
	 * @return
	 */
	public static Traverser findColleague(Node person) {
		TraversalDescription td = Traversal.description().breadthFirst()
				.relationships(Rel.MEMBER_OF, Direction.OUTGOING).relationships(Rel.MEMBER_OF, Direction.INCOMING)
				.evaluator(Evaluators.fromDepth(2));
		return td.traverse(person);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(Name.DB_PATH);
		Node refeNode = graphDb.getReferenceNode();

		// 查找所有单位
		logger.debug("==查找所有单位：");
		Traverser orgTraverser = TraverserHelper.findAllOrg2(refeNode);
		for (Path orgPath : orgTraverser) {
			logger.debug(orgPath.endNode().getProperty(Name.NAME_KEY).toString());
		}

		// 遍历某人的所有认识的人
		logger.debug("==遍历某人的所有认识的人：");
		Node xNode = graphDb.getNodeById(1);
		Traverser userTraverser = TraverserHelper.getFriends(xNode);
		for (Path userPath : userTraverser) {
			String chain = "";
			for (Node node : userPath.nodes()) {
				chain += node.getProperty(Name.NAME_KEY).toString() + " → ";
			}
			logger.debug(userPath.endNode().getProperty(Name.NAME_KEY).toString() + "—深度为：" + userPath.length() + "("
					+ chain.substring(0, chain.length() - 2) + ")");
		}

		// 两个人之间的最短路径
		logger.debug("==两个人之间的最短路径（其实就是查找俩人之间的人脉关系）：");
		Node startNode = graphDb.getNodeById(8);
		Node endNode = graphDb.getNodeById(12);
		Path shortestPath = TraverserHelper.findShortestFriendPath(startNode, endNode);
		String chain = "";
		for (Node node : shortestPath.nodes()) {
			chain += node.getProperty(Name.NAME_KEY).toString() + " → ";
		}
		logger.debug(chain.substring(0, chain.length() - 2));

		// 查找所有有单位的人
		logger.debug("==查找所有有单位的人：");
		Traverser orgUserTraverser = TraverserHelper.findAllOrgUser(refeNode);
		for (Path orgUserPath : orgUserTraverser) {
			logger.debug(orgUserPath.endNode().getProperty(Name.NAME_KEY).toString());
		}

		// 查找所有的BOSS
		logger.debug("==查找所有的BOSS：");
		Traverser bossTraverser = TraverserHelper.findAllBoss(refeNode);
		for (Path bossPath : bossTraverser) {
			logger.debug(bossPath.endNode().getProperty(Name.NAME_KEY).toString());
		}

		// 查找某人的所有同事
		logger.debug("==查找某人的所有同事：");
		Traverser colleagueTraverser = TraverserHelper.findColleague(xNode);
		for (Path colleaguePath : colleagueTraverser) {
			logger.debug(colleaguePath.endNode().getProperty(Name.NAME_KEY).toString());
		}

		graphDb.shutdown();
	}
}
