/**
 * 站点内容配置管理通用js
 * 
 * @author shishb
 * @version 1.0
 */
$(document).ready(function() {
	init_content_data_list();
	//site_form_validate();
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
			"bSort" : false,
			"sAjaxSource" : "/admin/site/content/"+$("#siteId")+"/s",
			"fnServerData" : retrieveData,
			"iDisplayLength" : 20,
			"oLanguage" : {"sUrl" : appPath + "/admin/js/javascript/de_CN.js"},
			"aoColumns" : [
			        {"mData" : "unitName",'fnRender' : 
			        	function(obj) {
							return '<label class="checkbox inline">'+'<input type="checkbox" id="inlineCheckbox'+ obj.aData.id+ '" name="idStr'+ obj.aData.id+ '" value="'+ obj.aData.id+ '" style="opacity: 0;" >'+ obj.aData.name+ '</label>'
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
	trHoverEdit();
	listDelete("/admin/site/delete", dataTableSiteContent);
}