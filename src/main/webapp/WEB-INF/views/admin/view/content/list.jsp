<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<body>
	<div id="content" class="span12">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" value="122">
				<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
				<li><a href="#">站点内容管理</a></li>
			</ul>
		</div>
		<div class="padlrn span6 action_buttons">
			<a class="btn" href="${appPath}/admin/site/content/${siteId}/new" target="_self"><i class="icon-plus"></i> 添加</a>
			<a class="btn delete_list" href="#"><i class="icon-trash"></i> 删除</a>
			<input type="hidden" id="siteId"  value="${siteId}">
		</div>
		<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered bootstrap-datatable trHoverEdit trHoverModi" id="site_content_list">
			<thead>
				<tr>
					<th><label class="checkbox inline"><input type="checkbox" class="selAll" />站点名称</label></th>
					<th>类型</th>
					<th>表达式</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<th>站点名称</th>
					<th>类型</th>
					<th>表达式</th>
				</tr>
			</tfoot>
		</table>
	</div>
	<script src="${appPath}/admin/jscript/site/content.js"></script>
</body>
</html>