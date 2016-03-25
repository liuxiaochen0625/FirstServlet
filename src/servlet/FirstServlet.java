package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FirstServlet")
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FirstServlet() {
        super();
    }

    /**
     * 以GET方式访问页面时，执行该方法
     * 执行doGet()前，先执行getLastModified，如果浏览器发现getLastModified返回的数值
     * 与上次返回的数值相同，则认为该文档没有更新，浏览器采用缓存而不执行doGet
     * 如果getLastModified返回值为-1，则认为是时刻更新的，总是执行doGet
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.log("执行doGet()方法");
		this.execute(request, response);
		
	}
	
	/**
	 * 以POST的方式访问页面时，执行该函数。执行前不会执行getLastModified方法
	 * 
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.log("执行doPost()方法");
		this.execute(request, response);
	}
	
	/**
	 * 返回该Servlet生成的文档的更新时间，对doGet有效
	 * 返回的时间为相对于1970年1月1日的毫秒数
	 * 如果为-1则认为是实时更新，默认为-1
	 */
	
	@Override
	public long getLastModified(HttpServletRequest request){
		this.log("执行getLastModified方法");
		return -1;
		
	}
	
	private void execute(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String requestURL = request.getRequestURI();
		String method = request.getMethod();
		String param = request.getParameter("param");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>");
		out.println("A Servlet");
		out.println("</TITLE>");
		out.println("</HEAD>");
		out.println("<BODY>");
		out.println(" 以 "+method+" 方式请求该页面。取到的param参数为:"+param+"<br/>");
		out.println(" <form action='"+requestURL+"' method='get'> <input type='text' name='param' value='param string'>");
		out.println("<input type='submit' value='以Get方式提交到页面"+requestURL+"'></form>");
		out.println("<form action='"+requestURL+"' method='post'><input type='text' name='param' value='param string'>");
		out.println("<input type='submit' value='以POST方式提交到页面"+requestURL+"'></form>");
		
		// 有客户端浏览器读取该文档的更新时间
		out.println("<script>document.write('本次页面更新时间为:'+document.lastModified);</script>");
		
		out.println("</BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
		
	}

}
