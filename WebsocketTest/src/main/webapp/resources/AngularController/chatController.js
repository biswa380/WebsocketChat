/**
 * Author : Biswabhusan Pradhan
 */
var app = angular.module('chatApp',[]).controller('ChatController', 
function($scope, $http){
	$scope.stompClient = null;
	$scope.showChatBox=false;
	$scope.sendTo = "group";
	
	$scope.login = function(){
		$http.post("login?username="+$scope.username+'&pwd='+$scope.pwd)
		.then(function(response){
			$scope.username=response.data;
			
			if($scope.username!='failed' && $scope.username!=undefined){
				$scope.connect();
				$('#loginModal').modal('hide');
			}
		},function(error){
			console.log(error);
		});
	}
	
	$scope.setConnected=function(connected) {
        document.getElementById('conversationDiv').style.visibility 
          = connected ? 'visible' : 'hidden';
        document.getElementById('response').innerHTML = '';
    }
    $scope.connect =  function() {
        var socket = new SockJS('/websocket/chat');
        stompClient = Stomp.over(socket);  
        $scope.showChatBox=true;
        stompClient.connect({}, function(frame) {
        	$scope.setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/messages', function(messageOutput) {
                showMessageOutput(JSON.parse(messageOutput.body));
            });
            
            stompClient.subscribe('/topic/privateMessage/'+$scope.username, function(messageOutput) {
                showPrivateMessageOutput(JSON.parse(messageOutput.body));
            });
            
        });
        
        $http.get("userlist").then(function(response){
        	$scope.userlist=response.data;
        })
    };
    
    $scope.disconnect=function() {
        if(stompClient != null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    };
    
    $scope.message={};
     
    $scope.sendMessage=function(index) {
    	var from=$scope.username;
        
        if($scope.sendTo=="group")
        	var text = document.getElementById('text').value;
        else
        	var text = document.getElementById('status_message_'+$scope.sendTo).value;
        
//        For getting current time
        function checkTime(i) {
            return (i < 10) ? "0" + i : i;
        }
        var today = new Date(),
        h = checkTime(today.getHours()),
        m = checkTime(today.getMinutes()),
        s = checkTime(today.getSeconds());
        var time = h + ":" + m + ":" + s;
//        end
        
        document.getElementById('text').value="";
        document.getElementById('status_message_'+$scope.sendTo).value="";
        if($scope.sendTo != "group") {
			destination = "/app/chat.private." + $scope.sendTo;
			stompClient.send(destination, {},JSON.stringify({'from':from, 'text':text, 'time':time}));
			$scope.chatBoxes[index].messages.push({'from':from,'to':$scope.sendTo, 'text':text});
		
			 setTimeout(function(){
		    		$('#'+$scope.sendTo+' .popup-messages').animate({
		    			scrollTop : $('#'+$scope.sendTo+' .popup-messages')[0].scrollHeight
		    		}, 0);
		    	},100);
        }
        else{
        	stompClient.send("/app/chat", {},JSON.stringify({'from':from, 'text':text, 'time':time}));
        }
        
       
    };
     
    var showMessageOutput=function(messageOutput) {
        var response = document.getElementById('response');
        var p = document.createElement('p');
        p.style.wordWrap = 'break-word';
        p.appendChild(document.createTextNode(messageOutput.from + ": " 
          + messageOutput.text + " (" + messageOutput.time + ")"));
        response.appendChild(p);
    };
    
    var showPrivateMessageOutput=function(messageOutput) {
    	$scope.user={};
    	$scope.user.userName=messageOutput.from;
    	
     			if($scope.chatBoxes.length==0){
     			
     			$http.get('getSession?from='+$scope.username+'&to='+messageOutput.from).then(function(response){
     	     		 $scope.sessionId=response.data.sessionId;
     	     		$scope.chatFlag=false;
     	     		 $http.get('getMessages?sessionId='+$scope.sessionId).then(function(res){
     	     			 $scope.messageList=res.data;
     	     			$scope.chatBoxes.push({user: $scope.user, messages : $scope.messageList});
     	     		 });
     	     		$scope.sendTo=messageOutput.from;
     	    	});
     			
     			}else{
          	    	angular.forEach($scope.chatBoxes, function(value, key) {
          	    		if(value.user.userName==$scope.user.userName){
          	    			value.messages.push({'from':messageOutput.from,'to':messageOutput.to, 'text':messageOutput.text});
          	    			$scope.chatFlag=true;
          	    		}
          	    	});
          	    	if($scope.chatFlag==false){
          	    		$http.get('getSession?from='+$scope.username+'&to='+messageOutput.from).then(function(response){
            	     		 $scope.sessionId=response.data.sessionId;
            	     		$scope.chatFlag=false;
            	     		 $http.get('getMessages?sessionId='+$scope.sessionId).then(function(res){
            	     			 $scope.messageList=res.data;
            	     			$scope.chatBoxes.push({user: $scope.user, messages : $scope.messageList});
            	     		 });
            	     		$scope.sendTo=messageOutput.from;
            	    	});
          			}
          	    }
     			$scope.$apply();
     			
     			setTimeout(function(){
      	    		$('#'+messageOutput.from+' .popup-messages').animate({
      	    			scrollTop : $('#'+$scope.sendTo+' .popup-messages')[0].scrollHeight
      	    		}, 0);
      	    	},500);
    };
    $scope.changeRecipent = function(user){
    	$scope.sendTo=user;
    };
    $scope.chatBoxes=[];
    $scope.selectRecipent = function(user){
    	$scope.sendTo=user.userName;
    	$scope.chatFlag=false;
    	$http.get('getSession?from='+$scope.username+'&to='+$scope.sendTo).then(function(response){
      		 $scope.sessionId=response.data.sessionId;
      		 $http.get('getMessages?sessionId='+$scope.sessionId).then(function(res){
      			 $scope.messageList=res.data;
      			 
      			if($scope.chatBoxes.length==0){
      	    		$scope.chatBoxes.push({user: user, messages : $scope.messageList});
      	    	}else{
      	    	angular.forEach($scope.chatBoxes, function(value, key) {
      	    		
      	    		if(value.user.userId==user.userId){
      	    			$scope.chatFlag=true;
      	    		}
      	    	});
      	    	if($scope.chatFlag==false){
      				$scope.chatBoxes.push({user: user, messages : $scope.messageList});
      			}
      	    }
      			setTimeout(function(){
      	    		$('#'+$scope.sendTo+' .popup-messages').animate({
      	    			scrollTop : $('#'+$scope.sendTo+' .popup-messages')[0].scrollHeight
      	    		}, 0);
      	    	},100);
      		 });
      	 });
    };
    
    $scope.closeWindow=function(index){
    	$scope.chatBoxes.splice(index, 1);
    };
    
})

.directive('ngEnter', function() {
    return function(scope, element, attrs) {
        element.bind("keydown keypress", function(event) {
            if(event.which === 13) {
                scope.$apply(function(){
                    scope.$eval(attrs.ngEnter, {'event': event});
                });

                event.preventDefault();
            }
        });
    };
});