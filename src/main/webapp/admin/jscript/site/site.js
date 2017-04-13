/**
 * 站点管理通用js
 * 
 * @author shishb
 * @version 1.0
 */
$(document).ready(function() {
	init_site_data_list();
});

// Define the global parameters
var dataTableSite;

// Initialize the site liting data
function init_site_data_list() {
	if ($("#site_list").length > 0) {
		var dataTableTitle = [
		        {"mData" : "name",'fnRender' : 
		        	function(obj) {
						return '<label class="checkbox inline">'+'<input type="checkbox" id="inlineCheckbox'+ obj.aData.id+ '" name="idStr'+ obj.aData.id+ '" value="'+ obj.aData.id+ '" style="opacity: 0;" >'+ obj.aData.name+ '</label>'
							+ '<button title="点击进行配置" data-rel="tooltip" class="btn btn-mini padmbt floatr editbtn none"><i class="icon-edit"></i></button>';
					}
		        },
				{"mData" : "domain"}, 
				{"mData" : "charset"}, 
				{"mData" : "status"}, 
				{"mData" : "times"}, 
				{"mData" : "intervalTime"}, 
				{"mData" : "updateTime"}
			];
		dataTablesCom($('#site_list'), "/admin/site/s", dataTableTitle, null, site_list_call_back,false, false, true);
	}
}

//Callback function
function site_list_call_back(){
	docReady();
	trHoverEdit();
}