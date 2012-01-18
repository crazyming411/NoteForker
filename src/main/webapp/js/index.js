
onload=function(){
	loginVal=0;
	initUI();
    
};

var initUI=function(){
    var profile=document.getElementById('profile');
    var loggedinUser=document.cookie.match(/(user=).+?(;|$)/gi);
    if (loggedinUser!=null){
    	loggedinUser=loggedinUser[0];
    	loggedinUser=loggedinUser.replace("user=","");
    	loggedinUser=loggedinUser.replace(';','');
   		profile.innerHTML=loggedinUser;
   		profile.href='/'+loggedinUser;
   		loginVal=1;
   	}
    profile.onclick=function(e){
    	e.preventDefault();
    	if(loginVal==0){
    		
    		$('#mask').fadeIn();
    		$('#signInForm').fadeIn();
    	}else{
    		pushStates(this.href);
    	}
    };
    
    profile.ondrag=function(e){
    	console.log(document.cookie.match(/user.+?;/gi));
    	var formData={};
			formData.account="";
			formData.passwd ="";
			formData.index = "";
			formData.maxId="";
		$.ajax(
					{
						url: '/user/',
						type: 'POST',
						headers: {"usage":"logout"},
						contentType: "application/json; charset=utf-8",
						data: encodeURIComponent(JSON.stringify(formData)),
						dataType: "json",
						success: function(resp){
												
					    	$('#profile').html("Sign In");
					    	profile.href='#';
					    	loginVal=0;
						},
						error: function(xhr, status, err){
							console.log(xhr.status);
						}
					}
				);
    };
    
    var mask=document.getElementById('mask');
    mask.onclick=function(){
    	$('#signInForm').fadeOut();
    	$('#mask').fadeOut();	
    };
    
    var regBtn=document.getElementById('regBtn');
    regBtn.onclick=function(e){
    	e.preventDefault();
    	$(this).fadeOut();
    	$('#register').fadeIn();
    	$('#loginBtn').val('Register');
    };

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
						data: encodeURIComponent(JSON.stringify(formData)),
						dataType: "json",
						success: function(resp){
							$('#signInForm').fadeOut();
					    	$('#mask').fadeOut();					
					    	$('#profile').html(formData.account);
					    	profile.href='/'+formData.account;
					    	loginVal=1;
						},
						error: function(xhr, status, err){
							console.log(xhr.status);
							if(xhr.status==400){
								loginForm['passwd'].value="";
								$('#passText').css('color','red');
							}else{
								loginForm['account'].value="";
								loginForm['passwd'].value="";
								$('#accText').css('color','red');
							}
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
						data: encodeURIComponent(JSON.stringify(formData)),
						dataType: "json",
						success: function(resp){
							console.log(resp);
							loginVal=1;
							pushStates('/'+loginForm['account'].value);
						},
						error: function(resp){
							console.log(resp.responseText);
						}
					}
				);
		}else;
	};

};

var pushStates=function(location){
	$('#mask').fadeIn();
	history.pushState(null, null, location);
	$('#main').load(
		location,
		function(resp, status, xhr){
			$('#mask').fadeOut();
		}
	);
};

onpopstate=function(){
   	$('#mask').fadeIn();
    $('#main').load(
    	location.href+" #main", 
    	function(response, status, xhr){
    		initUI();
    		$('#mask').fadeOut(800);
    	}
    );
};
