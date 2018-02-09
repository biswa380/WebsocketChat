<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Chat</title>
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/loginModal.css">
<link rel="stylesheet" href="resources/css/chatpopup.css">

<script type="text/javascript" src="resources/js/jquery-min.js"></script>
<script type="text/javascript" src="resources/js/angular.min.js"></script>
<script type="text/javascript" src="resources/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="resources/AngularController/chatController.js"></script>
<script src="resources/js/sockjs-0.3.4.js"></script>
<script src="resources/js/stomp.js"></script>
<!--         <script type="text/javascript">
            var stompClient = null;
             
            function setConnected(connected) {
                document.getElementById('connect').disabled = connected;
                document.getElementById('disconnect').disabled = !connected;
                document.getElementById('conversationDiv').style.visibility 
                  = connected ? 'visible' : 'hidden';
                document.getElementById('response').innerHTML = '';
            }
             
            function connect() {
                var socket = new SockJS('/websocket/chat');
                stompClient = Stomp.over(socket);  
                stompClient.connect({}, function(frame) {
                    setConnected(true);
                    console.log('Connected: ' + frame);
                    stompClient.subscribe('/topic/messages', function(messageOutput) {
                        showMessageOutput(JSON.parse(messageOutput.body));
                    });
                });
            }
             
            function disconnect() {
                if(stompClient != null) {
                    stompClient.disconnect();
                }
                setConnected(false);
                console.log("Disconnected");
            }
             
            function sendMessage() {
                var from = document.getElementById('from').value;
                var text = document.getElementById('text').value;
                stompClient.send("/app/chat", {}, 
                  JSON.stringify({'from':from, 'text':text}));
            }
             
            function showMessageOutput(messageOutput) {
                var response = document.getElementById('response');
                var p = document.createElement('p');
                p.style.wordWrap = 'break-word';
                p.appendChild(document.createTextNode(messageOutput.from + ": " 
                  + messageOutput.text + " (" + messageOutput.time + ")"));
                response.appendChild(p);
            }
        </script> -->
</head>
<body ng-app="chatApp" ng-controller="ChatController"
	ng-load="disconnect()">
	<div class="container">
		<div class="row">

			<!-- Signin & Signup -->
			<a class="btn btn-primary" href="#" data-toggle="modal"
				data-target=".login-register-form"> Login - Registration Modal </a>

			<!-- Login / Register Modal-->
			<div class="modal fade login-register-form" id="loginModal"
				role="dialog">
				<div class="modal-dialog modal-sm">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span class="glyphicon glyphicon-remove"></span>
							</button>
							<ul class="nav nav-tabs">
								<li class="active"><a data-toggle="tab" href="#login-form">
										Login <span class="glyphicon glyphicon-user"></span>
								</a></li>
								<li><a data-toggle="tab" href="#registration-form">
										Register <span class="glyphicon glyphicon-pencil"></span>
								</a></li>
							</ul>
						</div>
						<div class="modal-body">
							<div class="tab-content">
								<div id="login-form" class="tab-pane fade in active">
									<form>
										<div class="form-group">
											<label for="email">Username:</label> <input type="text"
												class="form-control" id="username"
												placeholder="Enter username" ng-model="username">
										</div>
										<div class="form-group">
											<label for="pwd">Password:</label> <input type="password"
												class="form-control" id="pwd" placeholder="Enter password"
												ng-model="pwd">
										</div>
										<div class="checkbox">
											<label><input type="checkbox" name="remember">
												Remember me</label>
										</div>
										<button type="submit" class="btn btn-default"
											ng-click='login()'>Login</button>
									</form>
								</div>
								<div id="registration-form" class="tab-pane fade">
									<form action="register" method="post">
										<div class="form-group">
											<label for="name">Your Name:</label> <input type="text"
												class="form-control" id="name" placeholder="Enter your name"
												name="name">
										</div>
										<div class="form-group">
											<label for="newemail">Email:</label> <input type="email"
												class="form-control" id="newemail"
												placeholder="Enter new email" name="newemail">
										</div>
										<div class="form-group">
											<label for="newpwd">Password:</label> <input type="password"
												class="form-control" id="newpwd" placeholder="New password"
												name="newpwd">
										</div>
										<button type="submit" class="btn btn-default">Register</button>
									</form>
								</div>

							</div>
						</div>
						<!--                                    <div class="modal-footer">-->
						<!--                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>-->
						<!--                                    </div>-->
					</div>
				</div>
			</div>
		</div>
		<div>
			<!-- 
            <div>
                <input type="text" id="from" placeholder="Choose a nickname"/>
            </div>
            <br />
            <div>
                <button id="connect" ng-click="connect()">Connect</button>
                <button id="disconnect" disabled="disabled" ng-click="disconnect()">
                    Disconnect
                </button>
            </div> -->
			<br />
			<div id="conversationDiv" ng-show="showChatBox">
				<label>Send to {{sendTo}}</label> <input type="text" id="text"
					placeholder="Write a message..." />
				<button id="sendMessage" ng-click="sendMessage()">Send</button>
				<p id="response"></p>
			</div>
		</div>
		<ul id="online">
			<li class="row" ng-repeat="user in userlist"
				ng-click="selectRecipent(user)"><a href="#">{{user.userName}}</a></li>
		</ul>
		<!--         Chat popup for private chat -->
		<div id="chatDivs">
			<div class="popup-box chat-popup popup-box-on" id='{{chatBox.user.userName}}' ng-repeat="chatBox in chatBoxes">
				<div class="popup-head">
					<div class="popup-head-left pull-left">
						<img
							src="resources/images/SDRC.png">
						{{chatBox.user.userName}}
					</div>
					<div class="popup-head-right pull-right">
					<div class="btn-group">
    								  <button class="chat-header-button" data-toggle="dropdown" type="button" aria-expanded="false">
									   <i class="glyphicon glyphicon-cog"></i> </button>
									  <ul role="menu" class="dropdown-menu pull-right">
										<li><a href="#">Media</a></li>
										<li><a href="#">Block</a></li>
										<li><a href="#">Clear Chat</a></li>
										<li><a href="#">Email Chat</a></li>
									  </ul>
						</div>
						<button data-widget="remove" id="removeClass"
							class="chat-header-button pull-right" type="button">
							<i class="glyphicon glyphicon-off" ng-click="closeWindow($index)"></i>
						</button>
					</div>
				</div>
				
				<div class="popup-messages">
					<div class="direct-chat-messages" id="msg-div" ng-repeat="message in chatBox.messages">
						<div class="direct-chat-msg doted-border">
							<div class="direct-chat-info clearfix">
								<span class="direct-chat-name pull-left">{{message.from}}</span>
							</div>
							<div class="direct-chat-text">{{message.text}}</div>
							<div class="direct-chat-info clearfix">
								<span class="direct-chat-timestamp pull-right">{{message.time}}</span>
							</div>
						</div>
					</div>
				</div>
				<div class="popup-messages-footer">
					<textarea id="status_message_{{chatBox.user.userName}}" placeholder="Type a message..."
						rows="10" cols="40" name="message" ng-focus="changeRecipent(chatBox.user.userName)" ng-enter="sendMessage($index)"></textarea>
					<div class="btn-footer">
					<input type="file" id="fileLoader" name="files" title="Load File" ng-show=false file-model = "message.attachment"/>
					<button id="uploadBtn" ng-show=false ng-click = "sendFile($index)">upload me</button>
					<button class="bg_none"><i class="glyphicon glyphicon-paperclip" onclick="openfileDialog();"></i> </button>
						<button class="bg_none pull-right">
							<i class="glyphicon glyphicon-send" ng-click="sendMessage($index)"></i>
						</button>
					</div>
				</div>
			</div>
		</div>

		<!-- 	  <div ng-repeat="chat in chatBoxes">{{chat.element}}</div> -->
	</div>
<script>
function openfileDialog() {
    $("#fileLoader").click();
}
</script>


</body>
</html>