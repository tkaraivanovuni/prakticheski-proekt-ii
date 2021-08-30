var me;
    	  
function getAuthorization(){
    $.ajax({
        url: "/authorization",
    	method: "GET",
    	complete: function(data){
    		
    		switch(data.status){
    		case 200:
    			me = data.responseJSON;
    			break;
    		case 401:
    			window.location.href = "index.html";
    			break;
    		}
    				  
    	},
    	fail: function(){
    		window.location.href = "index.html";
    	}
    				  
    });
}

function getAdministration(){
    $.ajax({
        url: "/authorization/admin",
    	method: "GET",
    	complete: function(data){
    		
    		switch(data.status){
    		case 200:
    			me = data.responseJSON;
    			break;
    		case 401:
    			window.location.href = "index.html";
    			break;
    		}
    				  
    	},
    	fail: function(){
    		window.location.href = "index.html";
    	}
    				  
    });
}

function logout(){
	$.ajax({
		  url: "/session",
		  method: "DELETE",
		  complete: function(data){
			  if(data.status == 200){
				  window.location.href = "index.html";
			  }else{
				  alert("Error!");
				  window.location.href = "index.html";
			  }
		  }
	});
}

function logoutAdmin(){
	$.ajax({
		  url: "/session",
		  method: "DELETE",
		  complete: function(data){
			  if(data.status == 200){
				  window.location.href = "admin.html";
			  }else{
				  alert("You are not logged in!");
				  window.location.href = "admin.html";
			  }
		  }
	});
}
