<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<META HTTP-EQUIV="EXPIRES" CONTENT="0">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page session="false"%>
<html>
<head>
<link href="${pageContext.servletContext.contextPath}/resources/css/Homepage.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/material.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/userdashboard.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <link rel="javascript" href="${ pageContext.servletContext.contextPath}/resources/js/paging.js">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">

.listDisplay {
	width: 100%;
	margin: 120px 100px 20px 1px;
	border: 0px solid #aaa;
	padding: 5px;
	font-family: "Arial", Sans-serif;
}

#addUser {
	margin: 320px 10px 100px 100px
}
</style>
<style type="text/css"> 
.pg-normal { 
color: black; 
font-weight: normal; 
text-decoration: none; 
cursor: pointer; 
} 
.pg-selected { 
color: black; 
font-weight: bold; 
text-decoration: underline; 
cursor: pointer; 
} 
</style> 

<title>View Posts By User</title>
<script type="text/javascript">
function Pager(tableName, itemsPerPage) {
    this.tableName = tableName;
    this.itemsPerPage = itemsPerPage;
    this.currentPage = 1;
    this.pages = 0;
    this.inited = false;
    
    this.showRecords = function(from, to) {        
        var rows = document.getElementById(tableName).rows;
        // i starts from 1 to skip table header row
        for (var i = 1; i < rows.length; i++) {
            if (i < from || i > to)  
                rows[i].style.display = 'none';
            else
                rows[i].style.display = '';
        }
    }
    
    this.showPage = function(pageNumber) {
    	if (! this.inited) {
    		alert("not inited");
    		return;
    	}

        var oldPageAnchor = document.getElementById('pg'+this.currentPage);
        oldPageAnchor.className = 'pg-normal';
        
        this.currentPage = pageNumber;
        var newPageAnchor = document.getElementById('pg'+this.currentPage);
        newPageAnchor.className = 'pg-selected';
        
        var from = (pageNumber - 1) * itemsPerPage + 1;
        var to = from + itemsPerPage - 1;
        this.showRecords(from, to);
    }   
    
    this.prev = function() {
        if (this.currentPage > 1)
            this.showPage(this.currentPage - 1);
    }
    
    this.next = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.currentPage + 1);
        }
    }                        
    
    this.init = function() {
        var rows = document.getElementById(tableName).rows;
        var records = (rows.length - 1); 
        this.pages = Math.ceil(records / itemsPerPage);
        this.inited = true;
    }

    this.showPageNav = function(pagerName, positionId) {
    	if (! this.inited) {
    		alert("not inited");
    		return;
    	}
    	var element = document.getElementById(positionId);
    	
    	var pagerHtml = '<span onclick="' + pagerName + '.prev();" class="pg-normal"> &#171 Prev </span> | ';
        for (var page = 1; page <= this.pages; page++) 
            pagerHtml += '<span id="pg' + page + '" class="pg-normal" onclick="' + pagerName + '.showPage(' + page + ');">' + page + '</span> | ';
        pagerHtml += '<span onclick="'+pagerName+'.next();" class="pg-normal"> Next &#187;</span>';            
        
        element.innerHTML = pagerHtml;
    }
}

</script>
<script type="text/javascript" >
	
	function checkall(source) {
		  checkboxes = document.getElementsByName('check');
		  for(var i=0, n=checkboxes.length;i<n;i++) {
		    checkboxes[i].checked = source.checked;
		  }
		}
	function validateCheckBoxes(){
		var checkCount=0;
		checkboxes = document.getElementsByName('check');
		for(var i=0; i<checkboxes.length;i++) {
		    if(checkboxes[i].checked)
		    	checkCount++;
		}
		if(checkCount==checkboxes.length){
			document.getElementById("selectAllId").checked=true;
		}
		else{
			document.getElementById("selectAllId").checked=false;
		}
	}
	</script>

</head>
<jsp:include page="header.jsp"></jsp:include>
<body>
<div class="posts">

<h4><b>User Post Details</b></h4>
	<br>			
		<c:if test="${not empty msg}">
		<div class="alert alert-${css} alert-dismissible" role="alert">
			<!-- <button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			 --><strong>${msg}</strong>
		</div>
	</c:if>
	
<c:if test="${ empty postList}">
	
	<table id="results" width="100%"  border="1px;">
			<tr style="background-color: #FAAC05;color: white;">
			<thead>
			  <tr>
			  <th width="5%"><input type="checkbox" id="selectAllId" name="selectall" value="all" onclick="checkall(this)"></input></th>
			    <th class="mdl-data-table__cell--non-numeric">Post ID</th>
			    <th class="mdl-data-table__cell--non-numeric">Post</th>
			    <th class="mdl-data-table__cell--non-numeric">Post Date</th>
			    <th class="mdl-data-table__cell--non-numeric">Post Type</th>
			  </tr>
			</thead>
			</table>
	</c:if>

<c:if test="${ ! empty postList}">
<table id="results" class="mdl-data-table mdl-js-data-table mdl-data-table--selectable mdl-shadow--2dp">
  <thead><tr></tr>
   <tr>
    <th width="5%"><input type="checkbox" id="selectAllId" name="selectall" value="all" onclick="checkall(this)"></input></th>
       <th class="mdl-data-table__cell--non-numeric">Post ID</th>
      <th class="mdl-data-table__cell--non-numeric">Post</th>
      <th class="mdl-data-table__cell--non-numeric">Post Date</th>
      <th class="mdl-data-table__cell--non-numeric">Post Type</th> 
    </tr>
  </thead>
  <tbody>
  <% int i=0; %>
			<c:forEach var="post" items="${postList}">
			<tr>
			<td align="center"><input type="checkbox" name="check" value="${post.postid}" id="<%= i++ %>" onchange="validateCheckBoxes()"></input></td>
			<td class="mdl-data-table__cell--non-numeric">${post.postid}</td>
			<td class="mdl-data-table__cell--non-numeric">${post.post}</td>
			<td class="mdl-data-table__cell--non-numeric">${post.postDate }</td>
			<td class="mdl-data-table__cell--non-numeric">${post.postType }</td>
			</tr>
			</c:forEach>
  </tbody>
 </table>
			 <% i++; %>
			 <br>
			<div id="pageNavPosition"></div>
	
</c:if>
			
	
			
			
	<script type="text/javascript">
	function postdelete(){
			var checked=false,checkboxes = document.getElementsByName('check');
			 for(var i=0, n=checkboxes.length;i<n;i++) {
				 if(checkboxes[i].checked)
			       {
			       	checked=true;
			       }
			 	}
			 if(checked){
				 var flag=confirm("Are you sure to delete the selected post(s)");
				 if(flag){
					 for(var i=0, n=checkboxes.length;i<n;i++) {
						   if(checkboxes[i].checked)
					       {
					   		 var id=document.getElementById(i).value;
					   		 var url = "<%=request.getContextPath()%>/deletepost/"+id;
							 alert(url);
							 $.ajax({
								    url: url,
									type: 'GET',
						            dataType: 'text',
									success: function(data) {
									location.reload(true);
										 }
						    		  });
					   		    }	
							}
						}
				 	}
			 	else{
				 alert('Please select atleast one post to delete');
			 		}
			}
		</script>
	
	 <script type="text/javascript"> 
			var pager = new Pager('results', 10); 
			pager.init(); 
			pager.showPageNav('pager', 'pageNavPosition'); 
			pager.showPage(1); 
		</script> 
		<% int i=1; %>
		 <c:forEach var="post" items="${postList}"> 
		 <% if(i==1){ %>
			<c:if test="${fn:length(postList) > 0}" >
			 <button id="submit" type="submit" onclick="javascript:postdelete()" style="color:white;margin-left:50px; position: relative; top: 20px;">Delete</button>
			<!-- <a href="javascript:postdelete()" style="color: blue;margin-left:50px; position: relative; top: 8px;" >Delete</a> -->
			</c:if>
			<% 
				i=0;
		 	}
			%>

		</c:forEach>
</div>

</body>
</html>