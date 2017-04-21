<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
	<div id="content" class="span12">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" value="122">
				<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
				<li><a href="${appPath }/admin/site" target="_self">站点管理</a><span class="divider">/</span></li>
				<li><a href="#">站点编辑</a></li>
			</ul>
		</div>
		<fieldset>
			<legend>站点编辑</legend>
		</fieldset>
		<form:form modelAttribute="crawlUnit" class="form-horizontal u_group_form" id="new_site_form"
			action="${appPath}/admin/site" method="post" target="_self">
			<fieldset>
				<div class="control-group">
					<label class="control-label" for="name">站点名称</label>
					<div class="controls">
						<form:hidden path="id" />
						<form:input path="name" name="name" class="typeahead" id="name"/>
						<label class="error"><form:errors path="name" cssClass="error" /> </label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="domain">站点域名</label>
					<div class="controls">
						<form:input path="domain" name="domain" class="typeahead" id="domain" />
						<label class="error"><form:errors path="domain" cssClass="error" /> </label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="startUrl">抓取起始页</label>
					<div class="controls">
						<form:input path="startUrl" name="startUrl" class="typeahead" id="startUrl" />
						<label class="error"><form:errors path="startUrl" cssClass="error" /> </label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="subUrl">站点子链表达式</label>
					<div class="controls">
						<form:input path="subUrl" name="subUrl" class="typeahead" id="subUrl" />
						<label class="error"><form:errors path="subUrl" cssClass="error" /> </label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="charset">网站编码</label>
					<div class="controls">
						<form:select path="charset" class="autogrow" name="charset" id="charset">
							<form:option value="utf-8">UTF-8</form:option>
							<form:option value="gb2312">GB2312</form:option>
							<form:option value="gbk">GBK</form:option>
							<form:option value="iso8859-1">ISO8859-1</form:option>
						</form:select>
						<label class="error"><form:errors path="charset" cssClass="error" /> </label>
					</div>
				</div>
				<div class="form-actions">
					<button type="submit" class="btn btn-primary">保存</button>
				</div>
			</fieldset>
		</form:form>
	</div>
	<script src="${appPath}/admin/jscript/site/site.js"></script>
</body>
</html>