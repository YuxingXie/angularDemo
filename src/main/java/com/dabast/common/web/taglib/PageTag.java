package com.dabast.common.web.taglib;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Created by IntelliJ IDEA.
 * User: lzp
 * Date: 11-5-16
 * Time: 下午6:22
 * from:http://www.adictosaltrabajo.com/tutoriales/tutoriales.php?pagina=tagsconcuerpo
 */
public class PageTag extends BodyTagSupport {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final String LI_LINK_STR = "<li><a href='";

    private static Logger logger = Logger.getLogger(PageTag.class);

    /**
     * property declaration for tag attribute: TotalPages.
     */
    private int totalPages;
    /**
     * property declaration for tag attribute: CurrentPage.
     */
    private Integer currentPage;
    /**
     * property declaration for tag attribute: Language.
     */
    private String language = "Simplified Chinese";
    /**
     * property declaration for tag attribute: Style.
     */
    private String style = "Standard";
    /**
     * Holds value of property totalRecords.
     */
    private int totalRecords;
    private String frontPath;
    private int displayNum;
    private boolean isDisplaySelect;
    private boolean isDisplayGoToPage;
    private String ajaxUrl;

    public String getAjaxUrl() {
        return ajaxUrl;
    }

    public void setAjaxUrl(String ajaxUrl) {
        this.ajaxUrl = ajaxUrl;
    }

    public PageTag() {
        super();
        displayNum = 10;
        isDisplaySelect = true;
        isDisplayGoToPage = false;
    }

    public void otherDoStartTagOperations() {
        currentPage = currentPage == null ? 1 : currentPage;

        try {
            JspWriter out = pageContext.getOut();
            String re = showStandardStyle();

            out.println(re);
        } catch (IOException ex) {
            // do something
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public boolean theBodyShouldBeEvaluated() {

        return true;

    }


    public boolean shouldEvaluateRestOfPageAfterEndTag() {

        return true;

    }


    public int doStartTag() throws JspException {

        otherDoStartTagOperations();

        if (theBodyShouldBeEvaluated()) {
            return EVAL_BODY_BUFFERED;
        } else {
            return SKIP_BODY;
        }
    }


    public int doEndTag() throws JspException {


        if (shouldEvaluateRestOfPageAfterEndTag()) {
            return EVAL_PAGE;
        } else {
            return SKIP_PAGE;
        }
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String value) {
        language = value;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String value) {
        style = value;
    }

    /**
     * .
     * Fill in this method to process the body content of the tag.
     * You only need to do this if the tag's BodyContent property
     * is set to "JSP" or "tagdependent."
     * If the tag's bodyContent is set to "empty," then this method
     * will not be called.
     */
    public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {

        bodyContent.writeOut(out);

        bodyContent.clearBody();
    }

    public void handleBodyContentException(Exception ex) throws JspException {
        // Since the doAfterBody method is guarded, place exception handing code here.
        throw new JspException("error in PageSeperatorTag: " + ex);
    }

    public int doAfterBody() throws JspException {

        try {
            //
            // This code is generated for tags whose bodyContent is "JSP"
            //
            BodyContent bodyContent = getBodyContent();
            JspWriter out = bodyContent.getEnclosingWriter();

            writeTagBodyContent(out, bodyContent);
        } catch (Exception ex) {
            handleBodyContentException(ex);
        }

        if (theBodyShouldBeEvaluatedAgain()) {
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }

    /**
     * Fill in this method to determine if the tag body should be evaluated
     * again after evaluating the body.
     * Use this method to create an iterating tag.
     * Called from doAfterBody().
     */
    public boolean theBodyShouldBeEvaluatedAgain() {

        return false;
    }

    private String getCleanUrl() throws UnsupportedEncodingException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        Map parameters = request.getParameterMap();
        boolean isFirst = true;
        Set entries = parameters.entrySet();
        Iterator it = entries.iterator();
        StringBuilder reqUrlBuilder = new StringBuilder();
        if (null == this.ajaxUrl) {
            reqUrlBuilder.append(request.getRequestURI());
        } else {
            reqUrlBuilder.append(this.ajaxUrl);
        }

        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String name = (String) entry.getKey();
            String[] value = (String[]) entry.getValue();

            String[] temp = new String[value.length];
            System.arraycopy(value, 0, temp, 0, value.length);

            for (int i = 0; i < value.length; i++) {
                temp[i] = URLEncoder.encode(temp[i], "utf-8");
            }
            if (!name.equalsIgnoreCase("page")) {
                for (int j = 0; j < temp.length; j++) {
                    if (StringUtils.isNotEmpty(temp[j])) {
                        if (isFirst) {
                            isFirst = false;
                            reqUrlBuilder.append("?" + name + "=" + temp[j]);
                        } else {
                            String param = "&" + name + "=" + temp[j];
                            String param2 = "&" + name + "=" + temp[j] + "&";
                            if (reqUrlBuilder.indexOf(param2) < 0 && !reqUrlBuilder.toString().endsWith(param)) {
                                reqUrlBuilder.append("&" + param);
                            }
                        }
                    }
                }
            }
        }
        return response.encodeURL(reqUrlBuilder.toString());
    }

    private String getCompleteUrl(String reqUrl, int page) {
        if (reqUrl.indexOf('?') > 0) {
            return reqUrl + "&page=" + page;
        } else {
            return reqUrl + "?page=" + page;
        }
    }

    private String getCompleteUrlNoParam(String reqUrl) {
        if (reqUrl.indexOf('?') > 0) {
            return reqUrl + "&";
        } else {
            return reqUrl + "?";
        }
    }

    private void clacCurrentPageStyle(StringBuilder re, String cleanUrl) { //NOSONAR
        int pagenumber = displayNum;
        int pagecenter = pagenumber / 2 - 1;
        int pagebet = pagenumber / 2 + 1;
        int beginPage = 1;
        int endPage = 1;

        if (currentPage < pagebet) {
            beginPage = 1;
        } else {
            beginPage = currentPage - pagecenter;
        }

        if (currentPage + pagecenter > totalPages) {
            endPage = totalPages;
        } else {
            endPage = currentPage + pagecenter;
        }

        if (currentPage + pagecenter < pagenumber) {
            endPage = pagenumber;
        }

        if (endPage - currentPage < pagecenter) {
            beginPage = totalPages - (pagenumber - 1);
            if (beginPage != 1) {
                beginPage += 1;
            }
        }

        if (beginPage <= 0) {
            beginPage = 1;
        }

        if (endPage > totalPages) {
            endPage = totalPages;
        }


        for (int i = beginPage; i <= endPage; i++) {
            String item = "";
            if (i != currentPage) {
                item = LI_LINK_STR + getCompleteUrl(cleanUrl, i) +
                        "' class='everyPage'>" + i +
                        "</a></li>";
            } else {
                item = "<li><span class='nowPage'>" + i +
                        "</span></li>";
            }
            re.append(item);
        }
//        if(totalRecords-endPage>=1){
//            if(totalRecords-endPage>1){
//                re.append("<li>...&nbsp;</li>");
//            }
//            re.append(LI_LINK_STR + getCompleteUrl(cleanUrl, totalPages) +
//                    "' class='everyPage'>"+totalPages+"</a></li>");
//        }

    }

    private String showStandardStyle() throws Exception { //NOSONAR
        StringBuilder re = new StringBuilder();
        re.append(writeCss());
        String cleanUrl = getCleanUrl();

        int ItemsDisplayed = 15;
        if (totalRecords > 0) {
            re.append("总共" + totalRecords + "条记录 ");
        }
        re.append("总共" + totalPages + "页,当前是第" + currentPage + "页&nbsp;&nbsp;");

        re.append("<div id='infoPage'><ul>");

        if (currentPage > 1) {
            re.append(LI_LINK_STR + getCompleteUrl(cleanUrl, 1) +
                    "' class='upPage' title='首页'>首页</a></li>");
            re.append(LI_LINK_STR + getCompleteUrl(cleanUrl, currentPage - 1) +
                    "' class='upPage' title='上一页'>上一页</a></li>");
        }

        //计算显示的页
        if (displayNum == 0) {
            re.append("<li class='pages'><span class='currentPage'>" + currentPage + "</span>/" + totalPages + "</li>");
        } else {
            clacCurrentPageStyle(re, cleanUrl);
        }
        if (currentPage < totalPages) {
            re.append(LI_LINK_STR + getCompleteUrl(cleanUrl, currentPage + 1) +
                    "' class='downPage' title='下一页'>下一页</a></li>");
            re.append(LI_LINK_STR + getCompleteUrl(cleanUrl, totalPages) +
                    "' class='downPage' title='尾页'>尾页</a></li>");
        }

        boolean isSelect = isDisplaySelect;
        if (isDisplayGoToPage) {
            isSelect = false;
            re.append("<li><div id='page-skip'>&nbsp;&nbsp;第&nbsp;<input id='inputPage' value='" + currentPage + "'/>&nbsp;页");
            String script = "javascript:var goPage=this.parentNode.parentNode.getElementsByTagName('input')[0].value;if(isNaN(goPage)||goPage>"
                    + totalPages + "||goPage<1||goPage==" + currentPage + ")return;document.location='" + getCompleteUrlNoParam(cleanUrl)
                    + "page='+goPage;return false;";
            re.append("<button href='javascript:;' onclick=\"" + script + "\" class='goToPage'>确定</button><div></li>");
        }

        if (isSelect) {
            //下拉的翻页可以不显示
            re.append("<li>&nbsp;&nbsp;第&nbsp;<select name='select2' onchange=\"window.location.href='" +
                    getCompleteUrlNoParam(cleanUrl) +
                    "page='+this.options[this.selectedIndex].value + ''\">");
            for (int iCount = 1; iCount <= totalPages; iCount++) {
                String strSelected = "";
                if (iCount == currentPage) {
                    strSelected = "selected";
                }
                re.append("<option value='" + iCount + "' " + strSelected + ">-" +
                        iCount + "-</option>");
            }
            re.append("</select>&nbsp;页</li>");
        }
        re.append("</ul></div>");
        return re.toString();
    }

    //    private void writeCss(StringBuilder re) {
//        re.append("<style>#infoPage {font-family: Verdana,Arial,Helvetica,sans-serif;height: 30px;margin-bottom: 20px;text-align: center;position:absolute;left;0;}\n" +
//                "#infoPage{float:right;width:auto;padding-top: 10px;}/*expression(doucment.getElementById('infoPage').scrollWidth+'px';);*/\n" +
//
//                "#infoPage ul li{float:left; line-height: 11px;list-style:none;color:#333;}\n" +
//                "#infoPage li span { float: left;}\n" +
//                "#infoPage .prev {margin-right: 20px;}\n" +
//                "#infoPage .next {margin-left: 15px;}\n" +
//                "#infoPage .everyPage{border: 1px solid #D4D4D4;color: #FF6500;margin-right: 3px;padding: 5px 7px;text-decoration: none;}\n" +
//                "#infoPage .nowPage{background: none repeat scroll 0 0 #FFC794;border: 1px solid #FF9600;color: #FF6500;margin-right: 3px;padding: 5px 7px;text-decoration: none;}\n" +
//                "#infoPage select{height:18px;color:#333;}\n" +
//                "#infoPage span, .pager a {font-size: 12px;font-weight: bolder; text-decoration: none;}\n" +
//                "#infoPage li {display: inline-block;height: 34px;}\n" +
//                "#infoPage li a, #infoPage .pages li span {display: block; float: left; width: auto; line-height:11px;}\n" +
//                "#infoPage li a:hover {color:#FF6500;background:#FFC794; border: 1px solid #FF9600;}\n" +
//                "#infoPage .everyPage { color: #0061DE;}\n" +
//                "#infoPage .upPage,#infoPage .downPage {color: #0061DE; border: 1px solid #E5E5E5;height: 16px; padding-top: 5px;text-align: center; width: 48px;margin-right: 4px;}\n" +
//                "#infoPage li.current {color: #000000; cursor: default;}\n" +
//                "#inputPage{border:#b3b5bd 1px solid; width:25px; height: 20px;text-align: center;}\n" +
//                "#infoPage .goToPage{margin-left:5px;width:50px;height:24px;}</style>");
//    }
    private String writeCss() {
        StringBuilder re = new StringBuilder();
        re.append("<style>" +
//                "#infoPage {font-family: Verdana,Arial,Helvetica,sans-serif;height: 30px;margin-bottom: 20px;text-align: center;position:absolute;left;0;}\n" +
                "#infoPage {font-family: Verdana,Arial,Helvetica,sans-serif;text-align: center;padding:0px 0 0 0;}\n" +
                "#infoPage{float:right;width:auto;/*padding-top: 10px;*/}/*expression(doucment.getElementById('infoPage').scrollWidth+'px';);*/\n" +
//                "#infoPage ul li{float:left; line-height: 11px;list-style:none;color:#333;}\n" +
//                "#infoPage li span { float: left;}\n" +
                "#infoPage .prev {margin-right: 20px;}\n" +
                "#infoPage .next {margin-left: 15px;}\n" +
                "#infoPage .everyPage{/*border: 1px solid #D4D4D4;color: #FF6500;*/margin-right: 3px;padding: 2px 7px;text-decoration: none;}\n" +
//                "#infoPage .nowPage{background: none repeat scroll 0 0 #FFC794;border: 1px solid #FF9600;color: #FF6500;margin-right: 3px;padding: 2px 6px;text-decoration: none;}\n" +
                "#infoPage .nowPage{background: none repeat scroll 0 0;margin-right: 3px;padding: 2px 6px;text-decoration: none;}\n" +
//                "#infoPage select{height:18px;color:#333;}\n" +
                "#infoPage span, .pager a {font-size: 12px;font-weight: bolder; text-decoration: none;}\n" +
                "#infoPage li {display: inline-block;/*height: 34px;*/}\n" +
                "#infoPage li a, #infoPage .pages li span {display: block; float: left; width: auto; line-height:11px;}\n" +
//                "#infoPage li a:hover {color:#FF6500;background:#FFC794; border: 1px solid #FF9600;}\n" +
                "#infoPage a:hover {color:#FF6500; }\n" +
//                "#infoPage .everyPage { color: #0061DE;}\n" +
                "#infoPage .upPage,#infoPage .downPage {color: #0061DE; border: 1px solid #E5E5E5;/*height: 16px;*/ padding-top: 2px;text-align: center; width: 48px;margin-right: 4px;}\n" +
//                "#infoPage li.current {color: #000000; cursor: default;}\n" +
                "#inputPage{border:#b3b5bd 1px solid; width:25px; /*height: 20px;*/text-align: center;}\n" +
                "#infoPage .goToPage{margin-left:0px;width:30px;/*height:20px;*/display:inline-block;}" +

                "</style>");
        return re.toString();
    }

    /**
     * Getter for property totalRecords.
     *
     * @return Value of property totalRecords.
     */
    public int getTotalRecords() {
        return this.totalRecords;
    }

    /**
     * Setter for property totalRecords.
     *
     * @param totalRecords New value of property totalRecords.
     */
    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int value) {
        totalPages = value;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int value) {
        currentPage = value;
    }

    public String getFrontPath() {
        return frontPath;
    }

    public int getDisplayNum() {
        return displayNum;
    }

    public boolean isIsDisplaySelect() {
        return isDisplaySelect;
    }

    public void setFrontPath(String value) {
        frontPath = value;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }

    public void setIsDisplaySelect(boolean isDisplaySelect) {
        this.isDisplaySelect = isDisplaySelect;
    }

    public boolean isIsDisplayGoToPage() {
        return isDisplayGoToPage;
    }

    public void setIsDisplayGoToPage(boolean isDisplayGoToPage) {
        this.isDisplayGoToPage = isDisplayGoToPage;
    }


}
