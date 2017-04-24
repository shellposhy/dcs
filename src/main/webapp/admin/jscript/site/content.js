/**
 * 站点内容配置管理通用js
 * 
 * @author shishb
 * @version 1.0
 */
$(document).ready(function() {
	init_content_data_list();
	site_content_validate();
});

//Define the global parameters
var dataTableSiteContent;

// Initialize the site liting data
function init_content_data_list() {
	if ($("#site_content_list").length > 0) {
		dataTableSite=$("#site_content_list").dataTable({
			"sDom" : "<'row-fluid'<'span6 alignr'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
			"sPaginationType" : "full_numbers",
			"bProcessing" : true,
			"bServerSide" : true,
			"bDestroy" : true,
			"bRetrieve" : true,
			"bSort" : true,
			"sAjaxSource" : "/admin/site/content/"+$("#siteId").val()+"/s",
			"fnServerData" : retrieveData,
			"iDisplayLength" : 20,
			"oLanguage" : {"sUrl" : appPath + "/admin/js/javascript/de_CN.js"},
			"aoColumns" : [
			        {"mData" : "unitName",'fnRender' : 
			        	function(obj) {
							return '<label class="checkbox inline">'
												+'<input type="checkbox" id="inlineCheckbox'+ obj.aData.id+ '" name="idStr'+ obj.aData.id+ '" value="'+ obj.aData.id+ '" style="opacity: 0;" >'+ obj.aData.unitName
											+ '</label>'
											+ '<button title="点击进行配置" data-rel="tooltip" class="btn btn-mini padmbt floatr editbtn none"><i class="icon-edit"></i></button>';
						}
			        },
					{"mData" : "type"}, 
					{"mData" : "formula"}
				],
			"fnDrawCallback" : content_list_call_back
		});
	}
}

//Callback function
function content_list_call_back(){
	docReady();
	content_edit_data();
	listDelete("/admin/site/delete", dataTableSiteContent);
}

//edit data
function content_edit_data(obj){
	$(".trHoverEdit tr").live("mouseenter", function() {
		var eidt_bt = $(this).find("td:first").find("i").parent();
		eidt_bt.fadeIn(200);
	});
	$(".trHoverEdit tr").live("mouseleave", function() {
		var eidt_bt = $(this).find("td:first").find("i").parent();
		eidt_bt.fadeOut(200);
	});
	if (obj) {
		obj();
	} else {
		edit_direct_url($(".trHoverEdit tr button.editbtn"));
	}
}

function edit_direct_url(obj) {
	var thishref = document.location.href;
	obj.live("click", function() {
		window.location.href = appPath + "/admin/site/content/"+ $(this).parent().find("input[type='checkbox']").val()+ "/edit";
	});
}

//Validate the form value,not null
function site_content_validate(){
	$("#new_content_form").validate({
		rules : {
			type : {required : true},
			formula : {required : true}
		},
		messages : {
			type : {required : "内容类型不能为空！"},
			formula : {required : "内容提取表达式不能为空！"}
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