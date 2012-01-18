window.onload=function(){
    initUI();
    
}

var initUI=function(){
    
    var profile=document.getElementById('profile');
    profile.onclick=function(e){
    	e.preventDefault();
    	$('#mask').fadeIn();
    	$('#signInForm').fadeIn();
    }
    
    var mask=document.getElementById('mask');
    mask.onclick=function(){
    	$('#signInForm').fadeOut();
    	$('#mask').fadeOut();	
    }
    
    var regBtn=document.getElementById('regBtn');
    regBtn.onclick=function(e){
    	e.preventDefault();
    	$(this).fadeOut();
    	$('#register').fadeIn();
    	$('#loginBtn').val('Register');
    }

	var loginForm=document.getElementById('login');
	loginForm.onsubmit=function(e){
		e.preventDefault();
		var formData={};
			formData.account=loginForm['account'].value;
			formData.passwd =loginForm['passwd'].value;
			formData.index = "";
			formData.maxId="";

		if(loginForm['submit'].value=='Sign In'){
			$.ajax(
					{
						url: '/user/',
						type: 'POST',
						headers: {"usage":"login"},
						contentType: "application/json; charset=utf-8",
						data: JSON.stringify(formData),
						dataType: "json",
						success: function(msg){
							console.log(msg);
						},
						error: function(response){
							console.log(response.responseText);
						}
					}
				);
		}else if(loginForm['submit'].value=='Register'){
			$.ajax(
					{
						url: '/user/',
						type: 'POST',
						headers: {"usage":"create"},
						contentType: "application/json; charset=utf-8",
						data: JSON.stringify(formData),
						dataType: "json",
						success: function(msg){
							console.log(msg);
						},
						error: function(response){
							console.log(response.responseText);
						}
					}
				);
		}else;
	}

}


onpopstate=function(){
    $('body').load(
    	location.href+" #main", 
    	function(response, status, xhr){
    		initUI();
    	}
    );
}