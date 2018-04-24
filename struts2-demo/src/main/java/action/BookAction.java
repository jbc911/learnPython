package action;

import com.opensymphony.xwork2.ActionSupport;

public class BookAction extends ActionSupport {
	private static final long serialVersionUID = -1398319795633020330L;

	public String pageList() {
		System.out.println(getText("book.name"));
		return SUCCESS;
	}
}
