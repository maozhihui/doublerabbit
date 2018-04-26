/*var menuList =[
    {
        "id": 1,
        "parentId": -1,
        "code": null,
        "name": "园区监测",
        "levelFlag": 1,
        "leafs": null
    },
    {
        "id": 2,
        "parentId": 1,
        "code": null,
        "name": "环境",
        "levelFlag": 2,
        "leafs": [
            {
                "id": 1,
                "devEui": null,
                "devName": "温湿度_E5_01",
                "devType": 4,
                "location": null
            },
            {
                "id": 3,
                "devEui": null,
                "devName": "温湿度_E4_01",
                "devType": 4,
                "location": null
            },
            {
                "id": 5,
                "devEui": null,
                "devName": "温湿度_A1_01",
                "devType": 4,
                "location": null
            },
            {
                "id": 7,
                "devEui": null,
                "devName": "温湿度_E2_01",
                "devType": 4,
                "location": null
            },
            {
                "id": 8,
                "devEui": null,
                "devName": "温湿度_E5_01",
                "devType": 4,
                "location": null
            },
            {
                "id": 9,
                "devEui": null,
                "devName": "温湿度_E2_02",
                "devType": 4,
                "location": null
            },
            {
                "id": 10,
                "devEui": null,
                "devName": "温湿度_E3_01",
                "devType": 4,
                "location": null
            },
            {
                "id": 11,
                "devEui": null,
                "devName": "温湿度_E3_02",
                "devType": 4,
                "location": null
            },
            {
                "id": 12,
                "devEui": null,
                "devName": "温湿度_E4_01",
                "devType": 4,
                "location": null
            },
            {
                "id": 13,
                "devEui": null,
                "devName": "温湿度_E5_02",
                "devType": 4,
                "location": null
            },
            {
                "id": 14,
                "devEui": null,
                "devName": "温湿度_E4_02",
                "devType": 4,
                "location": null
            },
            {
                "id": 15,
                "devEui": null,
                "devName": "温湿度_A1_02",
                "devType": 4,
                "location": null
            },
            {
                "id": 16,
                "devEui": null,
                "devName": "温湿度_E5_03",
                "devType": 4,
                "location": null
            },
            {
                "id": 17,
                "devEui": null,
                "devName": "温湿度_E5_04",
                "devType": 4,
                "location": null
            },
            {
                "id": 28,
                "devEui": null,
                "devName": "PM_01",
                "devType": 20,
                "location": null
            },
            {
                "id": 29,
                "devEui": null,
                "devName": "PH_01",
                "devType": 21,
                "location": null
            },
            {
                "id": 30,
                "devEui": null,
                "devName": "PH_02",
                "devType": 21,
                "location": null
            }
        ]
    },
    {
        "id": 3,
        "parentId": 1,
        "code": null,
        "name": "消防",
        "levelFlag": 2,
        "leafs": [
            {
                "id": 20,
                "devEui": null,
                "devName": "烟感_E3_01",
                "devType": 19,
                "location": null
            },
            {
                "id": 21,
                "devEui": null,
                "devName": "烟感_E2_01",
                "devType": 19,
                "location": null
            },
            {
                "id": 22,
                "devEui": null,
                "devName": "烟感_E4_01",
                "devType": 19,
                "location": null
            },
            {
                "id": 23,
                "devEui": null,
                "devName": "烟感_A1_01",
                "devType": 19,
                "location": null
            },
            {
                "id": 24,
                "devEui": null,
                "devName": "烟感_E2_01",
                "devType": 19,
                "location": null
            },
            {
                "id": 25,
                "devEui": null,
                "devName": "烟感_E4_01",
                "devType": 19,
                "location": null
            },
            {
                "id": 26,
                "devEui": null,
                "devName": "烟感_E4_02",
                "devType": 19,
                "location": null
            },
            {
                "id": 27,
                "devEui": null,
                "devName": "烟感_E3_01",
                "devType": 19,
                "location": null
            }
        ]
    },
    {
        "id": 4,
        "parentId": 1,
        "code": null,
        "name": "车库",
        "levelFlag": 2,
        "leafs": [
            {
                "id": 18,
                "devEui": null,
                "devName": "地磁_E5_01",
                "devType": 12,
                "location": null
            },
            {
                "id": 19,
                "devEui": null,
                "devName": "地磁_E5_02",
                "devType": 12,
                "location": null
            }
        ]
    }
];*/
function initMenu(url){
	var menuJson;
	if(url){
		$.ajax({
			url: url,
			method: 'GET',
			dataType: 'json',
			async:false,
			data: {},
			success: function(resp) {
				menuJson = resp;
			}
		});
	} else {
		menuJson = menuList;
	}
    var str="";
    $("#outerUl").empty();
    for(var i=0;i<menuJson.length;i++){
        if(menuJson[i].levelFlag==1){
            continue;
        }
       var str = '<li><a href="javascript:void(0)" class="menu_title"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+menuJson[i].name+'</span><i class="arrow_right"></i></a>';
       str +=  '<ul class="innerUl">';
       var items = menuJson[i].leafs;
       for(var j=0;j<items.length;j++){
    	   str += '<li><a href="javascript:void(0)" onclick="addActive(this)" class="menu_'+items[j].id+'_'+items[j].devType+'">'+items[j].devName+'</a></li>';
       }
       str += '</ul>';
       str += '</li>';
       $("#outerUl").append(str);
    } 
    $(".menu_title").each(function(){
    	$(this).click(function(){
    		if($(this).next(".innerUl").css("display")=="none"){
    			$(this).next(".innerUl").show();
    			$(this).find("i").removeClass("arrow_right").addClass("arrow_bottom");
    		} else {
    			$(this).next(".innerUl").hide();
    			$(this).find("i").removeClass("arrow_bottom").addClass("arrow_right");
    		}
    	})
    })
    
}

