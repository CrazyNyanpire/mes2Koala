<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
    <head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/animate.css">
        <%--<%@include file="/commons/metas.jsp"%>--%>
        <title>MES</title>
        <%@include file="/commons/statics.jsp"%>
	<style>
		*{
			font-family: 微软雅黑;
		}
		#loginFormId div{
			margin:1em auto;
			width: 27%;
		}
		#loginFormId span{
			position: absolute;
			font-size: 19px;
			color: #eee;
			line-height: 40px;
			margin-left: 10px;
			transition:1s;
		}
		input{
			width: 100%;
			line-height: 40px;
			height: 40px;
			border-radius: 10px;
			background-color: hsla(0, 0%, 100%, 0.5);
			border: none;
			font-size: 19px;
			color: #eee;padding: 0 10px;
			outline: none;
		}
		input:focus{
			border-radius: 10px;
			border: 1px solid #ffffff;
		}
		button:hover{
			border:2px solid;
/* 			transform: scale(1,0);
transition: opacity 0.35s, transform 0.35s; */
		}
		#loginFormId button{
			font-size: 19px;
			width: 100%;
			height: 40px;
			border-radius: 10px;
			color: #fff;
			background-color: transparent;
			border: 1px solid;
			cursor: pointer;
		}
		.password{
		  -webkit-animation-delay: 0.25s;
		  -moz-animation-delay: 0.25s;
		}
		.sign{
		  -webkit-animation-delay: 0.5s;
		  -moz-animation-delay: 0.5s;
		}
		.footer{
			margin-top: 14%;color: #fff;
		}
		.logo{
			margin-top:10%;
			margin-bottom:6%;
		}
		@media (min-width: 1400px) {
			.footer{
				margin-top: 21%;
			}
			#loginFormId div{
				width: 24%;
			}
		}
 		@media (min-width: 1400px) and (min-height: 1000px) {
			.footer{
				margin-top: 21%;
			}
			.logo{
				margin-top:15%;
			}
		} 
		@media (min-height: 750px) and (max-width: 1380px) {
			.logo{
				margin-top:15%;
			}
			.footer{
				margin-top: 16%;
			}
		}
	</style>
<script type = "text/javascript">
        var contextPath = '${pageContext.request.contextPath}';
</script>
</head>
<% response.setHeader("login","login"); %>
<body style="background:url('${pageContext.request.contextPath}/images/251570.jpg');background-size: 100%;text-align: center;">
	<div class="logo">
		<img src="${pageContext.request.contextPath}/images/logo3.png" alt="" style="width:200px;opacity:0.8">
	</div>
	<div>
		<form id="loginFormId" action="login" method="post">
			<div class="username">
				<span>User Name</span><input id="j_username" type="text" name="username" pattern="^1[3-9]\d{9}$" required>
			</div>
			<div class="password">
				<span>Pass Word</span><input id="j_password" type="password" name="password" required>
			</div>
			<div class="sign">
				<button id="loginBtn">Sign in</button>
			</div>
		</form>
	</div>
	<P class="footer">Copyright ©2011-2016 江苏艾科半导体</P>
</body>
	<script>
    $(function(){
    	$(".username").addClass('animated fadeInUp');
		$(".password").addClass('animated fadeInUp');
		$(".sign").addClass('animated fadeInUp');
		$(".username input").bind("focus",function(){$(this).prev("span").css("margin-left","-115px")});
		$(".password input").bind("focus",function(){$(this).prev("span").css("margin-left","-113px")});
     	var btnLogin = $('#loginBtn');
    	var form = $('#loginFormId');
        $('body').keydown(function(e) {
            if (e.keyCode == 13) {
            	dologin();
            }
        });
        btnLogin.on('click',function() {
        	dologin();
        });
        
	    var dologin = function() {
	        var userNameElement = $("#j_username");
	        var passwordElement = $("#j_password");
	        var username = userNameElement.val();
	        var password = passwordElement.val();
	        if (!Validation.notNull($('body'), userNameElement, username, '用户名不能为空')) {
	            return false;
	        }
	        if (!Validation.notNull($('body'), passwordElement, password, '密码不能为空')) {
	            return false;
	        }
	        btnLogin.attr('disabled', 'disabled').html('loading...');
    		var param = form.serialize();
        	$.ajax({
        		url: contextPath+"/login.koala",
        		dataType: "json",
        		data: param,
        		type: "POST",
        		success: function(data){
        			if(data.success){
        				$('body').message({
        					type: 'success',
        					content:  '登录成功！'
        				});
        				window.location.href=contextPath+"/index.koala";
        			}else{
        				btnLogin.removeAttr('disabled').html('Sign in');
        				$('body').message({
        					type: 'error',
        					content: data.errorMessage
        				});
        				refreshCode();
        			}
        		}
        	});
		};
		});
		
		function refreshCode() {
			$("#checkCode").attr("src","jcaptcha.jpg?time="+new Date().getTime());
		}
		
	</script>
</body>
</html>
