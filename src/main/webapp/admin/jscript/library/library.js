/**
 * 页面初始化
 */
$(document).ready(function() {
	// list
	init_directory_tree();
	bind_search();

	// edit
	init_lib_edit();
	bind_validate();
	inin_check_data_option(); 
	init_display_fields(); 
});

//定义公共参数
var thisPath = appPath + "/admin/system/library/";

// 初始化目录树
function init_directory_tree() {
	if ($("#directoryTree").length > 0) {
		var url = thisPath + "tree";
		$.ajax({
			type : "POST",
			url : url,
			data : '[{"name":"id","value":""}]',
			dataType : 'json',
			contentType : 'application/json',
			success : function(data) {
				if (data != null) {
					menuTreeCom($("#directoryTree"), data, true,node_click);
				}
			}
		});
	}
}

//节点被点击事件
function node_click(nodeId, nodeType, isDir) {
	$("#libId").val(nodeId);
	var treeObj = $.fn.zTree.getZTreeObj("directoryTree");
	var treenode = treeObj.getNodeByParam("id", nodeId, null);
	$("#colname").html(treenode.name);
	if (isDir) {
		$('#search_u_db').hide();
		$('#search_u_db_btn').hide();
		$('#libraries').show();
		$('#data_content').hide();
		//目录
		if (nodeType) {
			find_by_parentId(nodeId);
		} 
		//库
		else {
			find_by_parentId_init(nodeId);
		}
	} 
	//非目录节点
	else {
		$('#search_u_db').hide();
		$('#search_u_db_btn').hide();
		$('#libraries').hide();
		$('#data_content').show();
		find_data_by_libId(nodeId);
	}

}

// 添加目录
function add_directory(selid) {
	var url = appPath + "/admin/system/library/directory/new/";
	if (selid > 0) {
		url += selid;
	} else {
		url += "0";
	}
	window.location.href = url;
}



//调用当选中节点为目录节点且不为父节点，初始化右侧为2个添加按钮图标
function find_by_parentId_init(parentId) {
	var libMenu="";
	libMenu+='<li id="add_library">';
	libMenu+=' 	<a href="'+ thisPath+ "directory/new/"+ $("#libId").val()+ '"><div class="addimg_db"></div></a>';
	libMenu+='	 	<div class="actions"><a class="lh30 block  mt10" href="'+ 'javascript:add_directory('+ $("#libId").val()+ ')'+ '">添加目录</a></div>';
	libMenu+='</li>';
	libMenu+='<li id="add_lib">';
	libMenu+=' 	<a href="'+ thisPath+ "new/"+ $("#libId").val()+ '"><div class="addimg_db"></div></a>';
	libMenu+='	 	<div class="actions"><a class="lh30 block db_repair mt10" href="'+ thisPath + 'new/' + $("#libId").val()+ '">添加数据库</a></div>';
	libMenu+='</li>';
	$("#libraries").html(libMenu);
}

//调用当选中节点为目录节点且为父节点，按照父节点查询初始化右侧子节点内容
function find_by_parentId(parentId) {
	$("#libId").val(parentId);
	var treeObj = $.fn.zTree.getZTreeObj("directoryTree");
	var treenode = treeObj.getNodeByParam("id", parentId, null);
	$("#colname").html(treenode.name);
	$.ajax({
		type : "GET",
		url : thisPath + "find/" + parentId,
		dataType : 'json',
		success : function(data) {
			$("#libraries").html("");
			if (data != null) {
				var testData = data[0];
				var testDataType = testData.nodeType;
				if (testDataType == "Lib") {
					var libMenu="";
					libMenu+='<li id="add_lib">';
					libMenu+=' <a href="'+ thisPath+ "new/"+ $("#libId").val()+ '"><div class="addimg_db"></div></a>';
					libMenu+='	 <div class="actions"><a class="lh30 block db_repair mt10" href="'+ thisPath + 'new/'+ $("#libId").val()+ '">添加数据库</a></div>';
					libMenu+='</li>';
					$("#libraries").html(libMenu);
					for (var i = 0; i < data.length; i++) {
						var editLink = "";
						editLink += "<li id='dbv_"+ data[i].id+ "'><a class='' target='_blank' href='javascript:init_library("+ data[i].id+ ",false)' target='_self' ><div class='dbimg'></div></a>";
						editLink += "<span class='dbname'><i class='dbname'>"+ data[i].name+ "</i></span> <span class='dbtime'>更新时间：<br />"+ data[i].dataUpdateTimeStr + "</span>";
						editLink += "<div class='actions' ><a title='修改数据库' class='btn btn-small db_edit pop_link cboxElement' href='"+ thisPath+ "edit/"+ data[i].id+ "' target='_self'><i class='icon-pencil'></i></a>";
						editLink += "<a title='删除数据库' class='btn btn-small ml3 db_del' href='#' onclick='delete_lib("+ data[i].id+ ")' target='_self'><i class='icon-trash'></i></a>";
						editLink += "<a class='lh30 block db_repair mt10' href='#' onclick='repair_lib("+ data[i].id+ ")' target='_self'>修复数据库</a>";
						editLink += "</div><div class='progress progress-striped progress-success active none'><div class='bar'></div></div> </li>";
						$("#libraries").append(editLink);
						if (data[i].status == "Repairing") {
							repair_pregress(data[i].id, data[i].taskId);
						}
					}
				} else if (testDataType == "Directory") {
					var libMenu="";
					libMenu+='<li id="add_library">';
					libMenu+='		<a href="'+ thisPath+ "directory/new/"+ $("#libId").val()+ '"><div class="addimg_db"></div></a>';
					libMenu+='		<div class="actions"><a class="lh30 block  mt10" href="'+ 'javascript:add_directory('+ $("#libId").val() + ')'+ '">添加目录</a></div>';
					libMenu+='</li>';
					$("#libraries").html(libMenu);
					for (var i = 0; i < data.length; i++) {
						var editLink = "";
						editLink += "<li id='dbv_"+ data[i].id+ "'> <a class='' target='_blank' href='"+ "javascript:init_library("+ data[i].id+ ",true"+ ")"+ "' target='_self' ><div class='dbimg'></div></a>";
						editLink += "<span class='dbname'><i class='dbname'>"+ data[i].name + "</i></span>";
						editLink += "<div class='actions' ><a title='修改目录' class='btn btn-small db_edit pop_link cboxElement' href='"+ thisPath+ "directory/"+ data[i].id+ "/edit"+ "' target='_self'><i class='icon-pencil'></i></a>";
						editLink += "<a title='删除目录' class='btn btn-small ml3 db_del' href='#' onclick='delete_library("+ data[i].id+ ")' target='_self'><i class='icon-trash'></i></a>";
						editLink += "</div><div class='progress progress-striped progress-success active none'><div class='bar'></div></div> </li>";
						$("#libraries").append(editLink);
					}
				}
			}
		}
	});
}

//调用当选中节点为数据库节点的时候，右侧为数据列表的内容
function find_data_by_libId(parentId) {
	$("#libId").val(parentId);
	var treeObj = $.fn.zTree.getZTreeObj("directoryTree");
	var treenode = treeObj.getNodeByParam("id", parentId, null);
	$("#colname").html(treenode.name);
	$('#into_as_search').attr("href",appPath + '/admin/data/as');
	$('#colDatas thead tr th, #colDatas tfoot tr th').remove();
	$('#colDatas thead tr').append('<th><label class="checkbox inline"><input type="checkbox" class="selAll" />标题</label></th>');
	$('#colDatas tfoot tr').append('<th>标题</th>');
	var headTitle = [ {
		"mData" : "title",
		"fnRender" : function(obj) {
			var sumImg = "";
			var attach = "";
			if (obj.aData.img) {
				sumImg = '<div class="sum_img_div"><img class="list_sum_img" src="'+ obj.aData.img + '"/></div>';
			}
			if (obj.aData.attach) {
				attach = " <span class='icon icon-blue icon-attachment'></span>";
			}
			return '<h3>'
						+'	<label class="checkbox inline mt0">'
						+'		<input type="checkbox" id="inlineCheckbox'+ obj.aData.id+ '" name="idStr'+ obj.aData.id+ '" value="'+ obj.aData.id+ '_'+ obj.aData.tableId+ '" style="opacity: 0;" >'
						+ '	</label>'
						+'	<a class="data_title edit_pop_link" href="'+ thisPath+ 'data/edit/'+ obj.aData.tableId+ '/'+ obj.aData.id+ '" target="_blank">'+ obj.aData.title+ '</a>'
						+ '	<a class="padmbt btn floatr none edit_pop_link" href = "'+ thisPath+ 'data/info/'+ obj.aData.tableId+ '/'+ obj.aData.id+ '" target="_blank"><i class="icon-eye-open" title="稿件预览"></i></a>'
					+ '</h3>'
					+'<p class="summary clearfix" >'+ sumImg+ obj.aData.summary + attach + '</p>';
		}
	} ];
	var add_data_url = appPath + "/admin/system/library/data/new/" + parentId;
	$('#add_to_dsu').attr('href', add_data_url);
	$.ajax({
		url : appPath + "/admin/system/library/data/tablehead/" + parentId,
		async : false,
		success : function(data) {
			for (var i = 0; i < data.length; i++) {
				var mData = {
					"mData" : data[i].codeName
				};
				headTitle.push(mData);
				$('#colDatas thead tr,#colDatas tfoot tr').append("<th>" + data[i].name + "</th>");
			}
		}
	});
	dataTablesCom($('#colDatas'), "/admin/system/library/data/search/" + parentId,headTitle, null, callback_library_data, true);
}


function callback_library_data(otd) {
	docReady();
	trHoverEdit();
	$(".action_buttons").show();
	listDelete(thisPath + "/data/delete", otd);// 删除稿件
	listDelete(thisPath + "data/delete", otd);// 删除数据路径
	editPopWithDT(otd);// //设置新增-修改文章弹出层

}

//树形节点级联绑定
function init_library(parentId, isDir) {
	var treeObj = $.fn.zTree.getZTreeObj("directoryTree");
	var treenode = treeObj.getNodeByParam("id", parentId, null);
	treeObj.expandNode(treenode, true, false, false);
	treeObj.selectNode(treenode);
	$("#colname").html(treenode.name);
	var nodeType = treenode.isParent;
	$("#libId").val(parentId);
	if (isDir == true) {
		$('#libraries').show();
		$('#data_content').hide();
		if (nodeType) {
			find_by_parentId(parentId);
		} else {
			find_by_parentId_init(parentId);
		}
	} else {
		$('#libraries').hide();
		$('#data_content').show();
		find_data_by_libId(parentId);
	}
}

// 删除目录
function delete_library(id) {
	if (confirm("确定删除?")) {
		$.ajax({
			url : thisPath + "directory/" + id + "/delete",
			type : 'POST',
			success : function(response) {
				$('#dbv_' + id).remove();
			}
		});
	} else
		return false;
}


//关键词查询
function bind_search() {
	$("#search_u_db_btn").click(function() {
		if ($("#search_u_db").val()){
			$("#categoryTree .curSelectedNode").removeClass("curSelectedNode");
			var sdata = '[{"name":"sSearch","value":"'+ $("#search_u_db").val() + '"}]';
			$.ajax({
				type : "post",
				url : thisPath + "/search",
				dataType : 'json',
				contentType : 'application/json',
				data : sdata,
				success : function(data) {
					if (data.length != 0) {
						var libMenu="";
						libMenu+='<li id="add_lib">';
						libMenu+='		<a href="'+ thisPath+ "new/"+ $("#libId").val()+ '"><div class="addimg_db"></div></a>';
						libMenu+='		<div class="actions"><a class="lh30 block db_repair mt10" href="'+ thisPath+ 'new/'+ $("#libId").val()+ '">添加数据库</a></div>';
						libMenu+='</li>';
						$("#libraries").html(libMenu);
						for (var i = 0; i < data.length; i++) {
							var editLink = "";
							editLink += "<li id='dbv_"+ data[i].id+ "'> <a class='' target='_blank' href='"+ thisPath+ 'data/'+ data[i].id+ "' target='_self' ><div class='dbimg'></div></a>";
							editLink += "<span class='dbname'><i class='dbname'>"+ data[i].name+ "</i></span> <span class='dbtime'>更新时间：<br />"+ data[i].dataUpdateTimeStr+ "</span>";
							editLink += "<div class='actions' ><a class='btn btn-small db_edit' href='"+ thisPath+ "edit/"+ data[i].id+ "' target='_self'><i class='icon-pencil'></i>修改</a>";
							editLink += "<a class='btn btn-small ml5 db_del' href='#' onclick='delete_lib("+ data[i].id+ ")' target='_self'><i class='icon-trash'></i>删除</a>";
							editLink += "<a class='lh30 block db_repair mt10' href='#' onclick='repair_lib("+ data[i].id+ ")' target='_self'>修复数据库</a>";
							editLink += "</div><div class='progress progress-striped progress-success active none'><div class='bar'></div></div> </li>";
							$("#libraries").append(editLink);
							if (data[i].status == "Repairing") {
								repair_pregress(data[i].id,data[i].taskId);
							}
						}
					} else {
						$("#libraries").html("<h3 class='alert alert-info' >无匹配的搜索结果！</h3>");
					}
				}
			});
		} else {
			if ($("#libraries .alert-info:contains(请选择)").length) {
				$("#search_u_db").focus();
			} else {
				$("#directoryTree .curSelectedNode").removeClass("curSelectedNode");
				$("#libraries").html("<h3 class='alert alert-info'>请选择左侧分类以查看数据库</h3>");
			}
		}
	});
	$("#search_u_db").keyup(function(event) {
		if (event.keyCode == 13) {
			$("#search_u_db_btn").trigger("click");
			$(this).blur();
		} else {
			return false;
		}
	});
}

// 初始化库的编辑页
function init_lib_edit() {
	if ($("#categories_tree_radio").length > 0) {
		var url = thisPath + "/directory/tree";
		if ($("#categories_tree_radio")[0]) {
			$.ajax({
				url : url,
				success : function(data) {
					treeRadioCom($("#categories_tree_radio .treeNew"),data.children, true);
					setTimeout("$('.treeSelId').click()", 800);
				}
			});
		}
	}
}

// 添加数据库
function add_lib(thishref, selid, isSel, isParent) {
	if (isSel == true) {
		if (isParent == false) {
			window.location.href = thisPath + selid + "/new";
		} else {
			alert("请选择分类子节点以添加数据库");
			return false;
		}
	} else {
		alert("请选择分类节点以添加数据库");
	}
}

// 删除数据库
function delete_lib(id) {
	if (confirm("确定删除?")) {
		$.ajax({
			url : thisPath + "delete/" + id,
			type : 'POST',
			success : function(response) {
				$('#dbv_' + id).remove();
			}
		});
	} else
		return false;
}

// 修复数据库 重建索引
function repair_lib(id) {
	function repair(id) {
		$.ajax({
			url : thisPath + "repair/" + id,
			type : 'POST',
			success : function(data) {
				var thisact = $('#dbv_' + id).find(".actions");
				thisact.hide();
				var thispro = $('#dbv_' + id).find(".progress");
				var thisbar = $('#dbv_' + id).find(".progress .bar");
				thispro.show();
				thisbar.css("width", "0%");
				var oShowProgress = function showProgress() {
					$.ajax({
						type : "GET",
						url : appPath + "/admin/task/progress/" + data,
						cache : false,
						dataType : "json",
						success : function(data) {
							var barpres = data.progress + "%";
							if (data.progress < 100) {
								thisbar.css("width", barpres);
							} else if (data.progress == 100) {
								thisbar.css("width", "100%");
								thispro.hide();
								thisact.show();
								clearInterval(intInterval);
								noty({
									"text" : "修复成功！",
									"layout" : "center",
									"type" : "alert",
									"animateOpen" : {
										"opacity" : "show"
									}
								});
							}
						}
					});
				};
				setInterval(oShowProgress, 5000);
			}
		});
	}
	// 设置确认弹出框
	comConfirmModel(repair, id, "确定修复", "修复将耗时较长，确定修复?");
}

// 获取数据库的修复进度状态
function repair_pregress(id, data) {
	var thisact = $('#dbv_' + id).find(".actions");
	thisact.hide();
	var thispro = $('#dbv_' + id).find(".progress");
	var thisbar = $('#dbv_' + id).find(".progress .bar");
	thispro.show();
	thisbar.css("width", "0%");
	var oShowProgress = function showProgress() {
		$.ajax({
			type : "GET",
			url : appPath + "/admin/task/progress/" + data,
			cache : false,
			dataType : "json",
			success : function(data) {
				var barpres = data.progress + "%";
				if (data.progress < 100) {
					thisbar.css("width", barpres);
				} else if (data.progress == 100) {
					thisbar.css("width", "100%");
					thispro.hide();
					thisact.show();
					clearInterval(intIntervalPre);
					noty({
						"text" : "修复成功！",
						"layout" : "center",
						"type" : "alert",
						"animateOpen" : {
							"opacity" : "show"
						}
					});
				}
			}
		});
	};
	setInterval(oShowProgress, 5000);
}

// 绑定验证
function bind_validate() {
	$("#db_new_form").validate(
			{
				ignore : "",
				rules : {
					name : "required",
					code : "required",
					parentID : "required",
					moreDataFieldsStr : {
						required : function() {
							if ($("#moreFields").parents(".feilds_form_box")
									.hasClass("hiddened")) {
								return false;
							} else {
								return true;
							}
						}
					}
				},
				messages : {
					name : "请填写数据库名称",
					code : "请填写数据库编号",
					moreDataFieldsStr : "请自定义一组字段",
					parentID : "请选择数据库所属分类"
				},
				errorPlacement : function(error, element) {
					error.insertAfter(element);

				},
				submitHandler : function() {
					form.submit();
				},
				onkeyup : false
			});
}

/**
 * 初始化栏目模型，的列表字段显示区域
 */
function init_display_fields() {
	var columnModelId = $("#db_columnModelId").val();
	var dataFieldIdStr = $('#dataFieldsStr').val();
	var url = thisPath + "displayFields/" + columnModelId;
	var fieldsIds = new Array();
	if (null != dataFieldIdStr && "" != dataFieldIdStr) {
		fieldsIds = dataFieldIdStr.split(',');
	}
	if (null != columnModelId && "" != columnModelId) {
		$.ajax({
			type : "GET",
			url : url,
			dataType : 'json',
			contentType : 'application/json',
			success : function(data) {
				var temCheckCon = "";
				var dataResult = data;
				var dataSize = data.length;
				if (dataSize == fieldsIds.length&& fieldsIds.length == 0) {
					for (var i = 0; i < dataSize; i++) {
						temCheckCon += "<label><input  type='checkbox'   onclick='changeChkVal()'   rel='Reason' name='chk'   value='"+ dataResult[i].id+ "'  >"+ dataResult[i].name + "</label> ";
					}
				} else if (dataSize == fieldsIds.length&& fieldsIds.length > 0) {
					for (var i = 0; i < dataSize; i++) {
						temCheckCon += "<label><input  type='checkbox'   onclick='changeChkVal()'  checked  rel='Reason' name='chk'   value='"+ dataResult[i].id+ "'  >"+ dataResult[i].name + "</label> ";
					}
				} else {
					for (var i = 0; i < dataSize; i++) {
						var dataFd = dataResult[i].id;
						var flag = false;
						for (var j = 0; j < fieldsIds.length; j++) {
							if (dataFd == fieldsIds[j]) {
								flag = true;
								break;
							}
						}
						if (flag == false) {
							temCheckCon += "<label><input  type='checkbox' onclick='changeChkVal()'  rel='Reason' name='chk'   value='"+ dataResult[i].id+ "'  >"+ dataResult[i].name + "</label> ";
						} else {
							temCheckCon += "<label><input  type='checkbox' onclick='changeChkVal()'    rel='Reason' name='chk'  value='"+ dataResult[i].id+ "'   checked >"+ dataResult[i].name + "</label> ";
						}
					}
				}
				$('#columnModelFileds').html("");
				temCheckCon+='<label><div class="checker" id="uniform-undefined"><span class="checked"><input type="checkbox" onclick="changeChkVal()" rel="Reason" name="chk" value="16" style="opacity: 0;"></span></div>文档时间</label>';
				$('#columnModelFileds').append(temCheckCon);
				var fieldIds = "";
				var checkVals = $('#columnModelFileds :checkbox:checked');
				for (var i = 0; i < checkVals.length; i++) {
					fieldIds += checkVals[i].value + ",";
				}
				$("input:checkbox").uniform();
				fieldIds = fieldIds.substring(0, fieldIds.length - 1);
				$('#dataFieldsStr').val(fieldIds);
			}
		});
	}
}

// 监听数据库模型的选择，初始化字段区域
function listenModelId() {
	var columnModelId = $("#db_columnModelId").val();
	var url = thisPath + "displayFields/" + columnModelId;
	if (null != columnModelId && "" != columnModelId) {
		$.ajax({
			type : "GET",
			url : url,
			dataType : 'json',
			contentType : 'application/json',
			success : function(data) {
				var temCheckCon = "";
				var dataResult = data;
				var dataSize = data.length;
				for (var i = 0; i < dataSize; i++) {
					temCheckCon += "<input  type='checkbox' rel='Reason'      onclick='changeChkVal()'  name='chk'  value='"+ dataResult[i].id+ "' />"+ dataResult[i].name + " ";
				}
				$('#columnModelFileds').html("");
				$('#columnModelFileds').append(temCheckCon);
				var fieldIds = "";
				var checkVals = $('#columnModelFileds :checkbox:checked');
				for (var i = 0; i < checkVals.length; i++) {
					fieldIds += checkVals[i].value + ",";
				}
				$("input:checkbox").uniform();
				fieldIds = fieldIds.substring(0, fieldIds.length - 1);
				$('#dataFieldsStr').val(fieldIds);
			}
		});
	}
};

// 监听列表显示字段复选框勾选状态改变
function changeChkVal() {
	var checkElem = document.getElementsByName("chk");
	var temVal = "";
	for (var i = 0; i < checkElem.length; i++) {
		if (checkElem[i].checked) {
			temVal += checkElem[i].value + ",";
		}
	}
	temVal = temVal.substring(0, temVal.length - 1);
	$('#dataFieldsStr').val(temVal);
}

// 初始化导入数据是否需要审核Radio选区
var isCheckImportData = $('#isCheckImportData').val();
function inin_check_data_option() {
	if (null != isCheckImportData) {
		var tenFlag = "0";
		if (isCheckImportData == "true") {
			tenFlag = "1";
		} else {
			tenFlag = "0";
		}
		listenDataCheck(tenFlag);
	}
}

// 监听导入数据是否需要审核radio选项变化
function listenDataCheck(checkFlagValue) {
	var radios = document.getElementsByName("checkImportData");

	for (var i = 0; i < radios.length; i++) {
		if (radios[i].value == checkFlagValue) {
			radios[i].checked = true;
			break;
		}
	}
	if (checkFlagValue == "1") {
		$('#isCheckImportData').val("true");
	} else {
		$('#isCheckImportData').val("false");
	}

}
