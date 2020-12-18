$.notify.addStyle("metro", {
    html:
    	"<div  style='background-color:#605CA8; border-radius: 5px;border-color:#605CA8;margin-bottom:-50px;box-shadow: 10px 10px 5px #888888;width:100%;;color:white'>" +
    	   /* "<img src='Mail.png' style='margin-left:20px'>"+*/
    	            "<div class='image' data-notify-html='image'/>" +
    	            "<div class='text-wrapper'>" +
    	            "<i class='fa fa-bullhorn fa-3x'  style='margin-left:-45px;color:white'/>"+     "<div class='title' data-notify-html='title' />" +
    	                "<div class='text' data-notify-html='text'/>" +
    	                	
    	                "<div data-notify-html='buttonvj'/>" +
    					  "<div class='buttons' >" +
    	         
    					  "</div>" +
    					  "<div>" +
    					    "<i class='fa fa-caret-square-o-right' style='color:white'/>"+    "<p class='link1 btn-link' style='margin-top:-18px;margin-left:20px;color:white;font-size:12px;' data-notify-html='link1' />"+
    					  "</div>" +
    					 
    	            "</div>" +
    	        "</div>",
    classes: {
        error: {
            "color": "#fafafa !important",
            "background-color": "#F71919",
            "border": "1px solid #FF0026"
        },
        success: {
            "background-color": "#32CD32",
            "border": "1px solid #4DB149"
        },
        info: {
            "color": "#fafafa !important",
            "background-color": "#1E90FF",
            "border": "1px solid #1E90FF"
        },
        warning: {
            "background-color": "#FAFA47",
            "border": "1px solid #EEEE45"
        },
        black: {
            "color": "#fafafa !important",
            "background-color": "#333",
            "border": "1px solid #000"
        },
        white: {
            "background-color": "#f1f1f1",
            "border": "1px solid #ddd"
        },
		button: {
             "background-color": "#f1f1f1",
             "border": "1px solid #ddd"
       },
		tickerbackground:{
			
			 "background-color":' transparent',
			 "border": "1px solid #5DB20A"
			
		}

            
    }
});