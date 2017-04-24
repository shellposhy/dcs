<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
	<div id="content" class="span12">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" value="122">
				<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
				<li><a href="${appPath }/admin/site" target="_self">内容配置</a><span class="divider">/</span></li>
				<li><a href="#">配置编辑</a></li>
			</ul>
		</div>
		<fieldset>
			<legend>配置编辑</legend>
		</fieldset>
		<form:form modelAttribute="crawlContent" class="form-horizontal u_group_form" id="new_content_form"
			action="${appPath}/admin/site/content" method="post" target="_self">
			<fieldset>
				<div class="control-group">
					<label class="control-label" for="type">内容类型</label>
					<div class="controls">
						<form:hidden path="id" />
						<form:hidden path="unitID"/>
						<form:select path="type" id="type" name="type">
							<form:options items="${contentType}" itemLabel="title" />
						</form:select>
						<label class="error"><form:errors path="type" cssClass="error" /> </label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="formulaType">表达式类型</label>
					<div class="controls">
						<form:select path="formulaType" id="formulaType" name="formulaType">
							<form:options items="${formulaTypes}" itemLabel="title" />
						</form:select>
						<label class="error"><form:errors path="formulaType" cssClass="error" /> </label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="formula">提取表达式</label>
					<div class="controls">
						<form:input path="formula" name="formula" class="typeahead" id="formula" />
						<label class="error"><form:errors path="formula" cssClass="error" /> </label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="memo">备注</label>
					<div class="controls">
						<form:textarea path="memo" name="memo" class="typeahead" id="memo" />
						<label class="error"><form:errors path="memo" cssClass="error" /> </label>
					</div>
				</div>
				<div class="form-actions">
					<button type="submit" class="btn btn-primary">保存</button>
				</div>
			</fieldset>
		</form:form>
	</div>
	<script src="${appPath}/admin/jscript/site/content.js"></script>
</body>
</html>