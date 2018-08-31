package com.java.web.util;


import java.util.ArrayList;
import java.util.List;

public class Criteria {
   int page; //현재페이지
   int totalCount; //총페이지수
   int totalPage; //총게시물수
   int countList = 30; //한페이지에 출력될 게시물 수
   int countPage = 5; //한 화면에 출력될 페이지 수
   int startPage; //시작페이지
   int endPage;  //현재화면 끝페이지
   public int getPage() {
      return page;
   }
   public void setPage(int page) {
      this.page = page;
   }
   public int getTotalCount() {
      return totalCount;
   }
   public void setTotalCount(int totalCount) {
      this.totalCount = totalCount;
   }
   public int getCountList() {
      return countList;
   }
   public void setCountList(int countList) {
      this.countList = countList;
   }
   public int getStartPage() {
      return startPage;
   }
   public void setStartPage(int startPage) {
      this.startPage = startPage;
   }
   public int getEndPage() {
      return endPage;
   }
   public void setEndPage(int endPage) {
      this.endPage = endPage;
   }
   
   //컨트롤러에서 page는 넘겨받고 시작 (총페이지수, 한 페이지에 출력될 게시물 수)
   public void setCriteria(int totalCount,int countList) {
      this.totalCount = totalCount;
      this.countList = countList;
      this.totalPage = totalCount / countList;
      if (this.totalCount % countList > 0) {
         this.totalPage++;
      }
      if (totalPage < page) {
          this.page = totalPage;
      }
      this.startPage = ((page-1)/10) * 10 +1; 
      this.endPage = startPage + countPage - 1;
   }
   
   
   
   
   public List<String> parserJs() {
      List<String> list = new ArrayList<String>();

      if (startPage > 1) {
          list.add("<a href=\"?page=1\">처음</a>");
      }
      
      if (page > 1) {
          list.add("<a href=\"?page=" + (page - 1)  + "\">이전</a>");
      }
      
      for (int iCount = startPage; iCount <= endPage; iCount++) {
             if (iCount > totalPage) {
                System.out.println("iCount : "+iCount);
                System.out.println("totalPage : "+totalPage);
                continue;
             }
             else if(iCount == page) {
                 list.add("<a href='?page="+iCount+"' style='margin: 0 10px'><b>" + iCount + "</b></a>");
             } else {
                 list.add("<a href='?page="+iCount+"'style='margin: 0 10px'>" + iCount + "</a>");
             }
      }
      
      if (page < totalPage) {
          list.add("<a href=\"?page=" + (page + 1)  + "\">다음</a>");
      }
      
      if (endPage < totalPage) {
          list.add("<a href=\"?page=" + totalPage + "\">끝</a>");
      }
      
      
      return list;
   }
   public List<String> parserdivisionJs(String division) {
      List<String> list = new ArrayList<String>();

      if (startPage > 1) {
          list.add("<a href=\"?division="+division+"page=1\">처음</a>");
      }
      
      if (page > 1) {
          list.add("<a href=\"?division="+division+"&page=" + (page - 1)  + "\">이전</a>");
      }
      
      for (int iCount = startPage; iCount <= endPage; iCount++) {
             if (iCount > totalPage) {
                continue;
             }
             else if(iCount == page) {
                 list.add("<a href='?division="+division+"&page="+iCount+"' style='margin: 0 10px'><b>" + iCount + "</b></a>");
             } else {
                 list.add("<a href='?division="+division+"&page="+iCount+"' style='margin: 0 10px'>" + iCount + "</a>");
             }
      }
      
      if (page < totalPage) {
          list.add("<a href=\"?division="+division+"&page=" + (page + 1)  + "\">다음</a>");
      }
      
      if (endPage < totalPage) {
          list.add("<a href=\"?division="+division+"&page=" + totalPage + "\">끝</a>");
      }
      
      
      return list;
   }   
   public List<String> parserTypeJs(String type) {
      List<String> list = new ArrayList<String>();

      if (startPage > 1) {
          list.add("<a href=\"?type="+type+"page=1\">처음</a>");
      }
      
      if (page > 1) {
          list.add("<a href=\"?type="+type+"&page=" + (page - 1)  + "\">이전</a>");
      }
      
      for (int iCount = startPage; iCount <= endPage; iCount++) {
             if (iCount > totalPage) {
                continue;
             }
             else if(iCount == page) {
                 list.add("<a href='?type="+type+"&page="+iCount+"' style='margin: 0 10px'><b>" + iCount + "</b></a>");
             } else {
                 list.add("<a href='?type="+type+"&page="+iCount+"' style='margin: 0 10px'>" + iCount + "</a>");
             }
      }
      
      if (page < totalPage) {
          list.add("<a href=\"?type="+type+"&page=" + (page + 1)  + "\">다음</a>");
      }
      
      if (endPage < totalPage) {
          list.add("<a href=\"?type="+type+"&page=" + totalPage + "\">끝</a>");
      }
      
      
      return list;
   }      
}
