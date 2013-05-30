package com.dolplay.demo.mos.util;

import org.neo4j.graphdb.RelationshipType;

public enum Rel implements RelationshipType {
	EMPLOYS, MEMBER_OF, KNOWS, FOLLOWS, ADMIN
}
