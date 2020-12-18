/**
 * 
 */
var metadataFlag = false;
var dataFlag = false;
var metadata = "";
var data = "";
function loadValues(formId, custKey, dataFileName) {
    //alert("-=-=-=-=-=-=");
    if (dataFileName == null || dataFileName == '') {
        var parEle = document.getElementById("hiddenDiv");
        var newEle = document.createElement("input");
        parEle.appendChild(newEle);
        newEle.setAttribute("name", "mode");
        newEle.setAttribute("id", "modeId");
        newEle.setAttribute("value", "new");
        return;
    } else {
        var parEle = document.getElementById("hiddenDiv");
        var newEle = document.createElement("input");
        parEle.appendChild(newEle);
        newEle.setAttribute("name", "mode");
        newEle.setAttribute("id", "modeId");
        newEle.setAttribute("value", dataFileName);
    }
    //alert("-=-=-=xgfs-=-=-="+document.getElementById("modeId").value);
    getMetadata(formId, custKey);
    getData(formId, custKey, dataFileName);
}

function getMetadata(formId, custKey) {
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
    xmlhttp.open("get", "http://localhost:8080/rbid/formoperation/form/getform?formid=" + formId + "&customerkey=" + custKey, true);
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            //alert("Success:->>>"+this.responseText);
            var obj = JSON.parse(this.responseText);
            if (obj.Status == 'Success') {
                metadataFlag = true;
                metadata = this.responseText;
                load2Field();
            } else {
                alert("Error: " + obj.StatusDetails);
            }
            //call edit codeing
        }
    };

    xmlhttp.send();
}

function getData(formId, custKey, dataFileName) {
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
    xmlhttp.open("get", "http://localhost:8080/rbid/formoperation/form/getformdata?formid=" + formId + "&customerkey=" + custKey + "&dataname="+dataFileName+".json", true);
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            //alert("Success:->>>"+this.responseText);
            //call edit codeing
            var obj = JSON.parse(this.responseText);
            if (obj.Status == 'Success') {
                dataFlag = true;
                data = this.responseText;
                load2Field();
            } else {
                alert("Error: " + obj.StatusDetails);
            }
        }
    };

    xmlhttp.send();
}
function isArray(what) {
    return Object.prototype.toString.call(what) === '[object Array]';
}
function load2Field() {
    //alert(dataFlag+"-=-=-=val-=-=-="+metadataFlag);
    if (dataFlag && metadataFlag) {
        //alert("-=-=-=val-=-=-=");
        var metadataObj = JSON.parse(metadata);
        var dataObj = JSON.parse(data);
        var itemsArr = metadataObj.Items;
        var i = "";
        var ids = [];
        var fieldtypes = [];
        var co = 0;
        for (i in itemsArr) {
            var obj = itemsArr[i];
            var id = obj.id;
            var type = obj.type;
            var typeTemp = type;
            if (type == 'textarea' || type == 'email')
                type = 'text';
            else if (type == 'radio') {
                type = dataObj[id] == null ? "" : dataObj[id];
            }
            else if(type == 'pagebreak'){
            	continue;
            }
            if(type=='checkbox'){
            	if(dataObj[id] != null){
            		var selectArr = dataObj[id];
            		if(isArray(selectArr)){
            			for (j in selectArr) {
            				var selVal = selectArr[j];
            				var fieldID = id + selVal;
            				//alert(fieldID + "-=-=-=val-=-data=-="+selVal);
                			document.getElementById(fieldID).checked = true;
            			}
            		}else{
            			var fieldID = id + selectArr;
            			document.getElementById(fieldID).checked = true;
            		}
            	}
            	
            }else if(type=='multi'){
            	if(dataObj[id] != null){
            		var selectArr = dataObj[id];
            		if(isArray(selectArr)){
            			for (j in selectArr) {
            				var selVal = selectArr[j];
            				var fieldID = id + selVal;
            				//alert(fieldID + "-=-=-=val-=-data=-="+selVal);
                			document.getElementById(fieldID).selected = true;
            			}
            		}else{
            			var fieldID = id + selectArr;
            			document.getElementById(fieldID).selected = true;
            		}
            	}
            	
            }else{
            	var fieldID = id + type;
                var val = dataObj[id] == null ? "" : dataObj[id];
                //alert(fieldID + "-=-=-=val-=-data=-=" + val);
                if (typeTemp == 'radio' || typeTemp == 'checkbox') {
                    document.getElementById(fieldID).checked = true;
                } else {                   
                    try{                    	
                    	if(fieldID.includes("image")){
                    		var div= fieldID.substr(0,5);                    		
                    		var x="imgdiv"+div;                    		
                    		val=val.replaceAll("\\","/");
                    		if(val.includes("Business Planner")){                 		                    			
                    			val=val.replace("C:/Business Planner/","http://localhost:38391/");
                    		}   
                    		else{
                    			val=val.replace("C:/businessplanner/","https://ultradocuments.com/");
                    		}
                    		document.getElementById(x).src = val;                   		                    		
                    	}
                    	else{
                    	document.getElementById(fieldID).value = val;
                    	}
                    	}
                    	catch (e) { 
                    	  console.log(fieldID);
                    	}
                }
            }
            

            co++;
        }
    }
}