<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<body>
<div id="content">
	<div class="mt10">
		<ul class="breadcrumb ind_f_tree" value="1">
			<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
			<li><a href="#">首页</a></li>
		</ul>
	</div>
	<!--  first -->
	<div class="sortable row-fluid ui-sortable">
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/view/page" target="_self" data-original-title="站点管理">
			<span class="icon32 icon-red icon-book"></span>
			<div>站点管理</div>
			<div class="fontlt">查看</div> 
			<span class="notification red">查看</span>
		</a>
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/view/model" target="_self" data-original-title="日志查看">
			<span class="icon32 icon-blue icon-book-empty"></span>
			<div>日志查看</div>
			<div class="fontlt">查看</div>
			 <span class="notification blue">查看</span>
		</a>
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/task" target="_self"  data-original-title="日志统计">
			<span class="icon32 icon-red icon-bookmark"></span>
			<div>日志统计</div>
			<div class="fontlt">查看</div>
			<span class="notification red">查看</span> 
		</a>
	</div>
	<!-- / 首页九宫格导航 -->
	<div class="index_tip_box">
		<div class="box ">
			<p class="h200 p20">
				更多帮助，请参见<a href="#">帮助文档</a>
			</p>
		</div>
	</div>
</div>
</body>
</html>
