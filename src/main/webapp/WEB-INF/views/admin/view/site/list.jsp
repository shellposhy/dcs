<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<body>
	<div id="content" class="span12">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" value="121">
				<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
				<li><a href="#">站点管理</a></li>
			</ul>
		</div>
		<div class="padlrn span6 action_buttons">
			<a class="btn" href="${appPath}/admin/view/page/new" target="_self"><i class="icon-plus"></i> 添加</a>
			<a class="btn delete_list" href="#"><i class="icon-trash"></i> 删除</a>
		</div>
		<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered bootstrap-datatable trHoverEdit trHoverModi" id="site_list">
			<thead>
				<tr>
					<th><label class="checkbox inline"><input type="checkbox" class="selAll" />站点名称</label></th>
					<th>站点域名</th>
					<th>站点编码</th>
					<th>抓取状态</th>
					<th>抓取次数</th>
					<th>抓取间隔</th>
					<th>更新时间</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<th>名称</th>
					<th>站点域名</th>
					<th>站点编码</th>
					<th>抓取状态</th>
					<th>抓取次数</th>
					<th>抓取间隔</th>
					<th>更新时间</th>
				</tr>
			</tfoot>
		</table>
	</div>
	<script src="${appPath}/admin/jscript/site/site.js"></script>
</body>
</html>