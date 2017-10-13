/*
 * Copyright (c) 2006 Your Corporation. All Rights Reserved.
 */

package com.hinlin.base.jdbc;

import java.util.List;

/**
 * 描述:	 数据页，方便分页数据存取
 * 版权:	 Copyright (c) 2005
 * 公司:	 hinlin
 * 作者:	 hinlin
 * 版本:	 1.0
 * 创建日期: 2006-10-7
 * 创建时间: 17:19:45
 */
public class DBPage
{
    //缺省时一页显示的记录数
    public static final int NUMBERS_PER_PAGE = 10;
    //一页显示的记录数
    private int numPerPage;
    //记录总数
    private int totalRows;
    //总页数
    private int totalPages;
    //当前页码
    private int currentPage;
    //起始行数
    private int startIndex;
    //结束行数
    private int lastIndex;
    //结果集存放List
    private List dataList;

    private boolean firstFlag = true;
    private boolean prevFlag = true;
    private boolean nextFlag = true;
    private boolean lastFlag = true;
    private String buttonType = "";

    /**
     * 分页构造函数
     *
     * @param currentPage 当前页
     * @param numPerPage  每页记录数
     */
    public DBPage(int currentPage, int numPerPage)
    {
        //设置每页显示记录数
        this.numPerPage = numPerPage;
        //设置要显示的页数
        this.currentPage = currentPage;
    }

    /**
     * 设置总的记录数，设置此数据后，此方法会计算其它的数值
     *
     * @param totalRows 总的记录数
     * @param totalRows
     */
    public void setTotalRows(int totalRows)
    {
        //设置总记录数
        this.totalRows = totalRows;
        //计算总页数
        calcTotalPages();
        //计算起始行数
        calcStartIndex();
        //计算结束行数
        calcLastIndex();
        //计算分页按钮信息
        setPageButton();
    }

    /**
     * 设置分页按钮信息
     */
    private void setPageButton()
    {
        if (totalPages == 0 || totalPages == 1)
        {
            firstFlag = false;
            prevFlag = false;
            nextFlag = false;
            lastFlag = false;
            return;
        }

        if (currentPage > totalPages)
        {
            currentPage = totalPages;
        }

        if (currentPage <= 1 || currentPage == 0)
        {
            firstFlag = false;
            prevFlag = false;
        }
        if (currentPage >= totalPages || totalPages == 0)
        {
            lastFlag = false;
            nextFlag = false;
        }
    }


    private void calcTotalPages()
    {
        if (totalRows % numPerPage == 0)
        {
            this.totalPages = totalRows / numPerPage;
        }
        else
        {
            this.totalPages = (totalRows / numPerPage) + 1;
        }
    }


    private void calcStartIndex()
    {
        this.startIndex = (currentPage - 1) * numPerPage;
    }

    private void calcLastIndex()
    {
        if (totalRows < numPerPage)
        {
            this.lastIndex = totalRows;
        }
        else if ((totalRows % numPerPage == 0) || (totalRows % numPerPage != 0 && currentPage < totalPages))
        {
            this.lastIndex = currentPage * numPerPage;
        }
        else if (totalRows % numPerPage != 0 && currentPage == totalPages)
        {
            this.lastIndex = totalRows;
        }
    }

    private String renderButton(String show, boolean disabled, int page)
    {
        if (buttonType.equalsIgnoreCase("text"))
            return renderText(show, disabled, page);

        String temp = "";
        if (disabled == true)
            temp = "disabled";
        return "<input type=\"submit\" class=\"pageButton\" value=\"" + show + "\" onclick=\"goToPage(" + page + ",this.form)\" " + temp + ">\n";
    }

    private String renderText(String show, boolean disabled, int page)
    {
        if (disabled == true)
            return show;
        return "<a class=\"pageLink\" href=\"javascript:toPage(" + page + ")\">" + show + "</a>";
    }

    public String first(String show)
    {
        if (firstFlag == false)
            return renderButton(show, true, 0);
        return renderButton(show, false, 1);
    }

    public String preview(String show)
    {
        if (prevFlag == false)
            return renderButton(show, true, 0);
        return renderButton(show, false, (currentPage - 1));
    }

    public String next(String show)
    {
        if (nextFlag == false)
            return renderButton(show, true, 0);
        return renderButton(show, false, (currentPage + 1));
    }

    public String last(String show)
    {
        if (lastFlag == false)
            return renderButton(show, true, 0);
        return renderButton(show, false, totalPages);
    }


    public void setButtonType(String buttonType)
    {
        this.buttonType = buttonType;
    }

    /**
     * 获得当前页码
     *
     * @return
     */
    public int getCurrentPage()
    {
        return currentPage;
    }

    /**
     * 获得每页显示的记录数
     *
     * @return
     */
    public int getNumPerPage()
    {
        return numPerPage;
    }

    /**
     * 获得分页数据
     *
     * @return
     */
    public List getData()
    {
        if ( dataList!=null&&dataList.size()>getNumPerPage() )
        {
            return dataList.subList(getStartIndex(), getLastIndex());
        }
        return dataList;
    }

    /**
     * 获得总页数
     *
     * @return
     */
    public int getTotalPages()
    {
        return totalPages;
    }

    /**
     * 获得总记录数
     *
     * @return
     */
    public int getTotalRows()
    {
        return totalRows;
    }

    /**
     * 获得当前分页记录开始位置
     *
     * @return
     */
    public int getStartIndex()
    {
        return startIndex;
    }

    /**
     * 获得当前分页记录最后记录位置
     *
     * @return
     */
    public int getLastIndex()
    {
        return lastIndex;
    }

    /**
     * 设置分页对象的数据
     *
     * @param dataList
     */
    public void setData(List dataList)
    {
        this.dataList = dataList;
    }
    
    /**
     * 根据url构造分页字符串
     * @param url url
     * @return 分页字符串
     */
    public String getLinkStr(String url,String path)
    {
        String linkStr = "";
        String scriptTmp = "";

        int pageNumber = this.currentPage;
        int pages = this.totalPages;
        int total = this.totalRows;

        linkStr += "共 <b>" + total + "</b> 条&nbsp;当前 <b>" + pageNumber +
            "</b>/<b>" + pages + "</b> 页&nbsp;&nbsp;";
        if (url.indexOf("?") > 0)
        { //已经带有一个参数
            url = url + "&";
        }
        else
        {
            url = url + "?";
        }
        if ( (pageNumber == 1) && (pageNumber < pages))
        {
            linkStr += "首页&nbsp;上一页&nbsp;<a href='" + url + "&pageNumber=" +
                (pageNumber + 1) +
                "'>下一页</a>&nbsp;<a href='" + url +
                "&pageNumber=" +
                pages + "'>尾页</a>&nbsp;跳到<input type=text name=jump id=jump size=3 style=\"text-align:center\" value='"+pageNumber+"'>页&nbsp;<img border=\"0\" src=\""+path+"/images/button020.gif\" width=\"20\" height=\"18\"  style=\"cursor:hand;\" onclick=\"javascript:checkPage();\">";
        }
        else if ( (pageNumber > 1) && (pageNumber < pages))
        {

            linkStr += "<a href='" + url +
                "&pageNumber=1'>首页</a>&nbsp;<a href='" +
                url + "&pageNumber=" + (pageNumber - 1) +
                "'>上一页</a>&nbsp;<a href='" + url +
                "&pageNumber=" +
                (pageNumber + 1) + "'>下一页&nbsp;<a href='" +
                url +
                "&pageNumber=" +
                pages + "'>尾页</a>&nbsp;跳到<input type=text name=jump id=jump size=3 style=\"text-align:center\" value='"+pageNumber+"'>页&nbsp;<img border=\"0\" src=\""+path+"/images/button020.gif\" width=\"20\" height=\"18\"  style=\"cursor:hand;\" onclick=\"javascript:checkPage();\">";
        }
        else if ( (pageNumber == pages) && (pages > 1))
        {
            linkStr += "<a href='" + url +
                "&pageNumber=1'>首页</a>&nbsp;<a href='" +
                url + "&pageNumber=" + (pageNumber - 1) +
                "'>上一页</a>&nbsp;下一页&nbsp;尾页&nbsp;跳到<input type=text name=jump id=jump size=3 style=\"text-align:center\" value='"+pageNumber+"'>页&nbsp;<img border=\"0\" src=\""+path+"/images/button020.gif\" width=\"20\" height=\"18\" style=\"cursor:hand;\" onclick=\"javascript:checkPage();\">";
        }
        scriptTmp += "<SCRIPT LANGUAGE=\"JavaScript\">\n<!--\nfunction checkPage()\n {\n if(document.getElementById(\"jump\").value > " +
            pages + ") {\n alert('您输入的页码超出范围，请重新输入！');\n document.getElementById('jump').focus();\n return false;\n} else if(document.getElementById('jump').value < 1) {\n alert('您输入的页码超出范围，请重新输入！');\n document.getElementById('jump').focus();\n return false;\n} else {\n jumpTo('" +
            url + "');\n}\n}\n//-->\n</SCRIPT>";
        scriptTmp += "<SCRIPT LANGUAGE=\"JavaScript\">\n<!--\nfunction jumpTo(url)\n {\n self.location.href=\"" +
            url + "&pageNumber=\"+" +
            "document.getElementById(\"jump\").value;\n}\n//-->\n</SCRIPT>";
        linkStr += scriptTmp;
        return linkStr;
    }
}