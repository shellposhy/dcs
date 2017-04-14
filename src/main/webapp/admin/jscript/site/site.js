/**
 * 站点管理通用js
 * 
 * @author shishb
 * @version 1.0
 */
$(document).ready(function() {
	init_site_data_list();
	site_form_validate();
});

// Define the global parameters
var dataTableSite;

// Initialize the site liting data
function init_site_data_list() {
	if ($("#site_list").length > 0) {
		dataTableSite=$("#site_list").dataTable({
			"sDom" : "<'row-fluid'<'span6 alignr'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
			"sPaginationType" : "full_numbers",
			"bProcessing" : true,
			"bServerSide" : true,
			"bDestroy" : true,
			"bRetrieve" : true,
			"bSort" : false,
			"sAjaxSource" : "/admin/site/s",
			"fnServerData" : retrieveData,
			"iDisplayLength" : 20,
			"oLanguage" : {"sUrl" : appPath + "/admin/js/javascript/de_CN.js"},
			"aoColumns" : [
			        {"mData" : "name",'fnRender' : 
			        	function(obj) {
							return '<label class="checkbox inline">'+'<input type="checkbox" id="inlineCheckbox'+ obj.aData.id+ '" name="idStr'+ obj.aData.id+ '" value="'+ obj.aData.id+ '" style="opacity: 0;" >'+ obj.aData.name+ '</label>'
								+ '<button title="点击进行配置" data-rel="tooltip" class="btn btn-mini padmbt floatr editbtn none"><i class="icon-edit"></i></button>';
						}
			        },
					{"mData" : "domain"}, 
					{"mData" : "charset"}, 
					{"mData" :'status', 'fnRender':
						function(obj){
							return '<a class="btn btn-success">'+obj.aData.status+'</a>';
						}
					}, 
					{"mData" : "times"}, 
					{"mData" : "intervalTime"}, 
					{"mData" : "updateTime"}
				],
			"fnDrawCallback" : site_list_call_back
		});
	}
}

//Callback function
function site_list_call_back(){
	docReady();
	trHoverEdit();
	listDelete("/admin/site/delete", dataTableSite); 
}

//Validate the form value,not null
function site_form_validate(){
	$("#new_site_form").validate({
		rules : {
			name : {required : true},
			domain : {required : true},
			charset : {required : true}
		},
		messages : {
			name : {required : "站点名称不能为空！"},
			domain : {required : "站点域名不能为空！"},
			charset : {required : "站点编码不能为空！"}
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