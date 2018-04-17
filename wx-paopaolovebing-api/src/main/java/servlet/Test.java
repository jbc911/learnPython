package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import dao.JdbcUtil;
import entity.TextMessage;

public class Test extends HttpServlet {
	private static final long serialVersionUID = -4134342600751543886L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		String token = "880911";
		PrintWriter out = response.getWriter();
		List<String> list = new ArrayList<String>();
		list.add(nonce);
		list.add(timestamp);
		list.add(token);
		Collections.sort(list);
		String str = DigestUtils.sha1Hex(list.get(0) + list.get(1) + list.get(2));
		if (str.equals(signature)) {
			out.print(echostr);
		}
		out.close();
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for (Element e : elementList)
			map.put(e.getName(), e.getText());
		inputStream.close();
		inputStream = null;

		return map;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String result = null;
		Map<String, String> parseXml = null;
		try {
			parseXml = parseXml(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (parseXml != null) {
			TextMessage map = new TextMessage();
			map.setToUserName(parseXml.get("FromUserName"));
			map.setFromUserName(parseXml.get("ToUserName"));
			map.setCreateTime(new Date().getTime());
			map.setMsgType("text");
			map.setContent(getAnswer(parseXml.get("Content")));
			XStream xstream = new XStream(new DomDriver("UTF-8"));
			xstream.alias("xml", TextMessage.class);
			result = xstream.toXML(map);
		}
		response.getWriter().append(result);
	}

	private String getAnswer(String question) {
		if (question != null && !question.trim().equals("")) {
			if (question.indexOf("=") > -1) {
				JdbcUtil.save(question);
			} else if (question.indexOf("-") > -1) {
				return JdbcUtil.delete(question);
			} else {
				return JdbcUtil.list(question);
			}
		}
		return JdbcUtil.getById(-999);
	}

	public static void main(String[] args) {
		TextMessage map = new TextMessage();
		map.setToUserName("123");
		map.setFromUserName("123");
		map.setCreateTime(123L);
		map.setMsgType("123");
		map.setContent("123");
		XStream xstream = new XStream(new DomDriver("UTF-8"));
		xstream.alias("xml", TextMessage.class);
		String result = xstream.toXML(map);
		System.out.println(result);
	}
}
