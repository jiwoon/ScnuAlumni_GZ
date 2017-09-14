package com.newttl.scnualumni_gz.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.newttl.scnualumni_gz.bean.database.Knowledge;
import com.newttl.scnualumni_gz.logs.ScnuAlumniLogs;
import com.newttl.scnualumni_gz.util.DataBaseUtil;

import oracle.net.aso.d;

/**
 * 聊天服务类
 * 
 * @author lgc
 *
 * 2017年7月19日 下午2:33:59
 */
public class ChatService {

	private static Logger logger=ScnuAlumniLogs.getLogger();
	
	/**
	 * 得到索引路径
	 * @return String
	 */
	public static String getIndexDir(){
		//得到class文件所在的路径
		String classPath=ChatService.class.getResource("/").getPath();
		//将路径classPath中的%20替换为空格
		classPath=classPath.replaceAll("%20", " ");
		//索引存储位置
		logger.debug("索引存储位置-"+classPath+"index/");
		return classPath+"index/";
	}
	
	/**
	 * 创建索引
	 */
	public static void createIndex(){
		logger.debug("====创建索引====");
		//取得问答知识库的所有问答
		DataBaseUtil baseUtil=new DataBaseUtil();
		List<Knowledge> knowledges=baseUtil.findAllKnowledge();
		Directory directory=null;
		IndexWriter indexWriter=null;
		try {
			directory=FSDirectory.open(new File(getIndexDir()));
			IndexWriterConfig config=new IndexWriterConfig(Version.LUCENE_46, new IKAnalyzer(true));
			indexWriter=new IndexWriter(directory, config);
			Document document=null;
			//遍历问答知识库，创建索引
			for (Knowledge knowledge : knowledges) {
				document=new Document();
				//对question进行分词存储
				document.add(new TextField("question", knowledge.getQuestion(),Store.YES));
				//id、answer、category不分词存储
				document.add(new IntField("id", knowledge.getId(), Store.YES));
				document.add(new StringField("answer", knowledge.getAnswer(), Store.YES));
				document.add(new IntField("category", knowledge.getCategory(), Store.YES));
				indexWriter.addDocument(document);
			}
			indexWriter.close();
			directory.close();
			logger.debug("====创建索引【成功】====");
		} catch (IOException e) {
			logger.error("====创建索引【失败】====");
			logger.error(e.toString());
		}
	}
	
	/**
	 * 根据问题从索引文件中检索答案
	 * @param question
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static Knowledge searchIndex(String question){
		logger.debug("====根据问题从索引文件中检索答案====");
		Knowledge knowledge=null;
		try {
			Directory directory=FSDirectory.open(new File(getIndexDir()));
			IndexReader indexReader=IndexReader.open(directory);
			IndexSearcher indexSearcher=new IndexSearcher(indexReader);
			//使用查询解析器创建Query
			QueryParser parser=new QueryParser(Version.LUCENE_46, "question", new IKAnalyzer(true));
			Query query=parser.parse(QueryParser.escape(question));
			//检索得分最高的文档
			TopDocs docs=indexSearcher.search(query, 1);
			if (docs.totalHits>0) {
				knowledge=new Knowledge();
				ScoreDoc[] scoreDocs=docs.scoreDocs;
				for (ScoreDoc scoreDoc : scoreDocs) {
					Document document=indexSearcher.doc(scoreDoc.doc);
					knowledge.setId(document.getField("id").numericValue().intValue());
					knowledge.setQuestion(document.get("question"));
					knowledge.setAnswer(document.get("answer"));
					knowledge.setCategory(document.getField("category").numericValue().intValue());
				}
			}
			indexReader.close();
			directory.close();
			logger.debug("====根据问题从索引文件中检索答案【成功】====");
		} catch (Exception e) {
			knowledge=null;
			logger.error("====根据问题从索引文件中检索答案【失败】====");
			logger.error(e.toString());
		}
		return knowledge;
	}
	
	/**
	 * 聊天方法，根据question返回answer
	 * @param openId 用户id
	 * @param createTime 创建时间
	 * @param question 用户发送的问题
	 * @return
	 */
	public static String chat(String openId,String createTime,String question){
		logger.debug("====聊天方法，根据question返回answer====");
		String answer=null;
		int chatCategory=0;
		Knowledge knowledge=searchIndex(question);
		//找到匹配项
		if (null != knowledge) {
			switch (knowledge.getCategory()) {
			case 1://普通对话
				answer=knowledge.getAnswer();
				//如果答案为空，从问答知识库中随机选择一条
				if ("".equals(answer)) {
					answer=new DataBaseUtil().getOneKnowledgeSub(knowledge.getId());
				}
				chatCategory=1;
				break;
				
			case 2://笑话
				answer=new DataBaseUtil().getJoke();
				chatCategory=2;
				break;
				
			case 3://上下文
				//判断上一次的聊天类型
				int lastCategory=new DataBaseUtil().getLastCategory(openId);
				//是笑话，则继续讲笑话
				if (lastCategory==2) {
					answer=new DataBaseUtil().getJoke();
					chatCategory=2;
				}else {
					answer=knowledge.getAnswer();
					chatCategory=knowledge.getCategory();
				}
				break;
				
			}
			logger.debug("====聊天方法，根据question返回answer【找到匹配项】====");
		}else {
			//未找到匹配项
			logger.error("====聊天方法，根据question返回answer【未找到匹配项】====");
			answer=getDefaultAnswer();
			chatCategory=0;
		}
		//保存聊天记录
		new DataBaseUtil().saveChatLog(openId, createTime, question, answer, chatCategory);
		return answer;
	}
	
	
	/**
	 * 随机获取一个默认的答案
	 * 
	 * @return
	 */
	private static String getDefaultAnswer() {
		String []answer = {
			"要不我们聊点别的？",
			"恩？你到底在说什么呢？",
			"没有听懂你说的，能否换个说法？",
			"虽然不明白你的意思，但我却能用心去感受",
			"听的我一头雾水，阁下的知识真是渊博呀，膜拜~",
			"真心听不懂你在说什么，要不你换种表达方式如何？",
			"哎，我小学语文是体育老师教的，理解起来有点困难哦",
			"是世界变化太快，还是我不够有才？为何你说话我不明白？"
		};
		return answer[getRandomNumber(answer.length)];
	}

	/**
	 * 随机生成 0~length-1 之间的某个值
	 * 
	 * @return int
	 */
	private static int getRandomNumber(int length) {
		Random random = new Random();
		return random.nextInt(length);
	}
	
}
