<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" href="/img/favicon2.png" type="image/x-icon">
<link href="/css/common.css" rel="stylesheet" />

<style>
  table { width:100%; }
  td {
     padding:5px 10px;
     text-align : center;
     &:nth-of-type(1) {
	     background: black;
	     color : white;
	     border:1px solid white;
	 } 
  }
  tr:last-child > td {
      background: white;
      border : 1px solid black; 
  }
    
  input[type=text], input[type=number] {
     width : 100%;
  }
  input[type=submit], input[type=button] {
     width : 100px;
  }
  
</style>
<body> 
  <main>
  	  <%@include file="/WEB-INF/include/menus.jsp" %> 
  
    <h2>게시글 내용 보기</h2>
     <table>
      <tr>
        <td>글 번호</td>
        <td>${board.idx}</td>
        <td>조회수</td>
        <td>${board.hit}</td>
      </tr>
      <tr> 
        <td>작성자</td> 
        <td>${board.writer}</td>
        <td>작성일</td>
        <td>${board.regdate}</td>                
      </tr>
      <tr>
        <td>제목</td>
        <td colspan="3">${board.title }</td>
      </tr>
      <tr>
        <td>내용</td>
        <td colspan="4">${board.content }</td>
      </tr>
      
      <tr>
        <td colspan="4">
        	<a href="/Board/WriteForm">새글쓰기</a>
        	<a href="/Board/UpdateForm?idx=${border.idx}">수정</a>
        	<a href="/Board?delete?idx=${border.idx }">삭제</a>
        	<a href="/Border/List">목록</a>
        	<a href="/">HOME</a>
        </td>
      </tr>
     </table>    
  
  </main>
</body>
</html>    















