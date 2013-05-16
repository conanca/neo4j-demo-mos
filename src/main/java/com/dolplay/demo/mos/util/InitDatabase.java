package com.dolplay.demo.mos.util;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitDatabase {
	private static Logger logger = LoggerFactory.getLogger(InitDatabase.class);

	private static GraphDatabaseService graphDb;
	private static Index<Node> userIndex;
	private static Index<Node> orgIndex;

	public static void init() {
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(Neo4jDB.DB_PATH);
		userIndex = graphDb.index().forNodes(Neo4jDB.USER_INDEX);
		orgIndex = graphDb.index().forNodes(Neo4jDB.ORG_INDEX);

		Transaction tx = graphDb.beginTx();
		try {

			// 创建主角用户
			logger.debug("创建主角用户");
			Node xiangyu = createIndexUser("佟湘玉", 30, "女", "掌柜");
			Node zhantang = createIndexUser("白展堂", 28, "男", "跑堂");
			Node xiaoguo = createIndexUser("郭芙蓉", 20, "女", "杂役");
			Node xiucai = createIndexUser("吕轻侯", 21, "男", "账房");
			Node wushuang = createIndexUser("祝无双", 19, "女", "捕快");
			Node dazui = createIndexUser("李大嘴", 25, "男", "厨子");
			Node xiaobei = createIndexUser("莫小贝", 8, "女", "掌门");

			// 建立主角关系
			logger.debug("建立主角关系");
			xiangyu.createRelationshipTo(zhantang, Rel.KNOWS);
			xiangyu.createRelationshipTo(xiaoguo, Rel.KNOWS);
			xiangyu.createRelationshipTo(xiucai, Rel.KNOWS);
			xiangyu.createRelationshipTo(wushuang, Rel.KNOWS);
			xiangyu.createRelationshipTo(dazui, Rel.KNOWS);
			xiangyu.createRelationshipTo(xiaobei, Rel.KNOWS);

			logger.debug("~~~~~~~~~~~~~~~~~~~~~" + xiangyu.getId());

			zhantang.createRelationshipTo(xiangyu, Rel.KNOWS);
			zhantang.createRelationshipTo(xiaoguo, Rel.KNOWS);
			zhantang.createRelationshipTo(xiucai, Rel.KNOWS);
			zhantang.createRelationshipTo(wushuang, Rel.KNOWS);
			zhantang.createRelationshipTo(dazui, Rel.KNOWS);
			zhantang.createRelationshipTo(xiaobei, Rel.KNOWS);

			xiucai.createRelationshipTo(zhantang, Rel.KNOWS);
			xiucai.createRelationshipTo(xiaoguo, Rel.KNOWS);
			xiucai.createRelationshipTo(xiangyu, Rel.KNOWS);
			xiucai.createRelationshipTo(wushuang, Rel.KNOWS);
			xiucai.createRelationshipTo(dazui, Rel.KNOWS);
			xiucai.createRelationshipTo(xiaobei, Rel.KNOWS);

			xiaoguo.createRelationshipTo(zhantang, Rel.KNOWS);
			xiaoguo.createRelationshipTo(xiangyu, Rel.KNOWS);
			xiaoguo.createRelationshipTo(xiucai, Rel.KNOWS);
			xiaoguo.createRelationshipTo(wushuang, Rel.KNOWS);
			xiaoguo.createRelationshipTo(dazui, Rel.KNOWS);
			xiaoguo.createRelationshipTo(xiaobei, Rel.KNOWS);

			wushuang.createRelationshipTo(zhantang, Rel.KNOWS);
			wushuang.createRelationshipTo(xiaoguo, Rel.KNOWS);
			wushuang.createRelationshipTo(xiucai, Rel.KNOWS);
			wushuang.createRelationshipTo(xiangyu, Rel.KNOWS);
			wushuang.createRelationshipTo(dazui, Rel.KNOWS);
			wushuang.createRelationshipTo(xiaobei, Rel.KNOWS);

			dazui.createRelationshipTo(zhantang, Rel.KNOWS);
			dazui.createRelationshipTo(xiaoguo, Rel.KNOWS);
			dazui.createRelationshipTo(xiucai, Rel.KNOWS);
			dazui.createRelationshipTo(wushuang, Rel.KNOWS);
			dazui.createRelationshipTo(xiangyu, Rel.KNOWS);
			dazui.createRelationshipTo(xiaobei, Rel.KNOWS);

			xiaobei.createRelationshipTo(zhantang, Rel.KNOWS);
			xiaobei.createRelationshipTo(xiaoguo, Rel.KNOWS);
			xiaobei.createRelationshipTo(xiucai, Rel.KNOWS);
			xiaobei.createRelationshipTo(wushuang, Rel.KNOWS);
			xiaobei.createRelationshipTo(dazui, Rel.KNOWS);
			xiaobei.createRelationshipTo(xiangyu, Rel.KNOWS);

			// 创建配角用户
			logger.debug("创建配角用户");
			Node laoxing = createIndexUser("邢捕头", 35, "男", "捕头");
			Node xiaoliu = createIndexUser("燕小六", 18, "男", "捕头");
			Node laoye = createIndexUser("七舅姥爷", 68, "男", "");
			Node huilan = createIndexUser("杨蕙兰", 23, "女", "");
			Node duzijun = createIndexUser("杜子俊", 42, "男", "财主");
			Node jiwuming = createIndexUser("姬无命", 30, "男", "盗贼");
			Node duanzhi = createIndexUser("断指轩辕", 56, "女", "");
			Node xiaoqing = createIndexUser("小青", 21, "女", "丫鬟");
			Node tongboda = createIndexUser("佟伯达", 58, "男", "掌柜");
			Node xiansheng = createIndexUser("朱先生", 56, "男", "先生");
			Node hanjuan = createIndexUser("韩娟", 29, "女", "掌门夫人");
			Node laohe = createIndexUser("老何", 52, "男", "掌门");

			// 建立配角关系
			logger.debug("建立配角关系");
			wushuang.createRelationshipTo(xiaoliu, Rel.KNOWS);
			xiaoliu.createRelationshipTo(wushuang, Rel.KNOWS);
			xiaoliu.createRelationshipTo(laoxing, Rel.KNOWS);
			laoxing.createRelationshipTo(xiaoliu, Rel.KNOWS);
			xiaoliu.createRelationshipTo(laoye, Rel.KNOWS);
			laoye.createRelationshipTo(xiaoliu, Rel.KNOWS);
			dazui.createRelationshipTo(huilan, Rel.KNOWS);
			huilan.createRelationshipTo(dazui, Rel.KNOWS);
			huilan.createRelationshipTo(duzijun, Rel.KNOWS);
			duzijun.createRelationshipTo(huilan, Rel.KNOWS);
			zhantang.createRelationshipTo(jiwuming, Rel.KNOWS);
			jiwuming.createRelationshipTo(zhantang, Rel.KNOWS);
			dazui.createRelationshipTo(duanzhi, Rel.KNOWS);
			duanzhi.createRelationshipTo(dazui, Rel.KNOWS);
			xiaoguo.createRelationshipTo(xiaoqing, Rel.KNOWS);
			xiaoqing.createRelationshipTo(xiaoguo, Rel.KNOWS);
			xiangyu.createRelationshipTo(tongboda, Rel.KNOWS);
			tongboda.createRelationshipTo(xiangyu, Rel.KNOWS);
			xiaobei.createRelationshipTo(xiansheng, Rel.KNOWS);
			xiansheng.createRelationshipTo(xiaobei, Rel.KNOWS);
			xiangyu.createRelationshipTo(hanjuan, Rel.KNOWS);
			hanjuan.createRelationshipTo(xiangyu, Rel.KNOWS);
			hanjuan.createRelationshipTo(laohe, Rel.KNOWS);
			laohe.createRelationshipTo(hanjuan, Rel.KNOWS);

			// 创建单位(公司/组织/机构)
			logger.debug("创建单位");
			Node tongfu = createIndexOrg("同福客栈", "");
			Node hengshan = createIndexOrg("衡山派", "");
			Node qixiazhen = createIndexOrg("七侠镇衙门", "");
			Node shibalipu = createIndexOrg("十八里铺衙门", "");
			Node longmen = createIndexOrg("龙门镖局", "");
			Node baima = createIndexOrg("白马书院", "");
			Node kunlun = createIndexOrg("昆仑派", "");

			// 建立单位(公司/组织/机构)关系
			logger.debug("建立单位关系");
			tongfu.createRelationshipTo(xiangyu, Rel.EMPLOYS);
			xiangyu.createRelationshipTo(tongfu, Rel.EMPLOYED);
			tongfu.createRelationshipTo(zhantang, Rel.EMPLOYS);
			zhantang.createRelationshipTo(tongfu, Rel.EMPLOYED);
			tongfu.createRelationshipTo(xiaoguo, Rel.EMPLOYS);
			xiaoguo.createRelationshipTo(tongfu, Rel.EMPLOYED);
			tongfu.createRelationshipTo(xiucai, Rel.EMPLOYS);
			xiucai.createRelationshipTo(tongfu, Rel.EMPLOYED);
			tongfu.createRelationshipTo(dazui, Rel.EMPLOYS);
			dazui.createRelationshipTo(tongfu, Rel.EMPLOYED);
			hengshan.createRelationshipTo(xiaobei, Rel.EMPLOYS);
			xiaobei.createRelationshipTo(hengshan, Rel.EMPLOYED);
			qixiazhen.createRelationshipTo(xiaoliu, Rel.EMPLOYS);
			xiaoliu.createRelationshipTo(qixiazhen, Rel.EMPLOYED);
			qixiazhen.createRelationshipTo(wushuang, Rel.EMPLOYS);
			wushuang.createRelationshipTo(qixiazhen, Rel.EMPLOYED);
			shibalipu.createRelationshipTo(laoxing, Rel.EMPLOYS);
			laoxing.createRelationshipTo(shibalipu, Rel.EMPLOYED);
			longmen.createRelationshipTo(tongboda, Rel.EMPLOYS);
			tongboda.createRelationshipTo(longmen, Rel.EMPLOYED);
			baima.createRelationshipTo(xiansheng, Rel.EMPLOYS);
			xiansheng.createRelationshipTo(baima, Rel.EMPLOYED);
			kunlun.createRelationshipTo(laohe, Rel.EMPLOYS);
			laohe.createRelationshipTo(kunlun, Rel.EMPLOYED);
			kunlun.createRelationshipTo(hanjuan, Rel.EMPLOYS);
			hanjuan.createRelationshipTo(kunlun, Rel.EMPLOYED);

			tx.success();
		} finally {
			tx.finish();
		}

		Node foundUser = userIndex.get(Neo4jDB.NAME_KEY, "白展堂").getSingle();

		logger.debug("~~~~~~~~~~~~~~~~~~" + foundUser.getProperty(Neo4jDB.PROFESSION_KEY));

		graphDb.shutdown();
	}

	private static Node createIndexUser(final String name, final int age, final String gender, final String profession) {
		Node node = graphDb.createNode();
		node.setProperty(Neo4jDB.NAME_KEY, name);
		node.setProperty(Neo4jDB.AGE_KEY, age);
		node.setProperty(Neo4jDB.GENDER_KEY, gender);
		node.setProperty(Neo4jDB.PROFESSION_KEY, profession);

		// 添加该记录的索引
		userIndex.add(node, Neo4jDB.NAME_KEY, name);
		userIndex.add(node, Neo4jDB.AGE_KEY, age);
		userIndex.add(node, Neo4jDB.GENDER_KEY, gender);
		userIndex.add(node, Neo4jDB.PROFESSION_KEY, profession);
		return node;
	}

	private static Node createIndexOrg(String name, String description) {
		Node node = graphDb.createNode();
		node.setProperty(Neo4jDB.NAME_KEY, name);
		node.setProperty(Neo4jDB.DESCRIPTION_KEY, description);
		// 添加该记录的索引
		orgIndex.add(node, Neo4jDB.NAME_KEY, name);
		orgIndex.add(node, Neo4jDB.DESCRIPTION_KEY, description);
		return node;
	}

	public static void main(String[] args) {
		InitDatabase.init();
	}

}
